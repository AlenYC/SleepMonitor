package com.ycl.framework.base;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;
import com.umeng.analytics.MobclickAgent;
import com.ycl.framework.utils.util.FastJSONParser;
import com.ycl.framework.utils.util.YisLoger;
import com.ycl.framework.volley.RequestManager;
import com.ycl.framework.volley.Response;
import com.ycl.framework.volley.VolleyError;
import com.ycl.framework.volley.toolbox.MyJsonObjectFastJsonRequest;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;


/**
 * Fragment's framework
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public abstract class FrameFragment extends Fragment {

    protected View viewRoot;

    protected abstract View inflaterView(LayoutInflater inflater,
                                         ViewGroup container, Bundle bundle);
    //return inflater.inflate(R.layout.xxx, container, false);

    protected void initViews() {
    }


    protected void initData() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        viewRoot = inflaterView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, viewRoot);
        initViews();
        initData();
        return viewRoot;
    }

    public static <T extends FrameFragment> T newInstance(Class<T> clazz) {
        T fragment = null;
        try {
            fragment = clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        YisLoger.stateFg(getClass().getName(), "---------onCreateView ");
    }

    @Override
    public void onResume() {
        YisLoger.stateFg(getClass().getName(), "---------onResume ");
        super.onResume();
        MobclickAgent.onPageStart(getClass().getName()); //统计页面
    }

    @Override
    public void onPause() {
        YisLoger.stateFg(getClass().getName(), "---------onPause ");
        super.onPause();
        MobclickAgent.onPageEnd(getClass().getName());  //umeng页面统计
    }

    @Override
    public void onStop() {
        YisLoger.stateFg(getClass().getName(), "---------onStop ");
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        YisLoger.stateFg(getClass().getName(), "---------onDestroy ");
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @SuppressWarnings("unchecked")
    public <T extends View> T findViews(View convert, int ids) {
        return (T) convert.findViewById(ids);
    }

    public <T> void getDataSimpleFg(String url, Map<String, Object> map, final FrameNetworkResponse<T> netListener, final Class<?> cla, final boolean isBeanRestul) {

        RequestManager.addJsonRequest(new MyJsonObjectFastJsonRequest(url, map,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String result) {
                        try {
                            YisLoger.debug(result);
                            com.alibaba.fastjson.JSONObject response = JSON.parseObject(result);
                            if (netListener != null) {
                                if (!response.containsKey("errors")) {
                                    if (response.containsKey("data")) {
                                        if (cla == String.class) {
                                            netListener.successResponse(null, null, response.getString("data"));
                                        } else {
                                            String dataResult = response.getString("data");
                                            if (isBeanRestul) {
                                                T bean = (T) FastJSONParser.getBean(dataResult, cla);
                                                netListener.successResponse(bean, null, dataResult);
                                            } else {
                                                netListener.successResponse(null, (List<T>) FastJSONParser.getBeanList(dataResult, cla), dataResult);
                                            }
                                        }
                                    } else {
                                        //没有data时
                                        netListener.successResponse(null, null, response.toString());
                                    }
                                } else {
                                    ((FrameActivity) getActivity()).showToast(response.getString("errors"));
                                    netListener.failResponse(null);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (netListener != null)
                                netListener.endResponse();
                            ((FrameActivity) getActivity()).showToast("数据格式错误 !");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("TAG", error.getMessage(), error);
                if (netListener != null)
                    netListener.endResponse();
            }
        }), getClass().getSimpleName(),getActivity().getApplicationContext());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        unbindDrawables(viewRoot);
        RequestManager.cancelAll(getClass().getSimpleName());
    }

    //接触绑定
    private void unbindDrawables(View view) {
        if (view.getBackground() != null) {
            view.getBackground().setCallback(null);
        }
        if (view instanceof ViewGroup && !(view instanceof AdapterView)) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                unbindDrawables(((ViewGroup) view).getChildAt(i));
            }
            ((ViewGroup) view).removeAllViews();
        }
    }
}

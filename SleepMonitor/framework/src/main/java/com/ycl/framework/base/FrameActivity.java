package com.ycl.framework.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.umeng.analytics.MobclickAgent;
import com.ycl.framework.R;
import com.ycl.framework.utils.util.FastJSONParser;
import com.ycl.framework.utils.util.SystemBarTintManager;
import com.ycl.framework.utils.util.YisLoger;
import com.ycl.framework.volley.RequestManager;
import com.ycl.framework.volley.Response;
import com.ycl.framework.volley.VolleyError;
import com.ycl.framework.volley.toolbox.MyJsonObjectFastJsonRequest;

import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

public abstract class FrameActivity extends FragmentActivity implements
        Y_FrameActivity {

    protected ProgressDialog mProgressDialog;//进度条

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameActivityStack.create().addActivity(this);
        setRootView(); // 必须放在annotate之前调用
        initStatusBar();
        ButterKnife.bind(this);
        if (initBaseParams(savedInstanceState)) {
            initViews();
            initData();
        }
    }


    //初始化 状态栏
    protected void initStatusBar() {
        //子View 需要添加 一个 space 来 代替 状态栏   (ViewPager中不需要)
        setTranslucentStatus(true);
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setNavigationBarTintEnabled(false);
//        tintManager.setStatusBarTintColor(Color.parseColor("#FFC0312D"));
        tintManager.setStatusBarTintColor(Color.TRANSPARENT);
    }

    //设置透明度
    protected void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @SuppressWarnings("unchecked")
    public <T extends FrameFragment> T getFragment(String tag, Class<T> clazz, int ids) {
        T framgnt = (T) getSupportFragmentManager().findFragmentByTag(tag);
        if (framgnt == null)
            framgnt = FrameFragment.newInstance(clazz);
//        if (!framgnt.isAdded())
//            getSupportFragmentManager().beginTransaction().add(ids, framgnt, tag).commit();
        return framgnt;
    }

    //activity重置  数据重置
    protected boolean initBaseParams(Bundle savedInstanceState) {
        if (savedInstanceState != null) {

        }
        return true;
    }

    @Override
    public void initData() {
    }

    @Override
    public void initViews() {
    }


    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(getApplicationContext()); //友盟统计
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(getApplicationContext());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        YisLoger.state(this.getClass().getName(), "---------  onRestart ");
    }


    @Override
    protected void onDestroy() {
        ButterKnife.unbind(this);
        FrameActivityStack.create().removeActivityStack(this);
        super.onDestroy();
        //清除 request
        RequestManager.cancelAll(getLocalClassName());
//        unbindDrawables(((ViewGroup)findViewById(android.R.id.content)).getChildAt(0));
    }

    /**
     * 通过Class跳转界面 *
     */
    public void startAct(Class<?> cls) {
        startAct(cls, null);
    }

    /**
     * 含有Bundle通过Class跳转界面 *
     */
    public void startAct(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startActForResult(Class<?> cls, int requestCode) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivityForResult(intent, requestCode);
    }


    /**
     * 频繁的toast *
     */
    private Toast mToast;
    private Toast mToast2;

    public void showToast(String text) {
        if (TextUtils.isEmpty(text))
            return;
        if (mToast == null) {
            mToast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    //自定义 toast
    public void showWarmToast(String text) {
        if (TextUtils.isEmpty(text))
            return;
        if (mToast2 == null) {
            mToast2 = new Toast(getApplicationContext());
            mToast2.setGravity(Gravity.CENTER, 0, 0);
            mToast2.setView(LayoutInflater.from(getApplicationContext()).inflate(R.layout.toast_photo_center_layout, null));
        }
        ((TextView) mToast2.getView().findViewById(R.id.tv_toast_warm_str)).setText(text);
        mToast2.setDuration(Toast.LENGTH_SHORT);
        mToast2.show();
    }

    //自定义 res  icon_toast_success_warm
    public void showWarmToast(String text, int resId) {
        if (TextUtils.isEmpty(text))
            return;
        if (mToast2 == null) {
            mToast2 = new Toast(getApplicationContext());
            mToast2.setGravity(Gravity.CENTER, 0, 0);
        }
        mToast2.setView(LayoutInflater.from(this).inflate(R.layout.toast_photo_center_layout, null));
        ((ImageView) mToast2.getView().findViewById(R.id.iv_toast_warm)).setImageResource(resId);
        ((TextView) mToast2.getView().findViewById(R.id.tv_toast_warm_str)).setText(text);
        mToast2.setDuration(Toast.LENGTH_SHORT);
        mToast2.show();
    }


//    /**
//     * Log输出网络请求Url *
//     */
//    public void showLogUrl(String url, Map<String, Object> params) {
//        if (YisLoger.DEBUG_LOG) {
//            if (params == null || params.size() == 0) {
//                Log.i("yhUrl", url);
//                return;
//            }
//            //     if (!"&".equals(url.charAt(url.length() - 1)))
//            url = url + "?";
//            StringBuilder sb = new StringBuilder();
//            for (String key : params.keySet()) {
//                if (params.get(key) != null) {
//                    sb.append(key.trim()).append("=").append(params.get(key))
//                            .append("&");
//                }
//            }
//            Log.i("yhUrl", url + sb.toString());
//        }
//    }

    public void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, null, "数据请求中...");
            mProgressDialog.setCancelable(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
        } else
            mProgressDialog.show();
    }

    public void showProDialog(CharSequence context) {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, null, context);
            mProgressDialog.setCancelable(true);
            mProgressDialog.setCanceledOnTouchOutside(false);
        } else {
            mProgressDialog.setMessage(context);
            mProgressDialog.show();
        }
    }

    public void dismissProgressDialog() {
        if (mProgressDialog != null)
            mProgressDialog.dismiss();
    }

    //缩小动画
    public void finishZoom() {
        super.finish();
        overridePendingTransition(0, R.anim.zoom_exit);
    }

    public void startZoom(Class<?> cls, Bundle bundle) {
        startAct(cls, bundle);
        overridePendingTransition(R.anim.zoom_enter, 0);
    }


    /**
     * * Log输出网络请求Url *
     * *
     * * @param cla          返回Bean类型
     * * @param isBeanRestul 返回结果是否  Bean (true)  List (false)
     */
    public <T> void getDataSimple(String url, Map<String, Object> map, final FrameNetworkResponse<T> netListener, final Class<?> cla, final boolean isBeanRestul) {
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
                                    showToast(response.getString("errors"));
                                    netListener.failResponse(null);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            if (netListener != null)
                                netListener.endResponse();
                            showToast("数据格式错误 !");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                showToast("亲, 你的网络开小差了! ");
                Log.e("TAG", error.getMessage(), error);
                if (netListener != null)
                    netListener.endResponse();
            }
        }), getLocalClassName(), getApplicationContext());
    }

}

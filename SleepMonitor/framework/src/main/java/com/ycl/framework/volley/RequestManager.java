package com.ycl.framework.volley;

import android.content.Context;
import android.text.TextUtils;

import com.ycl.framework.utils.sp.SavePreference;
import com.ycl.framework.utils.util.YisLoger;
import com.ycl.framework.volley.toolbox.JsonObjectRequest;
import com.ycl.framework.volley.toolbox.MyJsonObjectFastJsonRequest;

/**
 * RequsetManager 管理 by yuchaoliang on 16/3/31.
 */
public class RequestManager {


    public static final int OUT_TIME = 11500;
    public static final int TIMES_OF_RETRY = 0;

    public static RequestQueue mRequestQueue ;

    private RequestManager() {
    }

    public static void addRequest(Request<?> request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        //给每个请求重设超时、重试次数
        request.setRetryPolicy(new DefaultRetryPolicy(
                OUT_TIME,
                TIMES_OF_RETRY,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mRequestQueue.add(request);
        YisLoger.logUrl(request.getUrl() + "?" + request.getmRequestBody());
    }

    public static void addJsonRequest(MyJsonObjectFastJsonRequest request, Object tag,Context ctx) {
        if (tag != null) {
            request.setTag(tag);
        }
        //给每个请求重设超时、重试次数
        request.setRetryPolicy(new DefaultRetryPolicy(
                OUT_TIME,
                TIMES_OF_RETRY,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //设置请求时间
        if (!TextUtils.isEmpty(SavePreference.getStr(ctx, "Cookie")))
            request.setSendCookie(SavePreference.getStr(ctx, "Cookie"));//向服务器发起post请求时加上cookie字段
        mRequestQueue.add(request);
        YisLoger.logUrl(request.getUrl() + "?" + request.getmRequestBody());
    }


    public static void addJsonRequestNoCookie(MyJsonObjectFastJsonRequest request, Object tag) {
        if (tag != null) {
            request.setTag(tag);
        }
        //给每个请求重设超时、重试次数
        request.setRetryPolicy(new DefaultRetryPolicy(
                OUT_TIME,
                TIMES_OF_RETRY,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mRequestQueue.add(request);
        YisLoger.logUrl(request.getUrl() + "?" + request.getmRequestBody());
    }


    public static void cancelAll(Object tag) {
        mRequestQueue.cancelAll(tag);
    }

}

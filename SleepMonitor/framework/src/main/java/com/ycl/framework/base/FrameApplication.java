package com.ycl.framework.base;

import android.app.Application;

import com.ycl.framework.volley.RequestManager;
import com.ycl.framework.volley.toolbox.Volley;

/**
 * frameApplication on 2015/10/15.
 */
public class FrameApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RequestManager.mRequestQueue = Volley.newRequestQueue(getApplicationContext());
    }


}
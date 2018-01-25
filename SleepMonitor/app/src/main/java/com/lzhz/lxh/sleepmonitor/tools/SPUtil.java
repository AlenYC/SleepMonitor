package com.lzhz.lxh.sleepmonitor.tools;

import android.content.Context;

/**
 * Created by dk on 2017/7/4.
 */

public class SPUtil {
    static SPUtil instance;
    private Context context;

    public static SPUtil getInstance(Context context) {
        if (instance == null)
            instance = new SPUtil(context);
        return instance;
    }

    private SPUtil(Context context) {
        this.context = context;
    }

    public void clear(){
        context.getSharedPreferences(Constance.APP_NAME, Context.MODE_PRIVATE).edit().clear().commit();
    }

    public boolean put(String key, String value) {
        return context.getSharedPreferences(Constance.APP_NAME, Context.MODE_PRIVATE).edit().putString(key, value).commit();
    }

    public String getString(String key) {
        return context.getSharedPreferences(Constance.APP_NAME, Context.MODE_PRIVATE).getString(key, "");
    }
}

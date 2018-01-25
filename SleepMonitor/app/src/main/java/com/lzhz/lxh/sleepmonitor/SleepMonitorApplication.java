package com.lzhz.lxh.sleepmonitor;

import android.app.Activity;
import android.app.Application;

import com.inuker.bluetooth.library.BluetoothContext;
import com.lzhz.lxh.sleepmonitor.home.activity.bean.User;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

import java.util.Stack;

/**
 * 作者：lxh on 2018-01-05:14:59
 * 邮箱：15911638454@163.com
 */

public class SleepMonitorApplication extends LitePalApplication {
    private static SleepMonitorApplication instance;
    public static String access_token;
    public static User user;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        BluetoothContext.set(this);
        LitePal.initialize(this);

    }


}

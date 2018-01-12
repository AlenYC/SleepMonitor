package com.lzhz.lxh.sleepmonitor;

import android.app.Application;

import com.inuker.bluetooth.library.BluetoothContext;

/**
 * 作者：lxh on 2018-01-05:14:59
 * 邮箱：15911638454@163.com
 */

public class SleepMonitorApplication extends Application {
    private static SleepMonitorApplication instance;

    public static Application getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        BluetoothContext.set(this);

    }
}

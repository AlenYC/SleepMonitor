package com.lzhz.lxh.sleepmonitor;

import android.app.Application;

import com.inuker.bluetooth.library.BluetoothContext;

import org.litepal.LitePal;
import org.litepal.LitePalApplication;

/**
 * 作者：lxh on 2018-01-05:14:59
 * 邮箱：15911638454@163.com
 */

public class SleepMonitorApplication extends LitePalApplication {
    private static SleepMonitorApplication instance;

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

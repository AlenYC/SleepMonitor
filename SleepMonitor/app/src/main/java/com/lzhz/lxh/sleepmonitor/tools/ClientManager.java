package com.lzhz.lxh.sleepmonitor.tools;

import com.inuker.bluetooth.library.BluetoothClient;
import com.lzhz.lxh.sleepmonitor.SleepMonitorApplication;

/**
 * Created by dingjikerbo on 2016/8/27.
 */
public class ClientManager {

    private static BluetoothClient mClient;

    public static BluetoothClient getClient() {
        if (mClient == null) {
            synchronized (ClientManager.class) {
                if (mClient == null) {
                    mClient = new BluetoothClient(SleepMonitorApplication.getInstance());
                }
            }
        }
        return mClient;
    }
}

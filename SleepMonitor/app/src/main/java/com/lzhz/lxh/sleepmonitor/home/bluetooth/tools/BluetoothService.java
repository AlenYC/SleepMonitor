package com.lzhz.lxh.sleepmonitor.home.bluetooth.tools;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.lzhz.lxh.sleepmonitor.tools.ClientManager;

/**
 * 作者：lxh on 2018-03-02:18:03
 * 邮箱：15911638454@163.com
 */

public class BluetoothService extends Service {
    private BluetoothBinder downLoadBinder=new BluetoothBinder();
    private BluetoothDevice mDevice;
    public BluetoothService(){};
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        ClientManager.getInstance().connectDevice(mDevice);
        return downLoadBinder;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        ClientManager.getClient().disconnect(mDevice.getAddress());
        super.unbindService(conn);
    }
    public class BluetoothBinder extends Binder{

    }

}

package com.lzhz.lxh.sleepmonitor.home.bluetooth.tools;

import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.lzhz.lxh.sleepmonitor.SleepMonitorApplication;
import com.lzhz.lxh.sleepmonitor.home.bluetooth.BlueToothActivity;
import com.lzhz.lxh.sleepmonitor.home.bluetooth.adapter.DeviceListAdapter;

import java.util.List;

/**
 * 作者：lxh on 2018-01-09:17:03
 * 邮箱：15911638454@163.com
 */

public class BluetoothTools {
    private  scanTool con;
    private static BluetoothTools bluetoothTools;
    private static BluetoothClient mClient;

    private BluetoothTools(){}
    public static BluetoothTools getInstance(){
        synchronized (BluetoothTools.class){
            if(mClient == null){
                mClient = new BluetoothClient(SleepMonitorApplication.getInstance());
            }
            if(bluetoothTools == null){
                bluetoothTools = new BluetoothTools();
            }
        }
        return bluetoothTools;
    }
    public BluetoothClient getClient(){
        return mClient;
    }
    public void setOnscan(scanTool con){
        this.con = con;
    }
    //扫描蓝牙
    public  void scan(){
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(3000, 3)   // 先扫BLE设备3次，每次3s
              //  .searchBluetoothClassicDevice(5000) // 再扫经典蓝牙5s
                .searchBluetoothLeDevice(2000)      // 再扫BLE设备2s
                .build();
        mClient.search(request, new SearchResponse() {
            @Override
            public void onSearchStarted() {
            }
            @Override
            public void onDeviceFounded(SearchResult device) {
                con.onDeviceFounded(device);

            }
            @Override
            public void onSearchStopped() {
                con.onSearchStopped();
            }
            @Override
            public void onSearchCanceled() {
            }
        });
    }
    public interface scanTool{
        void onDeviceFounded(SearchResult device);
        void onSearchStopped();
    }

}

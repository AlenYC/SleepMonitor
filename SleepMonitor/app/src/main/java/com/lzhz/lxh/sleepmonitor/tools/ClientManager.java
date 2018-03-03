package com.lzhz.lxh.sleepmonitor.tools;

import android.bluetooth.BluetoothClass;
import android.bluetooth.BluetoothDevice;
import android.view.View;

import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.connect.response.BleNotifyResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.lzhz.lxh.sleepmonitor.SleepMonitorApplication;

import org.greenrobot.eventbus.EventBus;

import java.util.UUID;

import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;

/**
 * Created by dingjikerbo on 2016/8/27.
 */
public class ClientManager {

    private static BluetoothClient mClient;
    private static ClientManager mClientManager;
    double raw_data[] = new double[30000];
    int i = 0;
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
    public static ClientManager getInstance(){
        if(mClientManager == null){
            synchronized (ClientManager.class){
                mClientManager = new ClientManager();
            }
        }
        return mClientManager;
    }

    /**
     * 蓝牙连接
     */
    public  void connectDevice(BluetoothDevice mDevice) {

        BleConnectOptions options = new BleConnectOptions.Builder()
                .setConnectRetry(3)
                .setConnectTimeout(20000)
                .setServiceDiscoverRetry(3)
                .setServiceDiscoverTimeout(10000)
                .build();

        ClientManager.getClient().connect(mDevice.getAddress(), options, new BleConnectResponse() {
            @Override
            public void onResponse(int code, BleGattProfile profile) {
                BluetoothLog.v(String.format("profile:\n%s", profile));
                if (code == REQUEST_SUCCESS) {
                }
            }
        });
        ClientManager.getClient().notify(mDevice.getAddress(), UUID.fromString(Constance.UUID_CHAR_SERVICE),UUID.fromString(Constance.UUID_CHAR_READ),new BleNotifyResponse(){

            @Override
            public void onResponse(int code) {
                if (code == REQUEST_SUCCESS) {

                }
            }
            @Override
            public void onNotify(UUID service, UUID character, byte[] value) {

              //  LogUtils.i("-------"+bytes2HexString(value));
                String str = bytes2HexString(value);
                int intt = byteToInt(value[0]);
              //  LogUtils.i("-------"+intt);
             //   LogUtils.i("-------"+intt);
              //  int s = Integer.parseInt(str.substring(0,4));
                LogUtils.i("-------"+byte2Int(value));
              //  LogUtils.i("-------"+intt);
                int s1 = byte2Int(value);
             //   int b = Integer.parseInt(str.substring(0,4));
            //    LogUtils.i("-------"+b);
                raw_data[i] = byteArrayToInt(value);
                i++;
                if(i == 300){

                    EventBus.getDefault().post(raw_data);
                    i = 0;
                }
            }
        });
    }
    public static int byteToInt(byte b) {
        return b & 0xFFFF;
    }
    /**
     * 4位字节数组转换为整型
     * @param b
     * @return
     */
    public static int byte2Int(byte[] b) {
        int intValue = 0;
        for (int i = 0; i < 4; i++) {
            intValue += (b[i] & 0xFF) << (8 * (3 - i));
        }
        return intValue;
    }

    public static String bytes2HexString(byte[] b) {
        String ret = "";
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret += hex;
        }
        return ret;
    }

    private static final char[] HEX_CHAR = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
    //byte数组转16进制字符串
    public static String bytesToHexFun2(byte[] bytes) {
        char[] buf = new char[bytes.length * 2];
        int index = 0;
        for(byte b : bytes) { // 利用位运算进行转换，可以看作方法一的变种
            buf[index++] = HEX_CHAR[b >>> 4 & 0xf];
            buf[index++] = HEX_CHAR[b & 0xf];
        }
        LogUtils.i("-------"+buf);
        return new String(buf);
    }


    public static String bytesToHexString(byte[] b, boolean isSpace) {
        String stmp = "";
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < b.length; n++) {
            stmp = Integer.toHexString(b[n] & 0xFF);
            sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
            if (isSpace) sb.append(" ");
        }
        return sb.toString().toUpperCase().trim();
    }
  /*  public static int byte2int(byte[] res) {
        // 一个byte数据左移24位变成0x??000000，再右移8位变成0x00??0000
        int targets = (res[0] & 0xff) | ((res[1] << 8) & 0xff00) // | 表示安位或
                | ((res[2] << 24) >>> 8) | (res[3] << 24);
        return targets;

    }*/


    public static int byteArrayToInt(byte[] b) {
        return   b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }
    //高位在前，低位在后
    public static int bytes2int(byte[] bytes){
        int result = 0;
        if(bytes.length == 4){
            int a = (bytes[0] & 0xff) << 24;//说明二
            int b = (bytes[1] & 0xff) << 16;
            int c = (bytes[2] & 0xff) << 8;
            int d = (bytes[3] & 0xff);
            result = a | b | c | d;
        }
        return result;
    }

}

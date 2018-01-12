package com.lzhz.lxh.sleepmonitor.home.bluetooth.bean;

import android.bluetooth.BluetoothDevice;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 作者：lxh on 2018-01-09:10:48
 * 邮箱：15911638454@163.com
 */

public class BlueToothBean  implements Parcelable {
    public BluetoothDevice device;

    public int rssi;

    public byte[] scanRecord;

    protected BlueToothBean(Parcel in) {
        device = in.readParcelable(BluetoothDevice.class.getClassLoader());
        rssi = in.readInt();
        scanRecord = in.createByteArray();
    }

    public BlueToothBean(BluetoothDevice device, int rssi, byte[] scanRecord) {
        this.device = device;
        this.rssi = rssi;
        this.scanRecord = scanRecord;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public int getRssi() {
        return rssi;
    }

    public byte[] getScanRecord() {
        return scanRecord;
    }

    public static final Creator<BlueToothBean> CREATOR = new Creator<BlueToothBean>() {
        @Override
        public BlueToothBean createFromParcel(Parcel in) {
            return new BlueToothBean(in);
        }

        @Override
        public BlueToothBean[] newArray(int size) {
            return new BlueToothBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.device, 0);
        parcel.writeInt(this.rssi);
        parcel.writeByteArray(this.scanRecord);
    }
}

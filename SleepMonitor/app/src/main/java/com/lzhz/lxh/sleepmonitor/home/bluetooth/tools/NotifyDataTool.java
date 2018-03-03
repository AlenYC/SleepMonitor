package com.lzhz.lxh.sleepmonitor.home.bluetooth.tools;

/**
 * 作者：lxh on 2018-03-02:17:31
 * 邮箱：15911638454@163.com
 */

public class NotifyDataTool {
    private static NotifyDataTool mNotifyDataTool;
    private double[] raw_data = new double[30000];
    int i  = 0;
    private NotifyDataTool(){
    }

    public static NotifyDataTool getInstans(){
        if(mNotifyDataTool == null){
            synchronized (NotifyDataTool.class){
                mNotifyDataTool = new NotifyDataTool();
            }
        }
        return mNotifyDataTool;
    }
    public void addData(double raw_data){
        this.raw_data[i] = raw_data;
    }
}

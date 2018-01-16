package com.lzhz.lxh.sleepmonitor.home.activity.bean;

import org.litepal.crud.DataSupport;

/**
 * 作者：lxh on 2018-01-15:15:27
 * 邮箱：15911638454@163.com
 */

public class AlarmBean extends DataSupport {
    private int alarmId; //闹钟Id
    private int flag;    //周期性时间间隔的标志,flag = 0 表示一次性的闹钟, flag = 1 表示每天提醒的闹钟(1天的时间间隔),flag = 2
                                   //表示按周每周提醒的闹钟（一周的周期性时间间隔）
    private int hour;    //时
    private int minute;  //分
    private String tips;    //闹钟提示信息
    private int soundOrVibrator;    //2表示声音和震动都执行，1表示只有铃声提醒，0表示只有震动提醒
    private String cycle;    //0 表示每天 ，-1表示只响一次的闹钟   多天用"，"分开
    public int getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(int alarmId) {
        this.alarmId = alarmId;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public int getSoundOrVibrator() {
        return soundOrVibrator;
    }

    public void setSoundOrVibrator(int soundOrVibrator) {
        this.soundOrVibrator = soundOrVibrator;
    }
}

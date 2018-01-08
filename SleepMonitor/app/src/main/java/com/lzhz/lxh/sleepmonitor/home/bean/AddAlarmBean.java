package com.lzhz.lxh.sleepmonitor.home.bean;

import java.util.List;

/**
 * 作者：lxh on 2018-01-06:09:51
 * 邮箱：15911638454@163.com
 */

public class AddAlarmBean {
    private String aabTime;
    private List<String> remindList;
    private boolean aabState;

    public AddAlarmBean(String aabTime, List<String> remindList, boolean aabState) {
        this.aabTime = aabTime;
        this.remindList = remindList;
        this.aabState = aabState;
    }

    public String getAabTime() {
        return aabTime;
    }

    public void setAabTime(String aabTime) {
        this.aabTime = aabTime;
    }

    public List<String> getRemindList() {
        return remindList;
    }

    public void setRemindList(List<String> remindList) {
        this.remindList = remindList;
    }

    public boolean isAabState() {
        return aabState;
    }

    public void setAabState(boolean aabState) {
        this.aabState = aabState;
    }
}

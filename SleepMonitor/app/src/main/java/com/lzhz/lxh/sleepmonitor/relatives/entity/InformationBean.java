package com.lzhz.lxh.sleepmonitor.relatives.entity;

/**
 * 作者：lxh on 2018-01-08:18:26
 * 邮箱：15911638454@163.com
 * 亲友实体类
 */

public class InformationBean {
    int headUrl;
    String userName;
    String describe;
    String time;

    public InformationBean(int headUrl, String userName, String describe, String time) {
        this.headUrl = headUrl;
        this.userName = userName;
        this.describe = describe;
        this.time = time;
    }

    public int getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(int headUrl) {
        this.headUrl = headUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

package com.lzhz.lxh.sleepmonitor.home.bean;

/**
 * 作者：lxh on 2018-01-11:15:34
 * 邮箱：15911638454@163.com
 */

public class HomeLinsBean {
    private int lins;
    private int linsState;

    public HomeLinsBean(int lins1, int linsState) {
        this.lins = lins1;
        this.linsState = linsState;
    }

    public int getLins() {
        return lins;
    }

    public int getLinsState() {
        return linsState;
    }
}

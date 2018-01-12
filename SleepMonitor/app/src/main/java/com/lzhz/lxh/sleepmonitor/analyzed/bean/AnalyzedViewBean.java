package com.lzhz.lxh.sleepmonitor.analyzed.bean;

import java.util.List;

/**
 * 作者：lxh on 2018-01-12:14:53
 * 邮箱：15911638454@163.com
 */

public class AnalyzedViewBean {
    private List<Integer> width;
    private String aTime;

    public AnalyzedViewBean(List<Integer> width, String aTime) {
        this.width = width;
        this.aTime = aTime;
    }

    public List<Integer> getWidth() {
        return width;
    }

    public String getaTime() {
        return aTime;
    }
}

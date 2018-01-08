package com.ycl.framework.base;

/**
 * Activity接口协议， 初始化Activity页面初始化
 */
public interface Y_FrameActivity {
    /** 设置root界面 */
    void setRootView();

    /** 初始化数据 */
    void initData();

    /** 初始化控件 */
    void initViews();
}

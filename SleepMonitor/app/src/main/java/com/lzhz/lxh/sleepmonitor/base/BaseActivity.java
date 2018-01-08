package com.lzhz.lxh.sleepmonitor.base;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.inter.Y_BaseInterface;
import com.lzhz.lxh.sleepmonitor.tools.FrameActivityStack;

import butterknife.ButterKnife;


public class BaseActivity extends AppCompatActivity implements Y_BaseInterface {
    public static boolean showTitle = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView();
        FrameActivityStack.create().addActivity(this);
        ButterKnife.bind(this);

        if (initBaseParams(savedInstanceState)) {
            initViews();
            initData();
        }
    }

    //activity重置  数据重置
    protected boolean initBaseParams(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return false;
        }
        return true;
    }


    @Override
    public void setRootView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initViews() {

    }

    @Override
    protected void onDestroy() {
        FrameActivityStack.create().removeActivityStack(this);
        super.onDestroy();

    }



}

package com.lzhz.lxh.sleepmonitor.home.activity;

import android.os.Bundle;
import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.analyzed.bean.AnalyzedViewBean;
import com.lzhz.lxh.sleepmonitor.base.BaseActivity;
import com.lzhz.lxh.sleepmonitor.tools.view.RectangleView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.ButterKnife;

/**
 * 作者：lxh on 2018-01-12:11:35
 * 邮箱：15911638454@163.com
 */

public class DetectionActivity extends BaseActivity {
    ArrayList<AnalyzedViewBean> list;
    RectangleView rv;
    @Override
    public void setRootView() {
        setContent(R.layout.activity_detection_layout);
        rv = findViewById(R.id.rectangle);
    }

    @Override
    public void initData() {
        list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0 ;i < 5; i++){
            ArrayList<Integer> inte = new ArrayList<>();
            inte.add(random.nextInt(100));
            inte.add(random.nextInt(100));
            inte.add(random.nextInt(100));
            list.add(new AnalyzedViewBean(inte,"5/26"));
        }
        rv.setList(list);
    }

    @Override
    public void initViews() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

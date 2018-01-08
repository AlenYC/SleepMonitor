package com.lzhz.lxh.sleepmonitor.relatives;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.BaseActivity;
import com.lzhz.lxh.sleepmonitor.home.adapter.ShowAlarmAdapter;
import com.lzhz.lxh.sleepmonitor.home.bean.AddAlarmBean;
import com.lzhz.lxh.sleepmonitor.relatives.adapter.InformationAdapter;
import com.lzhz.lxh.sleepmonitor.relatives.entity.InformationBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 消息界面
 */
public class InformationActivity extends BaseActivity {

    private InformationAdapter adapter;
    private List<InformationBean> list;
    @BindView(R.id.rl_information_list)
    RecyclerView rlInformationList;

    @Override
    public void setRootView() {
        setContent(R.layout.activity_information);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initViews() {
        list = new ArrayList<InformationBean>();
        list.add(new InformationBean(0,"小四","查看了你的睡眠数据","10:00"));
        list.add(new InformationBean(0,"小5","查看了你的睡眠数据","11:00"));
        list.add(new InformationBean(0,"小4","查看了你的睡眠数据","12:00"));
        list.add(new InformationBean(0,"小7","查看了你的睡眠数据","3:00"));
        adapter = new InformationAdapter(this, R.layout.mb_information_list, list);
        rlInformationList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rlInformationList.setHasFixedSize(true);
        rlInformationList.setAdapter(adapter);
    }


    @Override
    public void getCenTitle(ImageView ivLeft, TextView tvTitle, TextView tvRight) {
        tvTitle.setText(R.string.tv_information);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

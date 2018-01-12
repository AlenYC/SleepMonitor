package com.lzhz.lxh.sleepmonitor.home.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.BaseActivity;
import com.lzhz.lxh.sleepmonitor.home.adapter.ShowAlarmAdapter;
import com.lzhz.lxh.sleepmonitor.home.bean.AddAlarmBean;
import com.lzhz.lxh.sleepmonitor.tools.DividerItemDecoration;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：lxh on 2018-01-05:18:46
 * 邮箱：15911638454@163.com
 * 闹钟页面
 */

public class AlarmActivity extends BaseActivity implements SwipeItemClickListener{

    @BindView(R.id.smrv_alarm_list)
    SwipeMenuRecyclerView smrvAlarmList;
    private ShowAlarmAdapter adapter;
    private List<AddAlarmBean> list;
    @Override
    public void setRootView() {

        setContent(R.layout.alarm_activity);
    }

    @Override
    public void initData() {

    }



    @Override
    public void initViews() {

        list = new ArrayList<AddAlarmBean>();
        list.add(new AddAlarmBean("07:30", new ArrayList<String>(), true));
        list.add(new AddAlarmBean("08:30", new ArrayList<String>(), true));
        list.add(new AddAlarmBean("09:30", new ArrayList<String>(), true));
        adapter = new ShowAlarmAdapter(this, R.layout.mb_alarm_list, list);
        smrvAlarmList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        smrvAlarmList.setHasFixedSize(true);
        // 设置监听器。
        smrvAlarmList.setSwipeMenuCreator(mSwipeMenuCreator);
        smrvAlarmList.setSwipeItemClickListener(this);
        smrvAlarmList.addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));

        smrvAlarmList.setItemViewSwipeEnabled(true);
        smrvAlarmList.setAdapter(adapter);
    }

    // 创建菜单：
    SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.dp_70);
            int height = getResources().getDimensionPixelSize(R.dimen.dp_70);
            //  int height = ViewGroup.LayoutParams.MATCH_PARENT;
            Log.i("height", "--------" + height);
            SwipeMenuItem deleteItem = new SwipeMenuItem(AlarmActivity.this)
                    .setBackground(R.drawable.selector_red)
                    .setImage(R.mipmap.ic_action_delete)
                    .setText("删除")
                    .setTextColor(Color.WHITE)
                    .setWidth(width)
                    .setHeight(height);
            rightMenu.addMenuItem(deleteItem);// 添加菜单到右侧。

        }
    };


    @Override
    public void onItemClick(View itemView, int position) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    @Override
    public void getCenTitle(ImageView ivLeft, TextView tvTitle, TextView tvRight) {
        tvTitle.setText("闹钟");
        tvRight.setText("编辑");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(AlarmActivity.this, AddAlarmActivity.class);
                startActivity(intent);
            }
        });
    }

}

package com.lzhz.lxh.moduledemo;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzhz.lxh.moduledemo.base.BaseActivity;
import com.lzhz.lxh.moduledemo.map.GdLocationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.m_map)
    Button mMap;

    @Override
    public void setRootView() {
        setContentView(R.layout.activity_main);

    }

    @Override
    public void initData() {

    }

    @Override
    public void initViews() {
        mMap.setOnClickListener(this);
    }

    @Override
    public void setToolbarTitle(ImageView ivLeft, TextView toolbarTitle, ImageView ivRightTop) {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.m_map:
                keepTogo(this, GdLocationActivity.class);
                break;
        }
        super.onClick(view);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

package com.lzhz.lxh.sleepmonitor.sideslip;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.BaseActivity;

/**
 * 版本信息
 */
public class VersionsActivity extends BaseActivity {




    @Override
    public void setRootView() {
        setContent(R.layout.activity_versions);
    }

    @Override
    public void getCenTitle(ImageView ivLeft, TextView tvTitle, TextView tvRight) {
        tvTitle.setText("版本信息");
        tvRight.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

    }
}

package com.lzhz.lxh.sleepmonitor.base.BaseAcitivity;

import android.widget.ImageView;
import android.widget.TextView;

import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.BaseActivity;

/**
 * 公司介绍
 */
public class CompanyIntroduceActivity extends BaseActivity {

    @Override
    public void setRootView() {
        setContent(R.layout.activity_company_introduce);
    }

    @Override
    public void initData() {
    }

    @Override
    public void initViews() {
    }

    @Override
    public void getCenTitle(ImageView ivLeft, TextView tvTitle, TextView tvRight) {
        tvTitle.setText("公司介绍");
    }
}

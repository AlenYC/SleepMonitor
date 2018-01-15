package com.lzhz.lxh.sleepmonitor.home.activity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：lxh on 2018-01-06:15:20
 * 邮箱：15911638454@163.com
 */
public class AddAlarmActivity extends BaseActivity {


    @BindView(R.id.np_hour)
    NumberPicker npHour;
    @BindView(R.id.np_minute)
    NumberPicker minute;
    String time;
    String time1;
    @Override
    public void initViews() {
        setContent(R.layout.add_alarm_activity);

    }
    @Override
    public void initData() {
        npHour.setMinValue(0);
        npHour.setMaxValue(23);
        npHour.setValue(12);
        npHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                time = numberPicker.getValue()+":";
            }
        });
        minute.setMaxValue(59);
        minute.setMaxValue(0);
        minute.setValue(30);
        minute.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                time1 = numberPicker.getValue()+":";
            }
        });
    }

    @Override
    public void getCenTitle(ImageView ivLeft, TextView tvTitle, TextView tvRight) {
        tvTitle.setText("添加闹钟");
        tvRight.setText("存储");

    }
}

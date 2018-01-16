package com.lzhz.lxh.sleepmonitor.home.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.allen.library.SuperTextView;
import com.loonggg.lib.alarmmanager.clock.AlarmManagerUtil;
import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.BaseActivity;
import com.lzhz.lxh.sleepmonitor.home.activity.bean.AlarmBean;
import com.lzhz.lxh.sleepmonitor.home.activity.tools.SelectRemindCyclePopup;
import com.lzhz.lxh.sleepmonitor.home.activity.tools.SelectRemindWayPopup;
import com.lzhz.lxh.sleepmonitor.tools.LogUtils;
import java.util.Calendar;
/**
 * 作者：lxh on 2018-01-06:15:20
 * 邮箱：15911638454@163.com
 * 添加闹钟
 */
public class AddAlarmActivity extends BaseActivity {
    NumberPicker npHour;
    NumberPicker npMinute;
    private RelativeLayout repeat_rl, ring_rl;
    private TextView tv_repeat_value, tv_ring_value;
    private LinearLayout allLayout;
    private SuperTextView stv_remind;
    Integer[] times = {10,30};
    private int alarmId;
    private static String mWeeks;
    private boolean alarmState = true;
    private static  AlarmBean alarmBean;
    @Override
    public void initViews() {
        setContent(R.layout.add_alarm_activity);
        npHour = findViewById(R.id.np_hour);
        npMinute = findViewById(R.id.np_minute);
        allLayout = findViewById(R.id.all_layout);
        repeat_rl = findViewById(R.id.repeat_rl);
        repeat_rl.setOnClickListener(this);
        ring_rl = findViewById(R.id.ring_rl);
        ring_rl.setOnClickListener(this);
        tv_repeat_value =  findViewById(R.id.tv_repeat_value);
        tv_ring_value = findViewById(R.id.tv_ring_value);
        stv_remind = findViewById(R.id.stv_remind);
        tv_ring_value.setText(R.string.zdong);
        tv_repeat_value.setText(R.string.day);
        stv_remind.setSwitchCheckedChangeListener(new SuperTextView.OnSwitchCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                alarmBean.setState(b);
                LogUtils.i("stv_remind--- " +alarmState);
            }
        });
    }
    @Override
    public void initData() {
        Intent intent = getIntent();
        alarmBean = (AlarmBean) intent.getSerializableExtra("alarmBean");
        if(alarmBean == null){
            alarmBean= new AlarmBean();
            alarmBean.setHour(10);
            alarmBean.setMinute(30);
            alarmBean.setState(true);
            alarmBean.setCycle(0);
            alarmBean.setTips("闹钟响了");
            alarmBean.setSoundOrVibrator(0);
            alarmBean.setAlarmId(addAlarmId());
            alarmBean.setFlag(0);
            alarmBean.setWeeks(null);
        }

        npHour.setMinValue(0);
        npHour.setMaxValue(23);
        npHour.setValue(alarmBean.getHour());
        npHour.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                alarmBean.setHour(i1);
            }
        });
        npMinute.setMinValue(0);
        npMinute.setMaxValue(59);
        npMinute.setValue(alarmBean.getMinute());
        npMinute.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker numberPicker, int i, int i1) {
                alarmBean.setMinute(i1);
            }
        });
    }
    private void setClock() {
        alarmId = addAlarmId();
        if (alarmBean.getCycle() == 0) {//是每天的闹钟

            alarmBean.setFlag(0);

            if(alarmState)   //alarmState为true时添加闹钟 否则只添加到数据库
                AlarmManagerUtil.setAlarm(this, alarmBean.getFlag(),alarmBean.getHour(),
                        alarmBean.getMinute(), alarmBean.getAlarmId(), 0, alarmBean.getTips(), alarmBean.getSoundOrVibrator());

        } if(alarmBean.getCycle() == -1){//是只响一次的闹钟

            alarmBean.setFlag(1);

            if(alarmState)
                AlarmManagerUtil.setAlarm(this, alarmBean.getFlag(),alarmBean.getHour(),
                        alarmBean.getMinute(), alarmBean.getAlarmId(), 0, alarmBean.getTips(), alarmBean.getSoundOrVibrator());

        }else {//多选，周几的闹钟

            String weeksStr = parseRepeat(alarmBean.getCycle(), 1);
            String[] weeks = weeksStr.split(",");

            for (int i = 0; i < weeks.length; i++) {
                alarmBean.setFlag(2);
                if(alarmState)
                AlarmManagerUtil.setAlarm(this, alarmBean.getFlag(),alarmBean.getHour(),
                        alarmBean.getMinute(), alarmBean.getAlarmId(), Integer.parseInt(weeks[i]), alarmBean.getTips(), alarmBean.getSoundOrVibrator());
            }
        }
        saveAlarmBean();
    }

    //添加闹钟进数据库
    private void saveAlarmBean(){
        if(alarmBean.save()){
            Toast.makeText(this, "闹钟设置成功", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,AlarmActivity.class));
        }else{
            Toast.makeText(this, "缓存设置失败", Toast.LENGTH_LONG).show();
        }
    }

    private int addAlarmId(){
        Calendar Cld = Calendar.getInstance();
        int alarmId = (int)Cld.getTimeInMillis();
        LogUtils.i("------" + alarmId);
        return alarmId;
    }

    public void selectRemindCycle() {
        final SelectRemindCyclePopup fp = new SelectRemindCyclePopup(this);
        fp.showPopup(allLayout);
        fp.setOnSelectRemindCyclePopupListener(new SelectRemindCyclePopup
                .SelectRemindCyclePopupOnClickListener() {

            @Override
            public void obtainMessage(int flag, String ret) {
                switch (flag) {
                    // 星期一
                    case 0:

                        break;
                    // 星期二
                    case 1:

                        break;
                    // 星期三
                    case 2:

                        break;
                    // 星期四
                    case 3:

                        break;
                    // 星期五
                    case 4:

                        break;
                    // 星期六
                    case 5:

                        break;
                    // 星期日
                    case 6:

                        break;
                    // 确定
                    case 7:
                        int repeat = Integer.valueOf(ret);
                        tv_repeat_value.setText(parseRepeat(repeat, 0));
                        alarmBean.setCycle(repeat);
                        fp.dismiss();
                        break;
                    case 8:
                        tv_repeat_value.setText("每天");
                        alarmBean.setCycle(0);
                        fp.dismiss();
                        break;
                    case 9:
                        tv_repeat_value.setText("只响一次");
                        alarmBean.setCycle(-1);
                        fp.dismiss();
                        break;
                    default:
                        break;
                }
            }
        });
    }


    public void selectRingWay() {
        SelectRemindWayPopup fp = new SelectRemindWayPopup(this);
        fp.showPopup(allLayout);
        fp.setOnSelectRemindWayPopupListener(new SelectRemindWayPopup
                .SelectRemindWayPopupOnClickListener() {

            @Override
            public void obtainMessage(int flag) {
                switch (flag) {
                    // 震动
                    case 0:
                        tv_ring_value.setText("震动");
                        alarmBean.setSoundOrVibrator(0);
                        break;
                    // 铃声
                    case 1:
                        tv_ring_value.setText("铃声");
                        alarmBean.setSoundOrVibrator(1);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    /**
     * @param repeat 解析二进制闹钟周期
     * @param flag   flag=0返回带有汉字的周一，周二cycle等，flag=1,返回weeks(1,2,3)
     * @return
     */
    public static String parseRepeat(int repeat, int flag) {
        String cycle = "";
        String weeks = "";
        if (repeat == 0) {
            repeat = 127;
        }
        if (repeat % 2 == 1) {
            cycle = "周一";
            weeks = "1";
        }
        if (repeat % 4 >= 2) {
            if ("".equals(cycle)) {
                cycle = "周二";
                weeks = "2";
            } else {
                cycle = cycle + "," + "周二";
                weeks = weeks + "," + "2";
            }
        }
        if (repeat % 8 >= 4) {
            if ("".equals(cycle)) {
                cycle = "周三";
                weeks = "3";
            } else {
                cycle = cycle + "," + "周三";
                weeks = weeks + "," + "3";
            }
        }
        if (repeat % 16 >= 8) {
            if ("".equals(cycle)) {
                cycle = "周四";
                weeks = "4";
            } else {
                cycle = cycle + "," + "周四";
                weeks = weeks + "," + "4";
            }
        }
        if (repeat % 32 >= 16) {
            if ("".equals(cycle)) {
                cycle = "周五";
                weeks = "5";
            } else {
                cycle = cycle + "," + "周五";
                weeks = weeks + "," + "5";
            }
        }
        if (repeat % 64 >= 32) {
            if ("".equals(cycle)) {
                cycle = "周六";
                weeks = "6";
            } else {
                cycle = cycle + "," + "周六";
                weeks = weeks + "," + "6";
            }
        }
        if (repeat / 64 == 1) {
            if ("".equals(cycle)) {
                cycle = "周日";
                weeks = "7";
            } else {
                cycle = cycle + "," + "周日";
                weeks = weeks + "," + "7";
            }
        }
        if(flag == 0){
            alarmBean.setCycle(repeat);
             alarmBean.setWeeks(cycle);
        };
        return flag == 0 ? cycle : weeks;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.repeat_rl:
                selectRemindCycle();
                break;
            case R.id.ring_rl:
                selectRingWay();
                break;
            default:
                break;
        }
    }

    @Override
    public void getCenTitle(ImageView ivLeft, TextView tvTitle, TextView tvRight) {
        tvTitle.setText("添加闹钟");
        tvRight.setText("存储");
        tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setClock();
            }
        });
    }
}

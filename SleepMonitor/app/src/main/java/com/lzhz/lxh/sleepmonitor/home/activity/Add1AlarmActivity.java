package com.lzhz.lxh.sleepmonitor.home.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
import com.lzhz.lxh.sleepmonitor.home.activity.bean.AlarmBean;
import com.lzhz.lxh.sleepmonitor.home.activity.tools.SelectRemindCyclePopup;
import com.lzhz.lxh.sleepmonitor.home.activity.tools.SelectRemindWayPopup;
import com.lzhz.lxh.sleepmonitor.tools.LogUtils;
import com.lzhz.lxh.sleepmonitor.tools.view.WheelView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * 作者：lxh on 2018-01-06:15:20
 * 邮箱：15911638454@163.com
 * 添加闹钟
 */
public class Add1AlarmActivity extends AppCompatActivity implements View.OnClickListener{
    private RelativeLayout repeat_rl, ring_rl;
    private TextView tv_repeat_value, tv_ring_value;
    private TextView tv_right_text;
    private ImageView iv_left;
    private LinearLayout allLayout;
    private SuperTextView stv_remind;
    private boolean alarmState = true;  //闹钟是否启动
    private static  AlarmBean alarmBean;
    WheelView wheelView;
    WheelView wheelView1;
    ArrayList<Integer> alarmId = new ArrayList<>();
    boolean boo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_alarm_activity);
        initViews();
        initData();
    }
    public void initViews() {
        allLayout = findViewById(R.id.all_layout);
        repeat_rl = findViewById(R.id.repeat_rl);
        iv_left = findViewById(R.id.iv_left);
        tv_right_text = findViewById(R.id.tv_right_text);
        iv_left.setOnClickListener(this);
        tv_right_text.setOnClickListener(this);
        repeat_rl.setOnClickListener(this);
        ring_rl = findViewById(R.id.ring_rl);
        ring_rl.setOnClickListener(this);
        tv_repeat_value =  findViewById(R.id.tv_repeat_value);
        tv_ring_value = findViewById(R.id.tv_ring_value);
        stv_remind = findViewById(R.id.stv_remind);
        stv_remind.setSwitchCheckedChangeListener(new SuperTextView.OnSwitchCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                alarmBean.setState(b);
            }
        });
    }
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
            alarmId.add(addAlarmId());
            alarmBean.setAlarmIds(alarmId);
            alarmBean.setAlarmId(alarmId.get(0));
            alarmBean.setFlag(0);
            alarmBean.setWeeks("每天");
        }else{
            boo = true;
            tv_repeat_value.setText(alarmBean.getWeeks());
            stv_remind.setSwitchIsChecked(alarmBean.isState());
            if(alarmBean.getSoundOrVibrator() == 0){
                tv_ring_value.setText(R.string.zdong);
            }else {
                tv_ring_value.setText("铃声");
            }
        }


        wheelView= (WheelView) findViewById(R.id.wv_from_hour);
        wheelView.setData(getMinuteData(23));
        wheelView1= (WheelView) findViewById(R.id.wv_from_dd);
        wheelView1.setData(getMinuteData(59));
        wheelView.setDefault(alarmBean.getHour());
        wheelView1.setDefault(alarmBean.getMinute());
        //添加闹钟时间
        wheelView.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                alarmBean.setHour(id);
            }

            @Override
            public void selecting(int id, String text) {

            }
        });
        wheelView1.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                alarmBean.setMinute(id);
            }
            @Override
            public void selecting(int id, String text) {
            }
        });
    }
    private ArrayList<String> getMinuteData(int max) {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i <= max; i++) {
            list.add(i + "");
        }
        return list;
    }
    //设置闹钟
    private void setClock() {
        if (alarmBean.getCycle() == 0) {//是每天的闹钟

            alarmBean.setFlag(0);

            if(alarmState)   //alarmState为true时添加闹钟 否则只添加到数据库
                AlarmManagerUtil.setAlarm(this, alarmBean.getFlag(),alarmBean.getHour(),
                        alarmBean.getMinute(), alarmBean.getAlarmIds().get(0), 0, alarmBean.getTips(), alarmBean.getSoundOrVibrator());

        } if(alarmBean.getCycle() == -1){//是只响一次的闹钟

            alarmBean.setFlag(1);

            if(alarmState)
                AlarmManagerUtil.setAlarm(this, alarmBean.getFlag(),alarmBean.getHour(),
                        alarmBean.getMinute(), alarmBean.getAlarmIds().get(0), 0, alarmBean.getTips(), alarmBean.getSoundOrVibrator());

        }else {//多选，周几的闹钟

            String weeksStr = parseRepeat(alarmBean.getCycle(), 1);
            String[] weeks = weeksStr.split(",");

            for (int i = 0; i < weeks.length; i++) {
                alarmBean.setFlag(2);
                alarmId.add(addAlarmId());
                if(alarmState)
                AlarmManagerUtil.setAlarm(this, alarmBean.getFlag(),alarmBean.getHour(),
                        alarmBean.getMinute(), alarmBean.getAlarmIds().get(i), Integer.parseInt(weeks[i]), alarmBean.getTips(), alarmBean.getSoundOrVibrator());
                if(i!=0){

                }
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
        Random random = new Random();
        int alarmId = (int) System.currentTimeMillis() + random.nextInt(1000);
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
        switch (view.getId()) {
            case R.id.repeat_rl:
                selectRemindCycle();
                break;
            case R.id.ring_rl:
                selectRingWay();
                break;
            case R.id.iv_left:
                finish();
                break;
            case R.id.tv_right_text:
                //
                if(boo){
                    for (int i = 0;i<alarmBean.getAlarmIds().size();i++){
                        AlarmManagerUtil.cancelAlarm(this,AlarmManagerUtil.ALARM_ACTION,alarmBean.getAlarmIds().get(i));
                    }

                    ContentValues values = new ContentValues();
                    DataSupport.deleteAll( AlarmBean.class,"alarmId = ?",alarmBean.getAlarmId()+"");
                }
                setClock();
                break;
            default:
                break;
        }
    }


}

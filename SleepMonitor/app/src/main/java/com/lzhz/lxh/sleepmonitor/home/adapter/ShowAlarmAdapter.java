package com.lzhz.lxh.sleepmonitor.home.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.allen.library.SuperTextView;
import com.loonggg.lib.alarmmanager.clock.AlarmManagerUtil;
import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.adapter.CommonAdapter;
import com.lzhz.lxh.sleepmonitor.home.activity.Add1AlarmActivity;
import com.lzhz.lxh.sleepmonitor.home.activity.AlarmActivity;
import com.lzhz.lxh.sleepmonitor.home.activity.bean.AlarmBean;
import com.lzhz.lxh.sleepmonitor.home.bean.AddAlarmBean;
import com.lzhz.lxh.sleepmonitor.tools.LogUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.litepal.crud.DataSupport;

import java.util.List;

import static android.support.v7.widget.RecyclerView.*;

/**
 * 作者：lxh on 2018-01-06:10:03
 * 邮箱：15911638454@163.com
 */

public class ShowAlarmAdapter  extends CommonAdapter<RecyclerView.ViewHolder> {
    private List<AlarmBean> mDatas;

    public ShowAlarmAdapter(Context context, int layoutId,List<AlarmBean> mDatas) {
        super(context, layoutId, mDatas.size());
        this.mDatas = mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, mLayoutId, null);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        super.onBindViewHolder(holder, position);
        final  AlarmBean alarmBean = mDatas.get(position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        //myViewHolder.smrv.setLeftBottomString(mDatas.get(position).getAabTime());
        myViewHolder.smrv.setLeftString(getTime(alarmBean.getHour(),alarmBean.getMinute()));
        myViewHolder.smrv.setLeftBottomString(alarmBean.getWeeks() +"");
        myViewHolder.smrv.setSwitchIsChecked(alarmBean.isState());

        myViewHolder.smrv.setSwitchCheckedChangeListener(new SuperTextView.OnSwitchCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    //添加闹钟
                    addAlart(alarmBean);
                }else{
                    //删除
                    for (int i = 0;i<alarmBean.getAlarmIds().size();i++){
                        AlarmManagerUtil.cancelAlarm(mContext,AlarmManagerUtil.ALARM_ACTION,mDatas.get(position).getAlarmIds().get(i));
                    }
                }
                ContentValues values = new ContentValues();
                values.put("state",b);
                alarmBean.setState(b);
                DataSupport.updateAll(AlarmBean.class,values, "alarmId = ?",alarmBean.getAlarmId()+"");
            }
        });

        myViewHolder.smrv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext,Add1AlarmActivity.class);
                Bundle mBundle = new Bundle();
                mBundle.putSerializable("alarmBean",mDatas.get(position));
                intent.putExtras(mBundle);
                mContext.startActivity(intent);
            }
        });
    }
    private String getTime(int hour,int minute){
        String time;
        time = hour<10? "0"+hour:""+hour;
        time = minute<10? time + "："+"0"+minute:time + "："+minute;
        return time;
    }

    private void addAlart(AlarmBean bean) {
        if (bean.getCycle() == 0 || bean.getCycle() == -1) {//是每天的闹钟

            for (int i = 0;i<bean.getAlarmIds().size();i++){
                AlarmManagerUtil.setAlarm(mContext, bean.getFlag(),bean.getHour(),
                        bean.getMinute(),bean.getAlarmIds().get(0), 0, bean.getTips(),bean.getSoundOrVibrator());
            }

        } else {//多选，周几的闹钟
            String weeksStr = AlarmManagerUtil.parseRepeat(bean.getCycle(), 1);
            String[] weeks = weeksStr.split(",");
            for (int i = 0; i < weeks.length; i++) {
                AlarmManagerUtil.setAlarm(mContext, bean.getFlag(),bean.getHour(),
                        bean.getMinute(),bean.getAlarmIds().get(i), Integer.parseInt(weeks[i]), bean.getTips(),bean.getSoundOrVibrator());
            }
        }
    }
     class MyViewHolder extends RecyclerView.ViewHolder{
       private SuperTextView smrv;


        public MyViewHolder(View itemView) {
            super(itemView);
            smrv = itemView.findViewById(R.id.alipay_stv);
        }
    }
}

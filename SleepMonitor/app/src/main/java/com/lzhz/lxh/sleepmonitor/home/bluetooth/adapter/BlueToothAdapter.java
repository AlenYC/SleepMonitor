package com.lzhz.lxh.sleepmonitor.home.bluetooth.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allen.library.SuperTextView;
import com.inuker.bluetooth.library.search.SearchResult;
import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.adapter.CommonAdapter;
import com.lzhz.lxh.sleepmonitor.home.bean.AddAlarmBean;
import com.lzhz.lxh.sleepmonitor.tools.LogUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * 作者：lxh on 2018-01-06:10:03
 * 邮箱：15911638454@163.com
 */

public class BlueToothAdapter extends CommonAdapter<ViewHolder> {
    private static List<SearchResult> mDatas = new ArrayList<>();

    public BlueToothAdapter(Context context, int layoutId) {
        super(context, layoutId, mDatas.size());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(mContext, mLayoutId, null);
        MyViewHolder holder = new MyViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        //myViewHolder.smrv.setLeftBottomString(mDatas.get(position).getAabTime());
        myViewHolder.smrv.setLeftString(mDatas.get(position).getName());
    }
    public void setDataList(List<SearchResult> data) {
        mDatas.clear();
        mDatas.addAll(data);
        LogUtils.i("----mDatas-------"+mDatas.size());
        LogUtils.i("----data-------"+data.size());
        notifyDataSetChanged();
    }

     class MyViewHolder extends ViewHolder{
       private SuperTextView smrv;


        public MyViewHolder(View itemView) {
            super(itemView);
            smrv = itemView.findViewById(R.id.alipay_stv);
        }
    }
}

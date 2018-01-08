package com.lzhz.lxh.sleepmonitor.relatives.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.allen.library.SuperTextView;
import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.adapter.CommonAdapter;
import com.lzhz.lxh.sleepmonitor.home.bean.AddAlarmBean;
import com.lzhz.lxh.sleepmonitor.relatives.entity.InformationBean;

import java.util.List;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * 作者：lxh on 2018-01-06:10:03
 * 邮箱：15911638454@163.com
 */

public class InformationAdapter extends CommonAdapter<ViewHolder> {
    private List<InformationBean> mDatas;

    public InformationAdapter(Context context, int layoutId, List<InformationBean> mDatas) {
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        MyViewHolder myViewHolder = (MyViewHolder) holder;
        //myViewHolder.smrv.setLeftBottomString(mDatas.get(position).getAabTime());
        myViewHolder.smrv.setLeftString(mDatas.get(position).getUserName());
        myViewHolder.smrv.setLeftBottomString(mDatas.get(position).getDescribe());
        myViewHolder.smrv.setRightString(mDatas.get(position).getTime());
    }

     class MyViewHolder extends ViewHolder{
       private SuperTextView smrv;


        public MyViewHolder(View itemView) {
            super(itemView);
            smrv = itemView.findViewById(R.id.alipay_stv);
        }
    }
}

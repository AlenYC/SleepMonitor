package com.lzhz.lxh.sleepmonitor.home.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.allen.library.SuperTextView;
import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.adapter.CommonAdapter;
import com.lzhz.lxh.sleepmonitor.home.bean.AddAlarmBean;
import com.lzhz.lxh.sleepmonitor.home.bean.HomeLinsBean;
import com.lzhz.lxh.sleepmonitor.tools.view.LinsView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static android.support.v7.widget.RecyclerView.ViewHolder;

/**
 * 作者：lxh on 2018-01-06:10:03
 * 邮箱：15911638454@163.com
 */

public class HomeListAdapter extends CommonAdapter<ViewHolder> {
    private ArrayList<HomeLinsBean> mDatas;

    public HomeListAdapter(Context context, int layoutId, ArrayList<HomeLinsBean> mDatas) {
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
        if(mDatas.get(position).getLinsState() == 0){
            myViewHolder.imageView.setImageResource(R.mipmap.qingyou_shuimiang3x);
            myViewHolder.progressBar.setVisibility(View.GONE);
            myViewHolder.linsView.setVisibility(View.VISIBLE);
        } else if(mDatas.get(position).getLinsState() == 1){
            myViewHolder.imageView.setImageResource(R.mipmap.shendushuimiang3x);
            myViewHolder.progressBar.setVisibility(View.VISIBLE);
            myViewHolder.progressBar.setProgress(new Random().nextInt(100));
            myViewHolder.linsView.setVisibility(View.GONE);
        } else{
            myViewHolder.imageView.setImageResource(R.mipmap.zhishu3x);
        }

        myViewHolder.linsView.setList(mDatas);
    }

     class MyViewHolder extends ViewHolder{
       private ImageView imageView;
       private LinsView linsView;
       private ProgressBar progressBar;

        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.iv_home_top);
            linsView = itemView.findViewById(R.id.lv_home);
            progressBar = itemView.findViewById(R.id.sb_jindu);
        }
    }
}

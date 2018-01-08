package com.lzhz.lxh.sleepmonitor.base.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：lxh on 2018-01-06:10:29
 * 邮箱：15911638454@163.com
 * 添加RecyclerView点击事件
 */

public abstract class CommonAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T>{
    protected final Context mContext;
    protected final int mLayoutId;
    protected int mNum;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    public CommonAdapter(Context context, int layoutId, int num) {
        mContext = context;
        mLayoutId = layoutId;
        mNum = num;
    }

    @Override
    public abstract T onCreateViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(T holder, int position) {
        holder.itemView.setOnClickListener(new OnClickListener(position, mOnItemClickListener));
    }

    @Override
    public int getItemCount() {
        return mNum;
    }

    public static class OnClickListener implements View.OnClickListener{
        final int mPosition;
        final OnRecyclerViewItemClickListener mListener;

        public OnClickListener(int i, OnRecyclerViewItemClickListener listener) {
            mPosition = i;
            mListener = listener;
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onItemClick(v, mPosition);
            }
        }
    }

    // 用来设置每个item的接听
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }
}

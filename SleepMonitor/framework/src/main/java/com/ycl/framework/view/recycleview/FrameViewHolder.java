package com.ycl.framework.view.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * frame ViewHolder on 2015/11/12.
 */
public abstract class FrameViewHolder extends RecyclerView.ViewHolder {

    public FrameViewHolder(View itemView, int type) {
        super(itemView);
        initHolderViews(type);
    }

    protected abstract void initHolderViews(int viewType);

    public static Context getAppContext(FrameViewHolder holder) {
        return holder.itemView.getContext().getApplicationContext();
    }


}

package com.ycl.framework.view.recycleview;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ycl.framework.utils.util.ViewHolderUtil;

import java.util.List;

/**
 * recycle adapter（Multi） on 2015/11/5.
 */
public abstract class MultiRecycleTypesAdapter<VHoder extends RecyclerView.ViewHolder, T> extends RecyclerView.Adapter<VHoder> {

    protected View mHeaderView;
    protected View customLoadMoreView;  //加载脚
    protected List<T> datas;

    protected boolean mIsLoadMoreWidgetEnabled = false;//能否自动加载（true 不能加载）

    //view的Type类型
    public static class VIEW_TYPES {
        public static final int EMPTY_FOOTER = -2;
        public static final int TYPE_HEADER = -1;
        public static final int TYPE_FOOTER = 0;
        public static final int TYPE_NORMAL_1 = 1;
        public static final int TYPE_NORMAL_2 = 2;
        public static final int TYPE_NORMAL_3 = 3;
        public static final int TYPE_NORMAL_4 = 4;
    }

    @Override
    public VHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPES.TYPE_HEADER) {
            return getViewHolder(mHeaderView, viewType);
        } else if (viewType == VIEW_TYPES.EMPTY_FOOTER) {
            return getViewHolder(customLoadMoreView, viewType);
        } else if (viewType == VIEW_TYPES.TYPE_FOOTER) {
            return getViewHolder(customLoadMoreView, viewType);
        } else {
            return createCustomViewHolder(parent, viewType);
        }
    }


    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1 && customLoadMoreView != null) {
            if (mIsLoadMoreWidgetEnabled) {
                return VIEW_TYPES.EMPTY_FOOTER;
            }
            return VIEW_TYPES.TYPE_FOOTER;
        } else if (position == 0 && mHeaderView != null) {
            return VIEW_TYPES.TYPE_HEADER;
        } else {
            if (mHeaderView != null)
                position--;
            return getTypeForPosition(position);
        }

    }

    @Override
    public int getItemCount() {

        int itemCount = 0;
        if (datas != null) itemCount = itemCount + datas.size();
        if (mHeaderView != null) itemCount++;
        if (customLoadMoreView != null) itemCount++;
        return itemCount;
    }

    //数据长度
    public int getDataSize() {
        return datas != null ? datas.size() : 0;
    }

    /**
     * 获取 ContentItem类型type  ()
     *
     * @param position 考虑到有headerView   postion只使用于 判断ContentView 所需要的类型
     * @return viewType  (int)VIEW_TYPES
     */

    public abstract int getTypeForPosition(int position);


    //header 或者 header  特殊的 item   获取 getViewHolder
    public abstract VHoder getViewHolder(View views, int viewType);

    /**
     * 获取 子类 内容的 ViewHolder
     *
     * @param parent 获取context
     * @return viewType  (int)VIEW_TYPES
     */
    public abstract VHoder createCustomViewHolder(ViewGroup parent, int viewType); //创建 内容 ViewHolder

    public void setmHeaderView(View mHeaderView) {
        this.mHeaderView = mHeaderView;
    }

    public View getmHeaderView() {
        return mHeaderView;
    }

    /**
     * 获取数据  item
     *
     * @param position index标志位  （去除 headerView）
     */
    public T getItemDats(int position) {
        if (mHeaderView != null)
            return datas.get(position - 1);
        return datas.get(position);
    }

    public View getCustomLoadMoreView() {
        return customLoadMoreView;
    }

    public void setCustomLoadMoreView(View customLoadMoreView) {
        this.customLoadMoreView = customLoadMoreView;
    }

    public boolean ismIsLoadMoreWidgetEnabled() {
        return mIsLoadMoreWidgetEnabled;
    }

    public void setmIsLoadMoreWidgetEnabled(boolean mIsLoadMoreWidgetEnabled) {
        this.mIsLoadMoreWidgetEnabled = mIsLoadMoreWidgetEnabled;
    }

    /**
     * adapter 适配器 数据长度 count
     */
    public int getAdapterItemCount() {
        return datas.size();
    }

    /**
     * Insert a item to the list of the adapter
     *
     * @param object   object T 类型
     * @param position position
     */
    public void insert(T object, int position) {
        datas.add(position, object);
        if (mHeaderView != null) position++;
        notifyItemInserted(position);
    }

    public void insertRange(List<T> extraDatas) {
        int a = 0;
        if (mHeaderView != null) a++;
        a += datas.size();
        datas.addAll(extraDatas);
        notifyItemRangeInserted(a, extraDatas.size());
    }

    //删除
    public void deteleItem(int position) {
        int index = position;
        if (mHeaderView != null) index--;  //去除 headerview
        notifyItemRemoved(position);
        datas.remove(index);
    }


    //set数据
    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

    public List<T> getDatas() {
        return datas;
    }

    //获取Last T；最后一条数据
    public T getLastDatas() {
        if (datas != null && datas.size() > 0)
            return datas.get(datas.size() - 1);
        return null;
    }

    //提取  公用Glide加载 Mothed
    protected void commonLoadImag(String url, ImageView iv, int type) {
        if (type == 0) {
            ViewHolderUtil.loadSizeUrlCrop(url, iv);  //裁切
        } else
            ViewHolderUtil.loadSizeUrlFit(url, iv);
    }
}

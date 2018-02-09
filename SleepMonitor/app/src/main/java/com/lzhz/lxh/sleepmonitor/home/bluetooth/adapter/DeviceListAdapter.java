package com.lzhz.lxh.sleepmonitor.home.bluetooth.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.allen.library.SuperTextView;
import com.inuker.bluetooth.library.search.SearchResult;
import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.home.bluetooth.activity.DeviceDetailActivity;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dingjikerbo on 2016/9/1.
 */
public class DeviceListAdapter extends RecyclerView.Adapter<DeviceListAdapter.MyViewHolder>{

    private static List<SearchResult> mDatas;
    private Context mContext;
    private LayoutInflater inflater;

    public DeviceListAdapter(Context context){
        this. mContext=context;
        mDatas = new ArrayList<>();
        inflater=LayoutInflater. from(mContext);
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    //填充onCreateViewHolder方法返回的holder中的控件
    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        MyViewHolder myViewHolder = holder;
        myViewHolder.smrv.setLeftString(mDatas.get(position).getName());

        myViewHolder.smrv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, DeviceDetailActivity.class);
                intent.putExtra("mac", mDatas.get(position).getAddress());
                mContext.startActivity(intent);
            }
        });
    }

    //重写onCreateViewHolder方法，返回一个自定义的ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = inflater.inflate(R.layout.mb_bluetooth_list,parent, false);
        MyViewHolder holder= new MyViewHolder(view);
        return holder;
    }

    public void setDataList(List<SearchResult> data) {
        mDatas.clear();
        mDatas.addAll(data);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        SuperTextView smrv;

        public MyViewHolder(View view) {
            super(view);
            smrv = itemView.findViewById(R.id.buletooth_stv);
        }

    }
}

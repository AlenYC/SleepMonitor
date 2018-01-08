package com.lzhz.lxh.sleepmonitor.relatives;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.adapter.BaseRecyclerAdapter;
import com.lzhz.lxh.sleepmonitor.base.adapter.BaseRecyclerHolder;
import com.lzhz.lxh.sleepmonitor.relatives.entity.RelativesBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：lxh on 2018-01-04:18:07
 * 邮箱：15911638454@163.com
 * 亲友页面
 */

public class RelativesFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.rel_list)
    RecyclerView relList;
    private List<RelativesBean> list;
    private BaseRecyclerAdapter<RelativesBean> adapter;
    Context context;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_relatives, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }
    private void init(){
        context = getActivity();
        list = new ArrayList<>();
        initData();

        adapter = new BaseRecyclerAdapter<RelativesBean>(context,list,R.layout.mb_relatives_list) {
            @Override
            public void convert(BaseRecyclerHolder holder, RelativesBean item, int position, boolean isScrolling) {
                holder.setText(R.id.tv_name,item.name);


            }

        };
        relList.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        relList.setAdapter(adapter);
    }

    private void initData() {
        list.add(new RelativesBean("爸爸"));
        list.add(new RelativesBean("女儿"));
        list.add(new RelativesBean("儿子"));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

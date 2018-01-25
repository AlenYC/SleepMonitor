package com.lzhz.lxh.sleepmonitor.relatives;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.adapter.BaseRecyclerAdapter;
import com.lzhz.lxh.sleepmonitor.base.adapter.BaseRecyclerHolder;
import com.lzhz.lxh.sleepmonitor.home.bean.HomeLinsBean;
import com.lzhz.lxh.sleepmonitor.relatives.entity.RelativesBean;
import com.lzhz.lxh.sleepmonitor.tools.view.LinsView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    @BindView(R.id.tl_relat)
    Toolbar toolbar;
    private List<RelativesBean> list;
    private ArrayList<HomeLinsBean> listLins;
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

    private void init() {
        TextView title = toolbar.findViewById(R.id.toolbar_title);
        ImageView rightTop = toolbar.findViewById(R.id.iv_right_top);
        title.setText(R.string.qingy);
        rightTop.setVisibility(View.VISIBLE);
        rightTop.setImageResource(R.mipmap.heart_message_icon3x);

        rightTop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().startActivity(new Intent(getActivity(), InformationActivity.class));
            }
        });
        context = getActivity();
        listLins = new ArrayList<>();
        initData();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            if (i % 3 == 0)
                listLins.add(new HomeLinsBean(random.nextInt(100), 2));
            else if (i % 2 == 0) {
                listLins.add(new HomeLinsBean(random.nextInt(100), 1));
            } else {
                listLins.add(new HomeLinsBean(random.nextInt(100), 0));
            }
        }
        adapter = new BaseRecyclerAdapter<RelativesBean>(context, list, R.layout.mb_relatives_list) {
            @Override
            public void convert(BaseRecyclerHolder holder, RelativesBean item, int position, boolean isScrolling) {
                holder.setText(R.id.tv_name, item.name);
                LinsView linsView = holder.getView(R.id.lv_rela);
                linsView.setList(listLins);
            }

        };
        relList.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        relList.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView parent, View view, int position) {

            }
        });
    }

    private void initData() {
        list = new ArrayList<>();
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

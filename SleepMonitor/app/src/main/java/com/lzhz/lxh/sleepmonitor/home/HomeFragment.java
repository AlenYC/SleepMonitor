package com.lzhz.lxh.sleepmonitor.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.home.activity.AlarmActivity;
import com.lzhz.lxh.sleepmonitor.home.adapter.HomeListAdapter;
import com.lzhz.lxh.sleepmonitor.home.bean.HomeLinsBean;
import com.lzhz.lxh.sleepmonitor.home.bluetooth.BlueToothActivity;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 * 作者：lxh on 2018-01-04:18:07
 * 邮箱：15911638454@163.com
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.ib_alarm)
    ImageView ibAlarm;
    Unbinder unbinder;
    @BindView(R.id.ib_buletooth)
    ImageView ibBuletooth;
    @BindView(R.id.rv_home_list)
    RecyclerView rvHomeList;
    HomeListAdapter homeListAdapter;
    @BindView(R.id.bt_ljjc)
    Button btLjjc;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(
                R.layout.home_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        ibAlarm.setOnClickListener(this);
        ibBuletooth.setOnClickListener(this);
        ArrayList<HomeLinsBean> list = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            if (i % 3 == 0)
                list.add(new HomeLinsBean(random.nextInt(100), 2));
            else if (i % 2 == 0) {
                list.add(new HomeLinsBean(random.nextInt(100), 1));
            } else {
                list.add(new HomeLinsBean(random.nextInt(100), 0));
            }
        }

        rvHomeList.setLayoutManager(new LinearLayoutManager(getActivity(), VERTICAL, false));
        homeListAdapter = new HomeListAdapter(getActivity(), R.layout.mb_home_list, list);
        rvHomeList.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        rvHomeList.setAdapter(homeListAdapter);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ib_alarm:
                Intent intent = new Intent(getActivity(), AlarmActivity.class);
                startActivity(intent);
                break;
            case R.id.ib_buletooth:

                startActivity(new Intent(getActivity(), BlueToothActivity.class));
                break;
                case R.id.bt_ljjc:

                startActivity(new Intent(getActivity(), BlueToothActivity.class));
                break;
        }
    }
}

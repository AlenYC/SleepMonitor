package com.lzhz.lxh.sleepmonitor.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.home.activity.AlarmActivity;
import com.lzhz.lxh.sleepmonitor.home.bluetooth.BlueToothActivity;
import com.lzhz.lxh.sleepmonitor.relatives.InformationActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

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
        }
    }
}

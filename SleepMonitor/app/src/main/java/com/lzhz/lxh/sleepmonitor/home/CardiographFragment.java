package com.lzhz.lxh.sleepmonitor.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lzhz.lxh.sleepmonitor.MainActivity;
import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.tools.view.Cardiograph1View;
import butterknife.ButterKnife;
import butterknife.Unbinder;
/**
 * 作者：lxh on 2018-03-02:19:00
 * 邮箱：15911638454@163.com
 */

public class CardiographFragment extends Fragment {
    Unbinder unbinder;
    Cardiograph1View cvXdt;
    MainActivity mainActivity;
    double raw_data[] = new double[30000];
    double hbrRes[]= new double[2];
    double HD[]= new double[300];
    double RD[]= new double[300];
    double snore[]= new double[3000];
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_cardiograph_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        mainActivity = (MainActivity) getActivity();
        init(view);
        setData();
        return view;
    }

    public void init(View view){
        cvXdt = view.findViewById(R.id.cv_xdt);
    }
    public void setData(){
        for (int i = 0;i < RD.length;i++){
            RD[i] = Math.random()*400;
        }
        cvXdt.setData(hbrRes,HD,RD,snore);
    }
}

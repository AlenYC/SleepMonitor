package com.lzhz.lxh.sleepmonitor.analyzed;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.lzhz.lxh.sleepmonitor.MainActivity;
import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.analyzed.bean.AnalyzedViewBean;
import com.lzhz.lxh.sleepmonitor.tools.ToastUtil;
import com.lzhz.lxh.sleepmonitor.tools.view.ChartView;
import com.lzhz.lxh.sleepmonitor.tools.view.RectangleView;
import com.lzhz.lxh.sleepmonitor.tools.view.RoundWireView;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：lxh on 2018-01-04:18:07
 * 邮箱：15911638454@163.com
 * 分析详情
 */

public class AnalyzeDetailsFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = AnalyzeDetailsFragment.class.getSimpleName();
    Unbinder unbinder;
    ArrayList<Point> pointList;
    int[] count;
    int[] count1;
    String[] date = {"5/21", "5/22", "5/23", "5/24", "5/25", "5/26", "5/27", "5/28", "5/29", "5/30",};

    @BindView(R.id.rectangle)
    RectangleView rectangle;
    ArrayList<AnalyzedViewBean> list;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rwv_round)
    RoundWireView rwvRound;
    @BindView(R.id.rwv_round1)
    RoundWireView rwvRound1;
    @BindView(R.id.cv_line)
    ChartView cvLine;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    TabLayout toolbar_tab;
    MainActivity mainActivity;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(
                R.layout.analyze_details_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        mainActivity = (MainActivity) getActivity();
        init();
        setDate(100);
        return view;
    }


    private void init() {
        ivLeft.setOnClickListener(this);
        toolbar_tab = toolbar.findViewById(R.id.toolbar_tab);
        toolbar_tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                Log.i(TAG,"postion======" + position);
                switch (position){
                    case 0:
                        setDate(150);
                        break;
                    case 1:
                        setDate(200);
                        break;
                    case 2:
                        setDate(220);
                        break;
                    case 3:
                        setDate(250);
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        toolbar_tab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int i = (int) view.getTag();
                ToastUtil.showShort(mainActivity,""+i);
                switch (view.getId()) {
                    case R.id.ti_day:
                        setDate(150);
                        break;
                    case R.id.ti_week:
                        setDate(200);
                        break;
                    case R.id.ti_monch:
                        setDate(220);
                        break;
                    case R.id.ti_year:
                        setDate(250);
                        break;
                }
            }
        });
    }

    private void setDate(int fanw) {

        Random random = new Random();
        count = new int[10];
        count1 = new int[10];
        for (int i = 0; i < 10; i++) {
            count[i] = random.nextInt(100);
            count1[i] = random.nextInt(100);
        }
        //给ChartView设置坐标
        cvLine.setCount(count, date);
        rwvRound.setCount(count, date);
        rwvRound1.setCount(count1, date);


        list = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ArrayList<Integer> inte = new ArrayList<>();
            inte.add(random.nextInt(fanw));
            inte.add(random.nextInt(fanw));
            inte.add(random.nextInt(fanw));
            list.add(new AnalyzedViewBean(inte, "5/26"));
        }
        rectangle.setList(list);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_left:
                mainActivity.setAnalyze();
                break;
        }

    }
}

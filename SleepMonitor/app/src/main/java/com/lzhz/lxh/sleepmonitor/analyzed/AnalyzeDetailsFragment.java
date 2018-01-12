package com.lzhz.lxh.sleepmonitor.analyzed;

import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.analyzed.bean.AnalyzedViewBean;
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
    Unbinder unbinder;
    ArrayList<Point> pointList;
    int[] count;
    int[] count1;
    String[] date = {"5/21", "5/22", "5/23", "5/24", "5/25", "5/26", "5/27", "5/28", "5/29", "5/30",};

    @BindView(R.id.rectangle)
    RectangleView rectangle;
    ArrayList<AnalyzedViewBean> list;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.rwv_round)
    RoundWireView rwvRound;
    @BindView(R.id.rwv_round1)
    RoundWireView rwvRound1;
    @BindView(R.id.cv_line)
    ChartView cvLine;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(
                R.layout.analyze_details_fragment_layout, container, false);
        unbinder = ButterKnife.bind(this, view);

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
            inte.add(random.nextInt(100));
            inte.add(random.nextInt(100));
            inte.add(random.nextInt(100));
            list.add(new AnalyzedViewBean(inte, "5/26"));
        }
        rectangle.setList(list);

        return view;


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
        }
    }
}

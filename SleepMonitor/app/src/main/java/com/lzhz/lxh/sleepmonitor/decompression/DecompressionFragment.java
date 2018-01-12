package com.lzhz.lxh.sleepmonitor.decompression;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lzhz.lxh.sleepmonitor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：lxh on 2018-01-05:15:53
 * 邮箱：15911638454@163.com
 * 解压
 */

public class DecompressionFragment extends Fragment {
    Unbinder unbinder;
    @BindView(R.id.tl_relat)
    Toolbar tlRelat;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.fragment_decompression_layout, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        TextView title = tlRelat.findViewById(R.id.toolbar_title);
        title.setText("解压");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

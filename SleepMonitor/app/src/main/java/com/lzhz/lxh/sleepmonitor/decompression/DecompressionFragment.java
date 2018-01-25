package com.lzhz.lxh.sleepmonitor.decompression;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
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
    @BindView(R.id.seekbar)
    SeekBar seekbar;
    @BindView(R.id.seekbar1)
    SeekBar seekbar1;
    private int newProgress = 0;

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
        seekbar.setOnSeekBarChangeListener(seekBarChangeListener);
        seekbar1.setOnSeekBarChangeListener(seekBarChangeListener);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    private SeekBar.OnSeekBarChangeListener seekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            newProgress = progress;
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            if (newProgress <= 28) {
                newProgress = 0;
                seekBar.setProgress(0);
                return;
            }
            if (newProgress >= 73) {
                newProgress = 100;
                seekBar.setProgress(100);
                return;
            }
            if (newProgress > 28 && newProgress < 73) {
                newProgress = 50;
                seekBar.setProgress(50);
                return;
            }
        }
    };
}

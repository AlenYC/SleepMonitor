package com.lzhz.lxh.sleepmonitor.login.fragmen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzhz.lxh.sleepmonitor.R;

import butterknife.ButterKnife;

/**
 * 作者：lxh on 2018-01-06:16:50
 * 邮箱：15911638454@163.com
 * 登录
 */

public class LoginFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.register_fragment_layout, container, false);

        return view;
    }
}

package com.lzhz.lxh.sleepmonitor.login.fragmen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.lzhz.lxh.sleepmonitor.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：lxh on 2018-01-06:16:50
 * 邮箱：15911638454@163.com
 * 登录
 */

public class LoginFragment extends Fragment implements View.OnClickListener{
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.bt_ss)
    TextView btSs;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.tv_count_down)
    TextView tvCountDown;
    Unbinder unbinder;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(
                R.layout.register_fragment_layout, container, false);

        unbinder = ButterKnife.bind(this, view);
        return view;
    }
    private void init(){
        btSs.setOnClickListener(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_ss:
                break;
        }
    }
}

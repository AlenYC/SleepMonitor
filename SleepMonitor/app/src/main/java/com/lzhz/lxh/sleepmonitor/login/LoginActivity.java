package com.lzhz.lxh.sleepmonitor.login;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.BaseActivity;
import com.lzhz.lxh.sleepmonitor.login.fragmen.LoginFragment;
import com.lzhz.lxh.sleepmonitor.login.fragmen.RegisterFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends BaseActivity implements View.OnClickListener{


    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.bt_register)
    Button btRegister;
    @BindView(R.id.fl_all)
    FrameLayout flAll;
    Fragment currentFragment;
    LoginFragment loginFragment;
    RegisterFragment registerFragment;
    @Override
    public void setRootView() {
        setContentView(R.layout.login_activity);
    }

    @Override
    public void initViews() {
        btRegister.setOnClickListener(this);
        btLogin.setOnClickListener(this);
    }

    @Override
    public void initData() {
        loginFragment = new LoginFragment();
        registerFragment = new RegisterFragment();
        getSupportFragmentManager()
                .beginTransaction().add(R.id.fl_all,loginFragment).commit();
        currentFragment = loginFragment;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_login:
                switchFragment(loginFragment);
                break;
            case R.id.bt_register:
                switchFragment(registerFragment);
                break;
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            transaction
                    .hide(currentFragment)
                    .add(R.id.fl_all, targetFragment)
                    .commit();
        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment)
                    .commit();
        }
        currentFragment = targetFragment;
    }


}

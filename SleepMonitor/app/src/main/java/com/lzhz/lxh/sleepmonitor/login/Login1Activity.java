package com.lzhz.lxh.sleepmonitor.login;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzhz.lxh.sleepmonitor.MainActivity;
import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.SleepMonitorApplication;
import com.lzhz.lxh.sleepmonitor.base.BaseActivity;
import com.lzhz.lxh.sleepmonitor.tools.Constance;
import com.lzhz.lxh.sleepmonitor.tools.LogUtils;
import com.lzhz.lxh.sleepmonitor.tools.Net.NetCallBack;
import com.lzhz.lxh.sleepmonitor.tools.Net.NetParamas;
import com.lzhz.lxh.sleepmonitor.tools.Net.NetUtil;
import com.lzhz.lxh.sleepmonitor.tools.PersissionUtils;
import com.lzhz.lxh.sleepmonitor.tools.SPUtil;
import com.lzhz.lxh.sleepmonitor.tools.Tool;
import com.lzhz.lxh.sleepmonitor.tools.interfacetool.PermissionInter;

import org.json.JSONException;
import org.json.JSONObject;

import me.weyye.hipermission.PermissionItem;

/**
 * Created by dk on 2017/6/12.
 */

public class Login1Activity extends BaseActivity implements View.OnClickListener ,PermissionInter {
    private ImageView iv_login, iv_regis;
    private View layout_regis, layout_login;
    private EditText et_psw, et_tel, et_regis_tel, et_regis_psw, et_regis_re_psw, et_regis_yzm;
    private TextView tv_yzm;
    private SPUtil spUtil;
    private Boolean initrnet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spUtil = SPUtil.getInstance(this);
        initView();
    }

    private void initView() {
        PersissionUtils.setOnPermissionInter(this);
        PermissionItem permissionItem = new PermissionItem(Manifest.permission.INTERNET, "网络权限", R.drawable.permission_ic_location);
        PersissionUtils.setPermission(this, permissionItem);

        findViewById(R.id.tv_login).setOnClickListener(this);
        findViewById(R.id.tv_regis).setOnClickListener(this);
        findViewById(R.id.bt_login).setOnClickListener(this);
        findViewById(R.id.bt_regis).setOnClickListener(this);
        findViewById(R.id.tv_get_yzm).setOnClickListener(this);
        findViewById(R.id.forget_psw).setOnClickListener(this);
        iv_login = (ImageView) findViewById(R.id.iv_login);
        iv_regis = (ImageView) findViewById(R.id.iv_regis);
        layout_regis = findViewById(R.id.layout_regis);
        layout_login = findViewById(R.id.layout_login);
        et_psw = (EditText) findViewById(R.id.et_psw);
        et_tel = (EditText) findViewById(R.id.et_tel);
        et_regis_psw = (EditText) findViewById(R.id.et_regis_psw);
        et_regis_tel = (EditText) findViewById(R.id.et_regis_tel);
        et_regis_re_psw = (EditText) findViewById(R.id.et_regis_re_psw);
        et_regis_yzm = (EditText) findViewById(R.id.et_regis_yzm);
        tv_yzm = (TextView) findViewById(R.id.tv_get_yzm);
        String tel = spUtil.getString(Constance.KEY_TEL);
        String psw = spUtil.getString(Constance.KEY_PSW);
        if (!TextUtils.isEmpty(tel) && !TextUtils.isEmpty(psw)) {
            et_tel.setText(tel);
//            et_psw.setText(psw);
            login(tel, psw);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_login:
                iv_login.setVisibility(View.VISIBLE);
                layout_login.setVisibility(View.VISIBLE);
                iv_regis.setVisibility(View.INVISIBLE);
                layout_regis.setVisibility(View.INVISIBLE);
                break;
            case R.id.tv_regis:
                iv_regis.setVisibility(View.VISIBLE);
                layout_regis.setVisibility(View.VISIBLE);
                iv_login.setVisibility(View.INVISIBLE);
                layout_login.setVisibility(View.INVISIBLE);
                break;
            case R.id.bt_login:
                final String tel = et_tel.getText().toString().trim();
                if (TextUtils.isEmpty(tel)) {
                    Tool.showToast(this, getString(R.string.pls_input_tel));
                    return;
                }
                final String psw = et_psw.getText().toString().trim();
                if (TextUtils.isEmpty(psw)) {
                    Tool.showToast(this, getString(R.string.pls_input_psw));
                    return;
                }
                login(tel, Tool.getMD5(psw));
                break;
            case R.id.bt_regis:
                register();
                break;
            case R.id.tv_get_yzm:
                yzm();
                break;
            case R.id.forget_psw:
                startActivity(new Intent(this, ResetPswActivity.class));
                break;
        }
    }


    private CountDownTimer countDownTimer = new CountDownTimer(60000, 1000) {
        @Override
        public void onTick(long millisUntilFinished) {
            tv_yzm.setText(millisUntilFinished / 1000 + "S");
        }

        @Override
        public void onFinish() {
            tv_yzm.setText(getString(R.string.get_yzm));
            tv_yzm.setClickable(true);
        }
    };

    private void yzm() {
        String tel = et_regis_tel.getText().toString().trim();
        if (TextUtils.isEmpty(tel)) {
            Tool.showToast(this, getString(R.string.pls_input_tel));
            return;
        }
        if (!tel.matches(Constance.REG_TEL)) {
            Tool.showToast(this, getString(R.string.tel_error));
            return;
        }
        NetParamas netParamas = new NetParamas();
        netParamas.put("phone", tel);
        netParamas.put("type", "1");
        NetUtil.get(Constance.HTTP_GET_YZM, netParamas, new NetCallBack() {
            @Override
            public void onResponse(JSONObject result) {
                Tool.showToast(Login1Activity.this, getString(R.string.send_success));
                tv_yzm.setClickable(false);
                countDownTimer.start();
                findViewById(R.id.bt_regis).setEnabled(true);
            }

//            @Override
//            public void onResponse(Call call, final Response response) {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
//                            String s = response.body().string();
//                            MyLogger.i(s);
//                            JSONObject jsonObject = new JSONObject(s);
//                            if (jsonObject.getInt("ret") == 0) {
//                                Tool.showToast(LoginActivity.this, getString(R.string.send_success));
//                                tv_yzm.setClickable(false);
//                                countDownTimer.start();
//                                findViewById(R.id.bt_regis).setEnabled(true);
//                            } else {
//                                Tool.showToast(MyApplication.getCurrentActivity(), jsonObject.getString("message"));
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                            Tool.showToast(MyApplication.getCurrentActivity(), MyApplication.getCurrentActivity().getString(R.string.net_fail));
//                        } finally {
//                            Tool.removeProgressDialog();
//                        }
//
//                    }
//                });
//            }
        }, getString(R.string.sending), this);
    }

    private void register() {
        String tel = et_regis_tel.getText().toString().trim();
        if (TextUtils.isEmpty(tel)) {
            Tool.showToast(this, getString(R.string.pls_input_tel));
            return;
        }
        String yzm = et_regis_yzm.getText().toString().trim();
        if (TextUtils.isEmpty(yzm)) {
            Tool.showToast(this, getString(R.string.pls_input_yzm));
            return;
        }
        String psw = et_regis_psw.getText().toString().trim();
        if (TextUtils.isEmpty(psw)) {
            Tool.showToast(this, getString(R.string.pls_input_psw));
            return;
        }
        String re_psw = et_regis_re_psw.getText().toString().trim();
        if (TextUtils.isEmpty(re_psw)) {
            Tool.showToast(this, getString(R.string.pls_ensure_psw));
            return;
        }
        if (!psw.equals(re_psw)) {
            Tool.showToast(this, getString(R.string.psw_ensure_error));
            return;
        }
        NetParamas netParamas = new NetParamas();
        netParamas.put("phone", tel);
        netParamas.put("code", yzm);
        netParamas.put("password", Tool.getMD5(psw));
        NetUtil.post(Constance.HTTP_REGISTER, netParamas, new NetCallBack() {
            @Override
            public void onResponse(JSONObject result) {
                Tool.showToast(Login1Activity.this, getString(R.string.register_success));
                countDownTimer.cancel();
                et_regis_tel.setText("");
                et_regis_psw.setText("");
                et_regis_yzm.setText("");
                et_regis_re_psw.setText("");
                iv_login.setVisibility(View.VISIBLE);
                layout_login.setVisibility(View.VISIBLE);
                iv_regis.setVisibility(View.INVISIBLE);
                layout_regis.setVisibility(View.INVISIBLE);
            }
        }, getString(R.string.registering), this);
    }

    private void login(final String tel, final String psw) {
        NetParamas netParamas = new NetParamas();
        netParamas.put("phone", tel);
        netParamas.put("password", psw);
        NetUtil.post(Constance.HTTP_LOGIN, netParamas, new NetCallBack() {
            @Override
            public void onResponse(JSONObject result) {
                try {
                    SleepMonitorApplication.access_token = result.getJSONObject("data").getString("access_token");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            spUtil.put(Constance.KEY_TEL, tel);
                            spUtil.put(Constance.KEY_PSW, psw);
                        }
                    }).start();

                    startActivity(new Intent(Login1Activity.this, MainActivity.class));
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, getString(R.string.logining), this);
    }

    @Override
    public void onClose() {

    }

    @Override
    public void onFinish() {
        initrnet = true;
        LogUtils.i("initrnet === "  + initrnet);
    }

    @Override
    public void onDeny() {

    }

    @Override
    public void onGuarantee() {

    }
}

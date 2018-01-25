package com.lzhz.lxh.sleepmonitor.login;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.BaseActivity;
import com.lzhz.lxh.sleepmonitor.tools.Constance;
import com.lzhz.lxh.sleepmonitor.tools.Net.NetCallBack;
import com.lzhz.lxh.sleepmonitor.tools.Net.NetParamas;
import com.lzhz.lxh.sleepmonitor.tools.Net.NetUtil;
import com.lzhz.lxh.sleepmonitor.tools.Tool;

import org.json.JSONObject;

/**
 * Created by dk on 2017/7/4.
 */

public class ResetPswActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_regis_tel, et_regis_psw, et_regis_re_psw, et_regis_yzm;
    private TextView tv_yzm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_reset_psw);
        setTitle(getString(R.string.reset_psw));
        findViewById(R.id.bt_regis).setOnClickListener(this);
        findViewById(R.id.tv_get_yzm).setOnClickListener(this);
        tv_yzm = (TextView) findViewById(R.id.tv_get_yzm);
        et_regis_psw = (EditText) findViewById(R.id.et_regis_psw);
        et_regis_tel = (EditText) findViewById(R.id.et_regis_tel);
        et_regis_re_psw = (EditText) findViewById(R.id.et_regis_re_psw);
        et_regis_yzm = (EditText) findViewById(R.id.et_regis_yzm);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_regis:
                resetPsw();
                break;
            case R.id.tv_get_yzm:
                yzm();
                break;
        }
    }

    @Override
    public void getCenTitle(ImageView ivLeft, TextView tvTitle, TextView tvRight) {
        tvTitle.setText("重置密码");
    }

    private void resetPsw() {
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
        NetUtil.post(Constance.HTTP_RESET_PSW, netParamas, new NetCallBack() {
            @Override
            public void onResponse(JSONObject result) {
                Tool.showToast(ResetPswActivity.this, getString(R.string.reset_success));
                countDownTimer.cancel();
                finish();
            }
        }, getString(R.string.reset_psw), this);
    }

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
        netParamas.put("type", "2");
        NetUtil.get(Constance.HTTP_GET_YZM, netParamas, new NetCallBack() {

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
//                                Tool.showToast(ResetPswActivity.this, getString(R.string.send_success));
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

            @Override
            public void onResponse(JSONObject result) {
                Tool.showToast(ResetPswActivity.this, getString(R.string.send_success));
                tv_yzm.setClickable(false);
                countDownTimer.start();
                findViewById(R.id.bt_regis).setEnabled(true);
            }
        }, getString(R.string.sending), this);


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
}

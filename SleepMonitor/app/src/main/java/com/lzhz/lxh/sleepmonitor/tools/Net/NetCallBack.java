package com.lzhz.lxh.sleepmonitor.tools.Net;

import android.os.Handler;
import android.os.Looper;
import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.SleepMonitorApplication;
import com.lzhz.lxh.sleepmonitor.tools.FrameActivityStack;
import com.lzhz.lxh.sleepmonitor.tools.LogUtils;
import com.lzhz.lxh.sleepmonitor.tools.Tool;

import org.json.JSONObject;

import java.io.IOException;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by dk on 2016/6/1.
 */
public abstract class NetCallBack implements Callback {
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onFailure(Call call, IOException e) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Tool.removeProgressDialog();
                Tool.showToast(FrameActivityStack.getCurrentActivity(), FrameActivityStack.getCurrentActivity().getString(R.string.net_fail));
            }
        });
    }

    @Override
    public void onResponse(okhttp3.Call call, final Response response) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                try {
                    String s = response.body().string();
                    LogUtils.i(s);
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getInt("ret") == 0) {
                        onResponse(jsonObject);
                    } else {
                        Tool.showToast(FrameActivityStack.getCurrentActivity(), jsonObject.getString("message"));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Tool.showToast(FrameActivityStack.getCurrentActivity(), FrameActivityStack.getCurrentActivity().getString(R.string.net_fail));
                } finally {
                    Tool.removeProgressDialog();
                }

            }
        });

    }

    public abstract void onResponse(JSONObject result);

}

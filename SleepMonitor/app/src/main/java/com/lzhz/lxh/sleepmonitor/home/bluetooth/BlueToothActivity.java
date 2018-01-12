package com.lzhz.lxh.sleepmonitor.home.bluetooth;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inuker.bluetooth.library.search.SearchResult;
import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.BaseActivity;
import com.lzhz.lxh.sleepmonitor.home.bluetooth.adapter.DeviceListAdapter;
import com.lzhz.lxh.sleepmonitor.home.bluetooth.tools.BluetoothTools;
import com.lzhz.lxh.sleepmonitor.home.bluetooth.tools.BluetoothTools.scanTool;
import com.lzhz.lxh.sleepmonitor.tools.LogUtils;
import com.lzhz.lxh.sleepmonitor.tools.PersissionUtils;
import com.lzhz.lxh.sleepmonitor.tools.ToastUtil;
import com.lzhz.lxh.sleepmonitor.tools.interfacetool.PermissionInter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.weyye.hipermission.PermissionItem;

/**
 * 蓝牙
 */

public class BlueToothActivity extends BaseActivity implements PermissionInter {

    @BindView(R.id.rl_bluetooth_list)
    RecyclerView rlBluetoothList;
    @BindView(R.id.ib_refresh)
    ImageView ivRefersh;
    @BindView(R.id.rl_refresh)
    RelativeLayout rlRefersh;
    @BindView(R.id.pbar)
    ProgressBar pbar;
    private List<SearchResult> mDevices;
    DeviceListAdapter mAdapter;

    @Override
    public void setRootView() {
        setContent(R.layout.activity_blue_tooth);

    }

    @Override
    public void initData() {

        mDevices = new ArrayList<SearchResult>();
        mAdapter = new DeviceListAdapter(this);
        rlBluetoothList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // 如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        rlBluetoothList.setHasFixedSize(true);
        rlBluetoothList.setAdapter(mAdapter);
        PersissionUtils.setOnPermissionInter(this);
        PermissionItem permissionItem = new PermissionItem(Manifest.permission.ACCESS_FINE_LOCATION, "Location", R.drawable.permission_ic_location);
        PersissionUtils.setPermission(this, permissionItem);
        rlRefersh.setOnClickListener(this);
        ivRefersh.setOnClickListener(this);
    }

    @Override
    public void initViews() {
        super.initViews();
    }

    @Override
    public void getCenTitle(ImageView ivLeft, TextView tvTitle, TextView tvRight) {
        tvTitle.setText("周围的蓝牙");
        tvRight.setText("确定");
    }


    @Override
    public void onClose() {
        LogUtils.i("onClose");
    }

    @Override
    public void onFinish() {
        pbar.setVisibility(View.VISIBLE);
        //扫描
        BluetoothTools.getInstance().scan();
        scan();
    }

    private void scan() {
        BluetoothTools.getInstance().setOnscan(new scanTool() {
            @Override
            public void onDeviceFounded(SearchResult device) {
                if (!mDevices.contains(device)) {
                    mDevices.add(device);
                    mAdapter.setDataList(mDevices);
                }
            }

            @Override
            public void onSearchStopped() {
                pbar.setVisibility(View.GONE);
            }
        });

    }


    @Override
    public void onDeny() {
        ToastUtil.showShort(this, getString(R.string.deny));
        LogUtils.i("onDeny");
    }

    @Override
    public void onGuarantee() {
        LogUtils.i("onGuarantee");
    }

    private void setAnim(View view) {
        Animation animation = new RotateAnimation(0, 360);
        animation.setDuration(2000);
        animation.setRepeatCount(8);//动画的重复次数
        animation.setFillAfter(true);//设置为true，动画转化结束后被应用
        view.startAnimation(animation);//开始动画
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_refresh:
                LogUtils.i("-----------onClick");
                mDevices.clear();
                setAnim(ivRefersh);
                scan();
                break;
            case R.id.ib_refresh:
                LogUtils.i("-----------ib_refresh");
                mDevices.clear();
                setAnim(ivRefersh);
                scan();
                break;
        }
        super.onClick(view);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BluetoothTools.getInstance().getClient().stopSearch();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

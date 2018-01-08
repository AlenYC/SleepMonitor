package com.lzhz.lxh.sleepmonitor.home.bluetooth;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.inuker.bluetooth.library.BluetoothClient;
import com.inuker.bluetooth.library.beacon.Beacon;
import com.inuker.bluetooth.library.search.SearchRequest;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.search.response.SearchResponse;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.BaseActivity;

/**
 * 蓝牙
 */import butterknife.BindView;
import butterknife.ButterKnife;

public class BlueToothActivity extends BaseActivity {


    @BindView(R.id.rl_bluetooth_list)
    RelativeLayout rlBluetoothList;

    @Override
    public void setRootView() {
        setContent(R.layout.activity_blue_tooth);
    }

    @Override
    public void initData() {
        BluetoothClient mClient = new BluetoothClient(this);
        SearchRequest request = new SearchRequest.Builder()
                .searchBluetoothLeDevice(3000, 3)   // 先扫BLE设备3次，每次3s
                .searchBluetoothClassicDevice(5000) // 再扫经典蓝牙5s
                .searchBluetoothLeDevice(2000)      // 再扫BLE设备2s
                .build();

        mClient.search(request, new SearchResponse() {
            @Override
            public void onSearchStarted() {}

            @Override
            public void onDeviceFounded(SearchResult device) {
                Beacon beacon = new Beacon(device.scanRecord);
                BluetoothLog.v(String.format("beacon", device.getAddress(), beacon.toString()));
            }
            @Override
            public void onSearchStopped() {
            }
            @Override
            public void onSearchCanceled() {
            }
        });
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


}

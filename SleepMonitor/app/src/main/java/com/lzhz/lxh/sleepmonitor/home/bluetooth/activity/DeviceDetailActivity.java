package com.lzhz.lxh.sleepmonitor.home.bluetooth.activity;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.inuker.bluetooth.library.connect.listener.BleConnectStatusListener;
import com.inuker.bluetooth.library.connect.options.BleConnectOptions;
import com.inuker.bluetooth.library.connect.response.BleConnectResponse;
import com.inuker.bluetooth.library.model.BleGattProfile;
import com.inuker.bluetooth.library.search.SearchResult;
import com.inuker.bluetooth.library.utils.BluetoothLog;
import com.inuker.bluetooth.library.utils.BluetoothUtils;
import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.BaseActivity;
import com.lzhz.lxh.sleepmonitor.home.bluetooth.adapter.DeviceDetailAdapter;
import com.lzhz.lxh.sleepmonitor.home.bluetooth.bean.DetailItem;
import com.lzhz.lxh.sleepmonitor.tools.ClientManager;

import java.util.UUID;

import static com.inuker.bluetooth.library.Constants.REQUEST_SUCCESS;
import static com.inuker.bluetooth.library.Constants.STATUS_CONNECTED;

/**
 * Created by dingjikerbo on 2016/9/2.
 */
public class DeviceDetailActivity extends BaseActivity {

    private ProgressBar mPbar;

    private ListView mListView;
    private DeviceDetailAdapter mAdapter;

    private SearchResult mResult;

    private BluetoothDevice mDevice;

    private boolean mConnected;

    @Override
    public void setRootView() {
        setContent(R.layout.device_detail_activity);
    }

    @Override
    public void initData() {

        mPbar = (ProgressBar) findViewById(R.id.pbar);
        mListView = (ListView) findViewById(R.id.listview);
        mAdapter = new DeviceDetailAdapter(this, mDevice);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!mConnected) {
                    return;
                }
                DetailItem item = (DetailItem) mAdapter.getItem(position);
                if (item.type == DetailItem.TYPE_CHARACTER) {
                    BluetoothLog.v(String.format("click service = %s, character = %s", item.service, item.uuid));
                    startCharacterActivity(item.service, item.uuid);
                }
            }
        });

        ClientManager.getClient().registerConnectStatusListener(mDevice.getAddress(), mConnectStatusListener);

        connectDeviceIfNeeded();
    }

    @Override
    public void getCenTitle(ImageView ivLeft, TextView tvTitle, TextView tvRight) {
        Intent intent = getIntent();
        String mac = intent.getStringExtra("mac");
        mResult = intent.getParcelableExtra("device");
        mDevice = BluetoothUtils.getRemoteDevice(mac);
        tvTitle.setText(mDevice.getName());
    }

    @Override
    public void initViews() {
    }

    private final BleConnectStatusListener mConnectStatusListener = new BleConnectStatusListener() {
        @Override
        public void onConnectStatusChanged(String mac, int status) {
            BluetoothLog.v(String.format("DeviceDetailActivity onConnectStatusChanged %d in %s",
                    status, Thread.currentThread().getName()));

            mConnected = (status == STATUS_CONNECTED);
            connectDeviceIfNeeded();
        }
    };

    private void startCharacterActivity(UUID service, UUID character) {
       /* Intent intent = new Intent(this, CharacterActivity.class);
        intent.putExtra("mac", mDevice.getAddress());
        intent.putExtra("service", service);
        intent.putExtra("character", character);
        startActivity(intent);*/
    }

    private void connectDevice() {
        mPbar.setVisibility(View.VISIBLE);
        mListView.setVisibility(View.GONE);

        BleConnectOptions options = new BleConnectOptions.Builder()
                .setConnectRetry(3)
                .setConnectTimeout(20000)
                .setServiceDiscoverRetry(3)
                .setServiceDiscoverTimeout(10000)
                .build();

        ClientManager.getClient().connect(mDevice.getAddress(), options, new BleConnectResponse() {
            @Override
            public void onResponse(int code, BleGattProfile profile) {
                BluetoothLog.v(String.format("profile:\n%s", profile));
                mPbar.setVisibility(View.GONE);
                mListView.setVisibility(View.VISIBLE);

                if (code == REQUEST_SUCCESS) {
                    mAdapter.setGattProfile(profile);
                }
            }
        });
    }

    private void connectDeviceIfNeeded() {
        if (!mConnected) {
            connectDevice();
        }
    }

    @Override
    protected void onDestroy() {
        ClientManager.getClient().disconnect(mDevice.getAddress());
        ClientManager.getClient().unregisterConnectStatusListener(mDevice.getAddress(), mConnectStatusListener);
        super.onDestroy();
    }
}

package com.lzhz.lxh.moduledemo.map;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.lzhz.lxh.moduledemo.R;
import com.lzhz.lxh.moduledemo.base.BaseActivity;
import com.lzhz.lxh.moduledemo.tools.LogUtils;
import com.lzhz.lxh.moduledemo.tools.PersissionUtils;
import com.lzhz.lxh.moduledemo.tools.interfacetool.PermissionInter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.weyye.hipermission.PermissionItem;

/**
 * 作者：lxh on 2018-01-29:11:47
 * 邮箱：15911638454@163.com
 */

public class GdLocationActivity extends BaseActivity implements AMapLocationListener, PermissionInter, PoiSearch.OnPoiSearchListener {
    public AMapLocationClient aMapLocationClient;
    public AMapLocationClientOption aMapLocationClientOption;
    @BindView(R.id.m_map)
    MapView mMapView;
    //初始化地图控制器对象
    AMap aMap;

    PoiSearch.Query mQuery;
    PoiSearch poiSearch;
    @BindView(R.id.bt_poi)
    Button btPoi;
    private UiSettings mUiSettings;//定义一个UiSettings对象

    @Override
    public void setRootView() {
        setContentView(R.layout.gd_location_activity);
    }

    @Override
    public void initData() {
        PersissionUtils.setOnPermissionInter(this);
        List<PermissionItem> list = new ArrayList<>();
        PermissionItem permissionItem = new PermissionItem(Manifest.permission.ACCESS_COARSE_LOCATION, "Location", R.drawable.permission_ic_location);
        PermissionItem permissionItem1 = new PermissionItem(Manifest.permission.READ_PHONE_STATE, "读取手机当前的状态", R.drawable.permission_ic_location);
        PermissionItem permissionItem2 = new PermissionItem(Manifest.permission.WRITE_EXTERNAL_STORAGE, "SD卡写入", R.drawable.permission_ic_location);
        list.add(permissionItem);
        list.add(permissionItem1);
        list.add(permissionItem2);
        PersissionUtils.setPermissionList(this, list);
    }

    @Override
    public void initViews() {
        isShowTitle(false);
        btPoi.setOnClickListener(this);

    }


    /**
     * 地图定位
     */
    public void aMapLocation() {
        aMapLocationClient = new AMapLocationClient(this);
        aMapLocationClientOption = new AMapLocationClientOption();
        aMapLocationClient.setLocationListener(this);
        aMapLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        aMapLocationClientOption.setInterval(2000);
        aMapLocationClient.setLocationOption(aMapLocationClientOption);
        aMapLocationClient.startLocation();


    }

    /**
     * 停止定位
     */
    public void deactivate() {
        if (aMapLocationClient != null) {
            aMapLocationClient.stopLocation();
            aMapLocationClient.onDestroy();
        }
        aMapLocationClient = null;
    }


    /**
     * 设置地图蓝点
     */
    public void setLocationBulePoint() {
        if (aMap == null) {
            aMap = mMapView.getMap();
        }
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        aMap.showIndoorMap(true); //显示室内地图  默认false关闭
        mUiSettings = aMap.getUiSettings();
        mUiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮
        mUiSettings.setCompassEnabled(true);  //指南针
        setPOI();
    }

    public void setPOI() {
        mQuery = new PoiSearch.Query("餐厅", "", "");
        //第二个参数表示POI搜索类型，二者选填其一，选用POI搜索类型时建议填写类型代码，码表可以参考下方（而非文字）
        //cityCode表示POI搜索区域，可以是城市编码也可以是城市名称，也可以传空字符串，空字符串代表全国在全国范围内进行搜索
        mQuery.setPageSize(10);// 设置每页最多返回多少条poiitem
        mQuery.setPageNum(1);//设置查询页码

        poiSearch = new PoiSearch(this, mQuery);
        poiSearch.setOnPoiSearchListener(this);
        poiSearch.searchPOIAsyn();
    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                //  Log.i("map", "Latitude ---- " + amapLocation.getLatitude() + "Longitude ---- " + amapLocation.getLongitude());
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }

    @Override
    public void onClose() {
    }

    @Override
    public void onFinish() {
        aMapLocation();
        setLocationBulePoint();
    }

    @Override
    public void onDeny() {

    }

    @Override
    public void onGuarantee() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        mMapView.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
            case R.id.bt_poi:
                setPOI();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        LogUtils.i(poiResult.getSearchSuggestionCitys().get(0).getCityName());
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }
}

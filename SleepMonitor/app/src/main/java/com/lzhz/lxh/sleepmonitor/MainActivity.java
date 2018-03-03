package com.lzhz.lxh.sleepmonitor;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.lzhz.lxh.sleepmonitor.analyzed.AnalyzeDetailsFragment;
import com.lzhz.lxh.sleepmonitor.analyzed.AnalyzedFragment;
import com.lzhz.lxh.sleepmonitor.base.BaseAcitivity.CompanyIntroduceActivity;
import com.lzhz.lxh.sleepmonitor.decompression.DecompressionFragment;
import com.lzhz.lxh.sleepmonitor.home.CardiographFragment;
import com.lzhz.lxh.sleepmonitor.home.HomeFragment;
import com.lzhz.lxh.sleepmonitor.home.activity.UserInfoActivity;
import com.lzhz.lxh.sleepmonitor.home.activity.bean.User;
import com.lzhz.lxh.sleepmonitor.home.adapter.FragmentAdapter;
import com.lzhz.lxh.sleepmonitor.login.Login1Activity;
import com.lzhz.lxh.sleepmonitor.login.LoginActivity;
import com.lzhz.lxh.sleepmonitor.relatives.RelativesFragment;
import com.lzhz.lxh.sleepmonitor.sideslip.PersonalDetailsActivity;
import com.lzhz.lxh.sleepmonitor.sideslip.VersionsActivity;
import com.lzhz.lxh.sleepmonitor.tools.Constance;
import com.lzhz.lxh.sleepmonitor.tools.GlideCircleTransform;
import com.lzhz.lxh.sleepmonitor.tools.LogUtils;
import com.lzhz.lxh.sleepmonitor.tools.Net.NetCallBack;
import com.lzhz.lxh.sleepmonitor.tools.Net.NetParamas;
import com.lzhz.lxh.sleepmonitor.tools.Net.NetUtil;
import com.lzhz.lxh.sleepmonitor.tools.SPUtil;
import com.lzhz.lxh.sleepmonitor.tools.jni.JNIUtils;
import com.lzhz.lxh.sleepmonitor.tools.view.NoScrollViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONObject;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener ,RadioGroup.OnCheckedChangeListener{

    //  @BindView(R.id.bottom_navigation_bar)
    //  BottomNavigationView bottomNavigationView;
    @BindView(R.id.viewpager)
    NoScrollViewPager viewPager;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.dl_home)
    DrawerLayout dlHome;
    @BindView(R.id.rg_radio_navigation)
    RadioGroup rgRadioNavigation;
    ImageView ivHead_portrait;
    TextView tvUserName;
    RequestOptions options;
    View headerLayout;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initViews();
        viewPager.setCurrentItem(0);
        viewPager.setNoScroll(true);

        dyJNI();
    }

    public void dyJNI(){
        double raw_data[] = new double[30000];
        for (int i = 0;i < 30000;i++){
            if(i%2==0)
                raw_data[i] = 0;
            else
                raw_data[i] = 255;
        }
        double hbrRes[]= new double[2];
        double HD[]= new double[300];
        double RD[]= new double[300];
        double snore[]= new double[3000];

        JNIUtils.detect0131(raw_data,hbrRes,HD,RD,snore);

        System.out.println("单片机原始数据 : " + Arrays.toString(raw_data));

        System.out.println("心率值 : " + hbrRes[0]);
        System.out.println("呼吸速率 : " + hbrRes[1]);
        System.out.println("心跳数据 : " + Arrays.toString(HD));
        System.out.println("呼吸数据 : " + Arrays.toString(RD));
        System.out.println("鼾声数据 : " + Arrays.toString(snore));
    }

    public void openDrawer() {
        dlHome.openDrawer(navView);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void userEventBus(User user){
            Glide.with(MainActivity.this).load(user.getPortrait()).apply(options).into(ivHead_portrait);
            tvUserName.setText(user.getName());
    }

    public void initViews() {
       // headerLayout  = navView.inflateHeaderView(R.layout.nav_header);
        headerLayout = navView.getHeaderView(0);
        tvUserName = headerLayout.findViewById(R.id.tv_user_name);
        ivHead_portrait = headerLayout.findViewById(R.id.iv_head_portrait);

        options = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.user)
                .error(R.mipmap.user)
                .priority(Priority.HIGH).transform(new GlideCircleTransform(this));

        String headImage = SPUtil.getInstance(getApplicationContext()).getString("userPortrait");
        String userName = SPUtil.getInstance(getApplicationContext()).getString("userName");
        if(headImage!=null||"".equals(headImage))
        Glide.with(MainActivity.this).load(headImage).apply(options).into(ivHead_portrait);
        if(userName!=null||"".equals(userName))
            tvUserName.setText(userName);
        if (viewPager != null) {
            rgRadioNavigation.setOnCheckedChangeListener(this);
            rgRadioNavigation.check(R.id.rb_radio_homepage);

        /*    bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    int itemId = item.getItemId();
//                当第一项被选择时BlankFragmentOne显示，以此类推
                    switch (itemId) {
                        case R.id.btm_nav_item1:
                            viewPager.setCurrentItem(0);
                            break;
                        case R.id.btm_nav_item2:
                            viewPager.setCurrentItem(1);
                            break;
                        case R.id.btm_nav_item3:
                            viewPager.setCurrentItem(2);
                            break;
                        case R.id.btm_nav_item4:
                            viewPager.setCurrentItem(3);
                            break;
                    }
                    return true;
                }
            });*/
            setupViewPager(viewPager);
        }
        navView.setNavigationItemSelectedListener(this);
        getUserInfo();
    }

    public void setAnalyze() {
        viewPager.setCurrentItem(1);
    }

    public void setAnalyzeDetails() {
        viewPager.setCurrentItem(4);
    }


    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new HomeFragment());
        //adapter.addFragment(new CardiographFragment());
        adapter.addFragment(new AnalyzedFragment());
        adapter.addFragment(new DecompressionFragment());
        adapter.addFragment(new RelativesFragment());
        adapter.addFragment(new AnalyzeDetailsFragment());
        viewPager.setAdapter(adapter);

    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            //个人信息
            case R.id.it_plm:
                startActivity(new Intent(this, UserInfoActivity.class));
                break;
            case R.id.it_version_information:
                startActivity(new Intent(this, VersionsActivity.class));
                break;
            case R.id.it_user_feedback:
                break;
            case R.id.it_usinghelp:
                break;
            case R.id.it_about_us:
                startActivity(new Intent(this, CompanyIntroduceActivity.class));
                break;
            case R.id.it_log_out:
                onBackPressed();
                break;
        }
        return true;
    }


    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch(i){
            case R.id.rb_radio_homepage:
                viewPager.setCurrentItem(0);
                break;
            case R.id.rb_radio_subscription:
                viewPager.setCurrentItem(1);
                break;
            case R.id.rb_radio_find:
                viewPager.setCurrentItem(2);
                break;
            case R.id.rb_radio_mine:
                viewPager.setCurrentItem(3);
                break;
        }
    }
    //退出提示框
    public void onBackPressed() {
        showDialog(getString(R.string.notice), getString(R.string.logout_notice), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                SleepMonitorApplication.access_token = "";
                SPUtil.getInstance(MainActivity.this).clear();
                Intent intent = new Intent(MainActivity.this, Login1Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
    void showDialog(String title, String msg, DialogInterface.OnClickListener listner) {
        new android.support.v7.app.AlertDialog.Builder(this).setTitle(title).setMessage(msg).setPositiveButton(android.R.string.ok, listner).setNegativeButton(
                android.R.string.cancel, null).create().show();
    }

    private void getUserInfo() {
        NetParamas netParamas = new NetParamas();
        netParamas.put("access_token", SleepMonitorApplication.access_token);
        NetUtil.get(Constance.HTTP_GET_INFO, netParamas, new NetCallBack() {
            @Override
            public void onResponse(JSONObject j) {
                try {
                    JSONObject result = j.getJSONObject("data");
                   SleepMonitorApplication.user = JSON.parseObject(result.toString(), User.class);
                    if (SleepMonitorApplication.user != null) {
                        Glide.with(MainActivity.this).load(SleepMonitorApplication.user.getPortrait()).apply(options).into(ivHead_portrait);
                        tvUserName.setText(SleepMonitorApplication.user.getName());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, getString(R.string.loading), this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //注销注册
      //  EventBus.getDefault().unregister(this);
    }

}

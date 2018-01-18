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
import android.view.MenuItem;
import android.widget.RadioGroup;

import com.lzhz.lxh.sleepmonitor.analyzed.AnalyzeDetailsFragment;
import com.lzhz.lxh.sleepmonitor.analyzed.AnalyzedFragment;
import com.lzhz.lxh.sleepmonitor.base.BaseAcitivity.CompanyIntroduceActivity;
import com.lzhz.lxh.sleepmonitor.decompression.DecompressionFragment;
import com.lzhz.lxh.sleepmonitor.home.HomeFragment;
import com.lzhz.lxh.sleepmonitor.home.adapter.FragmentAdapter;
import com.lzhz.lxh.sleepmonitor.relatives.RelativesFragment;
import com.lzhz.lxh.sleepmonitor.sideslip.PersonalDetailsActivity;
import com.lzhz.lxh.sleepmonitor.sideslip.VersionsActivity;
import com.lzhz.lxh.sleepmonitor.tools.LogUtils;
import com.lzhz.lxh.sleepmonitor.tools.view.NoScrollViewPager;

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


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
        viewPager.setCurrentItem(0);
        viewPager.setNoScroll(true);
    }

    public void openDrawer() {
        dlHome.openDrawer(navView);
    }

    public void initViews() {

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
                startActivity(new Intent(this, PersonalDetailsActivity.class));
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
        new AlertDialog.Builder(this).setTitle("确认退出吗？")
                .setIcon(android.R.drawable.ic_dialog_info)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“确认”后的操作
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("返回", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 点击“返回”后的操作,这里不设置没有任何操作
                    }
                }).show();
    }
}

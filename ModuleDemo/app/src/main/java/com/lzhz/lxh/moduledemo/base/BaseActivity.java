package com.lzhz.lxh.moduledemo.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzhz.lxh.moduledemo.R;
import com.lzhz.lxh.moduledemo.base.inter.Y_BaseInterface;
import com.lzhz.lxh.moduledemo.tools.FrameActivityStack;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 作者：lxh on 2018-01-27:14:11
 * 邮箱：15911638454@163.com
 */

public abstract class BaseActivity  extends AppCompatActivity implements View.OnClickListener,Y_BaseInterface {
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.iv_right_top)
    ImageView ivRightTop;
    @BindView(R.id.tl_relat)
    Toolbar tlRelat;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameActivityStack.create().addActivity(this);
        setRootView();
        ButterKnife.bind(this);
        setToolbarTitle(ivLeft,toolbarTitle,ivRightTop);
        ivLeft.setOnClickListener(this);
        if (initBaseParams(savedInstanceState)) {
            initViews();
            initData();
        }
    }

    /**
     * 设置标题
     * @param ivLeft 左
     * @param toolbarTitle 中
     * @param ivRightTop 右
     */
    public  void setToolbarTitle(ImageView ivLeft,TextView toolbarTitle,ImageView ivRightTop){};

    /**
     * 是否显示标题
     * @param showTool false 不显示
     */
    public void isShowTitle(boolean showTool){
        if(!showTool)
            tlRelat.setVisibility(View.GONE);
    }

    /**
     * activity重置  数据重置
     */
    protected boolean initBaseParams(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return false;
        }
        return true;
    }

    /**
     * activity跳转
     * @param activity 调用类
     * @param cls 跳转到
     */
    public void keepTogo(Activity activity, Class<?> cls) {
        Intent intent = new Intent();
        intent.setClass(activity, cls);
        activity.startActivity(intent);

    }
    /**
     * activity跳转
     * @param activity 调用类
     * @param intent 跳转intent
     */
    public void keepTogo(Activity activity, Intent intent) {
    activity.startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_left:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        FrameActivityStack.create().removeActivityStack(this);
        super.onDestroy();
    }
}

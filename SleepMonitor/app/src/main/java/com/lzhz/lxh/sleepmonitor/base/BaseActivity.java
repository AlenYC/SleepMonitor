package com.lzhz.lxh.sleepmonitor.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.base.inter.Y_BaseInterface;
import com.lzhz.lxh.sleepmonitor.tools.FrameActivityStack;

import butterknife.BindView;
import butterknife.ButterKnife;


public abstract class BaseActivity extends AppCompatActivity implements Y_BaseInterface,View.OnClickListener {
    public static boolean showTitle = true;
    FrameLayout flContent;
    @BindView(R.id.iv_left)
    ImageView ivLeft;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.tv_right_text)
    TextView tvRightText;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRootView();
        FrameActivityStack.create().addActivity(this);


        if (initBaseParams(savedInstanceState)) {
            initViews();
            initData();
        }
    }

    public void setContent(int layoutId) {
        setContentView(R.layout.activity_base);
        flContent = findViewById(R.id.fl_content);
        flContent.addView(View.inflate(this, layoutId,null));
        ButterKnife.bind(this);

        getToolbar(toolbar);

        getCenTitle(ivLeft,toolbarTitle,tvRightText);
        ivLeft.setOnClickListener(this);

        flContent.addView(View.inflate(this, layoutId, null));
    }

    //activity重置  数据重置
    protected boolean initBaseParams(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            return false;
        }
        return true;
    }



    @Override
    public void setRootView() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void initViews() {

    }

    @Override
    protected void onDestroy() {
        FrameActivityStack.create().removeActivityStack(this);
        super.onDestroy();
    }

    //获取Toolbar
    public void getToolbar(Toolbar toolbar) {
    };

    //获取中间标题
    public void getCenTitle(ImageView ivLeft,TextView tvTitle,TextView tvRight) {
    };
    public void isShowToolBar(Boolean boo){
        if(boo){
            toolbar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_left:
                finish();
                break;
        }
    }
}

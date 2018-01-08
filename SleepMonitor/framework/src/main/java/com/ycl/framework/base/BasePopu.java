package com.ycl.framework.base;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.ycl.framework.R;


@SuppressLint("ViewConstructor")
public abstract class BasePopu extends PopupWindow implements OnClickListener {
    protected FrameActivity mActivity;

    protected OnPupClickListener listener;

    protected int heightNavigationBar = 0;

    public BasePopu(FrameActivity act, int idRes, int width, int height) {
        super(LayoutInflater.from(act).inflate(idRes, null), width, height, true);
        mActivity = act;
        setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        this.setOnDismissListener(new OnDismissListener() {
            public void onDismiss() {
                WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
                lp.alpha = 1f;
                mActivity.getWindow().setAttributes(lp);
                afterDismiss();
            }
        });
        init();
    }

    //底部暂时时 .需要处理 虚拟导航键的高度
    public void setHeightNavigationBar(int heightNavigationBar) {
        this.heightNavigationBar = heightNavigationBar;
    }

    public void showBottom() {
        setAnimationStyle(R.style.Popup_Animation_UpDown);
        beforeshow();
        showAtLocation(mActivity.getWindow().getDecorView(), Gravity.BOTTOM | Gravity.CENTER, 0, heightNavigationBar);
        WindowManager.LayoutParams lp = mActivity.getWindow().getAttributes();
        lp.alpha = 0.7f; //屏幕变暗 0.0-1.0
        mActivity.getWindow().setAttributes(lp);
        update();
    }

    protected void beforeshow() {
    }

    protected void afterDismiss() {
    }

    protected abstract void init();

    //点击回调
    public interface OnPupClickListener {
        void onPupClick(int position);
    }

    public OnPupClickListener getListener() {
        return listener;
    }

    public void setOnPupClicListener(OnPupClickListener listener) {
        this.listener = listener;
    }

    @SuppressWarnings("unchecked")
    protected <T extends View> T findViewsId(int id, boolean clickAble) {
        T views = (T) getContentView().findViewById(id);
        if (clickAble)
            views.setOnClickListener(this);
        return views;
    }
}
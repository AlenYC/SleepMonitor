package com.ycl.framework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ycl.framework.R;
import com.ycl.framework.base.FrameActivity;
import com.ycl.framework.utils.string.DensityUtils;
import com.ycl.framework.utils.util.SelectorUtil;

/**
 * 顶部栏 (通用) on 2015/10/16.
 */
public class TitleHeaderView extends FrameLayout {

    private TextView tvTitle;
    private ImageView ivBack;

    public TitleHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addView(LayoutInflater.from(context).inflate(R.layout.view_title_header, this, false));
        tvTitle = (TextView) findViewById(R.id.tv_title_header_titleText);
        ivBack = (ImageView) findViewById(R.id.iv_title_header_back);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.styleable_titleHeaderView);
//        if (typedArray.getString(R.styleable.styleable_titleHeaderView_title_name) != null)
        tvTitle.setText(typedArray.getString(R.styleable.styleable_itemViews_item_left_string));
        typedArray.recycle();

        initViews();
    }

    private void initViews() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            findViewById(com.ycl.framework.R.id.ll_title_root_parent).setPadding(0, 0, 0, 0);
        }
        ivBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((FrameActivity) getContext()).finish();
            }
        });
    }

    //设置 tvTitle的文本
    public void setTitleText(int txtId) {
        try {
            tvTitle.setText(getContext().getString(txtId));
        } catch (Exception e) {
            tvTitle.setText("");
        }
    }

    public void setTitleText(CharSequence content) {
        tvTitle.setText(content.toString());
    }

    //自定义click事件
    public void setCustomClickListener(int rId, OnClickListener listener) {
        findViewById(rId).setOnClickListener(listener);
    }

    private TextView rightAlbum_tv;

    //相册选中多图
    public void setRightBtn_Album(int length, OnClickListener onClickListener, int maxLength) {
        if (rightAlbum_tv == null) {
            rightAlbum_tv = new TextView(getContext());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParams.setMargins(0, 0, DensityUtils.dp2px(15, getContext()), 0);
            rightAlbum_tv.setLayoutParams(layoutParams);
            int paddingTop = DensityUtils.dp2px(2, getContext());
            int paddingLeft = DensityUtils.dp2px(12, getContext());
            rightAlbum_tv.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);
            rightAlbum_tv.setBackgroundResource(R.drawable.bg_btn_titleview_album);
            rightAlbum_tv.setTextColor(SelectorUtil.getColorListState(getContext(), R.color.me_data_icon, R.color.money_color));

            rightAlbum_tv.setOnClickListener(onClickListener);
            RelativeLayout title_root = (RelativeLayout) findViewById(R.id.ll_title_root);
            title_root.addView(rightAlbum_tv);
            rightAlbum_tv.setId(R.id.title_view_right_id);
        }

        if (length > 0) {
            rightAlbum_tv.setEnabled(true);
            rightAlbum_tv.setText("完成(" + length + "/" + maxLength + ")");
        } else {
            rightAlbum_tv.setEnabled(false);
            rightAlbum_tv.setText("完成");
        }
    }

    /**
     * right Text按钮
     *
     * @param str 文本 onClickListener 点击回调
     */
    public void setRightBtn_txt(String str, OnClickListener onClickListener) {
        if (rightAlbum_tv == null) {
            rightAlbum_tv = new TextView(getContext());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParams.setMargins(0, 0, DensityUtils.dp2px(8, getContext()), 0);
            rightAlbum_tv.setLayoutParams(layoutParams);
            int paddingTop = DensityUtils.dp2px(2, getContext());
            int paddingLeft = DensityUtils.dp2px(12, getContext());
            rightAlbum_tv.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);
            rightAlbum_tv.setTextColor(Color.WHITE);
            rightAlbum_tv.setTextSize(15);

            rightAlbum_tv.setOnClickListener(onClickListener);
            RelativeLayout title_root = (RelativeLayout) findViewById(R.id.ll_title_root);
            title_root.addView(rightAlbum_tv);
            rightAlbum_tv.setId(R.id.title_view_right_id);
        }
        rightAlbum_tv.setText(str);
    }


    /**
     * right Img按钮
     *
     * @param ids 文本 onClickListener 点击回调
     */
    public void addRightImg(int ids, OnClickListener onClickListener) {
        ImageView ivRight = (ImageView) findViewById(R.id.title_view_right_id);
        if (ivRight == null) {
            ivRight = new ImageView(getContext());
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);

            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
            layoutParams.setMargins(0, 0, DensityUtils.dp2px(8, getContext()), 0);
            ivRight.setLayoutParams(layoutParams);
            int paddingTop = DensityUtils.dp2px(2, getContext());
            int paddingLeft = DensityUtils.dp2px(12, getContext());
            ivRight.setPadding(paddingLeft, paddingTop, paddingLeft, paddingTop);
            ivRight.setImageResource(ids);

            ivRight.setOnClickListener(onClickListener);
            RelativeLayout title_root = (RelativeLayout) findViewById(R.id.ll_title_root);
            title_root.addView(ivRight);
            ivRight.setId(R.id.title_view_right_id);
        }
    }


    //设置左边imageView的visible
    public void setLeftViewVisible(int visible) {
        ivBack.setVisibility(visible);
    }

    //设置backGround 的透明度
    public void setBgAlpha(int alpha) {
        findViewById(R.id.ll_title_root_parent).getBackground().setAlpha(alpha);
    }

    public void setLeftViewRes(int ids) {
        ivBack.setImageResource(ids);
    }


}

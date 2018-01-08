package com.ycl.framework.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ycl.framework.R;
import com.ycl.framework.utils.string.DensityUtils;
import com.ycl.framework.utils.util.GlideProxy;
import com.ycl.framework.utils.util.SelectorUtil;

/**
 * Simple选项通用item（纯文本 ） on 2015/10/19.
 */
public class ItemsSimpleView extends FrameLayout {

    private TextView tvTitle;
    private ImageView ivLeft;
    private View line; //底部的线 (根据right view进行调整)

    public ItemsSimpleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addView(LayoutInflater.from(context).inflate(R.layout.layout_simple_itemview, this, false));
        setBackgroundDrawable(SelectorUtil.getDrawable(context, android.R.color.white, R.color.bg_default));

        tvTitle = (TextView) findViewById(R.id.tv_simple_item_view_title);
        ivLeft = (ImageView) findViewById(R.id.iv_item_simple_view_left);
        line = findViewById(R.id.line_simple_item_view_bottom);

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs,
                R.styleable.styleable_titleHeaderView);
        tvTitle.setText(typedArray.getString(R.styleable.styleable_titleHeaderView_title_name));

        Drawable d = typedArray.getDrawable(R.styleable.styleable_titleHeaderView_title_left_img_res);
        if (d != null) {
            ivLeft.setImageDrawable(d);
            ivLeft.setVisibility(VISIBLE);
        }
        typedArray.recycle();
    }

    public void setSimpleItemsTitle(String text) {
        tvTitle.setText(text);
    }


    private ImageView ivImg;

    /**
     * Method 设置Right 图片 (类似  头像)
     *
     * @param path 图片路径
     */
    public void setRightImg(String path) {
        if (ivImg == null) {
            ivImg = new ImageView(getContext());
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(DensityUtils.dp2px(75, getContext()), DensityUtils.dp2px(75, getContext()));
            params.setMargins(DensityUtils.dp2px(15, getContext()), DensityUtils.dp2px(15, getContext()), DensityUtils.dp2px(15, getContext()), DensityUtils.dp2px(15, getContext()));
            params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            ivImg.setLayoutParams(params);
            ivImg.setScaleType(ScaleType.FIT_XY);
            ((RelativeLayout) findViewById(R.id.rl_simple_item_view_root)).addView(ivImg);
            ivImg.setId(R.id.title_view_right_id);
            //底部线
            View line = findViewById(R.id.line_simple_item_view_bottom);
            RelativeLayout.LayoutParams paramLine = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, DensityUtils.dp2px(1, getContext()));
            paramLine.addRule(RelativeLayout.BELOW, R.id.title_view_right_id);
            paramLine.setMargins(DensityUtils.dp2px(15, getContext()), DensityUtils.dp2px(10, getContext()), 0, 0);
            line.setLayoutParams(paramLine);

        }
        GlideProxy.loadImgForUrl(ivImg, path);
    }


    //添加右边 数字提示
    public void addDigitView(String str) {
//        TextView tvDigit = (TextView) findViewWithTag("digitView");
//        if (tvDigit == null) {
//            tvDigit = new TextView(getContext());
//            tvDigit.setTextSize(11);
//            tvDigit.setTextColor(Color.WHITE);
//            int padding = DensityUtils.dp2px(3f, getContext());
//            tvDigit.setPadding(DensityUtils.dp2px(7f, getContext()), padding, DensityUtils.dp2px(7f, getContext()), padding);
//            tvDigit.setBackground(SelectorUtil.getShape(0xFFC0312D, DensityUtils.dp2px(25, getContext()), 0, 0xFFC0312D));
//
//            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
//            params.addRule(RelativeLayout.LEFT_OF, R.id.iv_simple_item_view_guide);
//            params.addRule(RelativeLayout.CENTER_VERTICAL);
//            params.setMargins(0, 0, DensityUtils.dp2px(10, getContext()), 0);
//            tvDigit.setTag("digitView");
//            tvDigit.setLayoutParams(params);
//            ((RelativeLayout) findViewById(R.id.rl_simple_item_view_content_right)).addView(tvDigit);
//        }
//        tvDigit.setText(str);

        ImageView tvDigit = (ImageView) findViewWithTag("digitView");
        if (tvDigit == null) {
            tvDigit = new ImageView(getContext());
            int padding = DensityUtils.dp2px(8f, getContext());
            tvDigit.setBackground(SelectorUtil.getShape(0xFFC0312D, DensityUtils.dp2px(4, getContext()), 0, 0xFFC0312D));

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(padding, padding);
            params.addRule(RelativeLayout.LEFT_OF, R.id.iv_simple_item_view_guide);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.setMargins(0, 0, DensityUtils.dp2px(10, getContext()), 0);
            tvDigit.setTag("digitView");
            tvDigit.setLayoutParams(params);
            ((RelativeLayout) findViewById(R.id.rl_simple_item_view_content_right)).addView(tvDigit);
        }

    }


    //dismiss View(数字提示)
    public void disDigitView() {
        View v = findViewWithTag("digitView");
        if (v != null) {
            v.setVisibility(GONE);
        }
    }

    public void addRightTv(CharSequence str) {
        TextView tvHint = (TextView) findViewWithTag("rightTv");
        if (tvHint == null) {
            tvHint = new TextView(getContext());
            tvHint.setTextSize(14);
            tvHint.setTextColor(getResources().getColor(R.color.title_view_header_bg_color));

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.LEFT_OF, R.id.iv_simple_item_view_guide);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.setMargins(0, 0, DensityUtils.dp2px(10, getContext()), 0);
            tvHint.setTag("rightTv");
            tvHint.setLayoutParams(params);
            ((RelativeLayout) findViewById(R.id.rl_simple_item_view_content_right)).addView(tvHint);
        }
        tvHint.setText(str);
    }


    public void addRightTvWithColor(CharSequence str, int colors) {
        TextView tvHint = (TextView) findViewWithTag("rightTv");
        if (tvHint == null) {
            tvHint = new TextView(getContext());
            tvHint.setTextSize(14);
            tvHint.setTextColor(getResources().getColor(colors));

            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.addRule(RelativeLayout.LEFT_OF, R.id.iv_simple_item_view_guide);
            params.addRule(RelativeLayout.CENTER_VERTICAL);
            params.setMargins(0, 0, DensityUtils.dp2px(10, getContext()), 0);
            tvHint.setTag("rightTv");
            tvHint.setLayoutParams(params);
            ((RelativeLayout) findViewById(R.id.rl_simple_item_view_content_right)).addView(tvHint);
        }
        tvHint.setText(str);
    }

}

package com.lzhz.lxh.sleepmonitor.tools.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.home.bean.HomeLinsBean;
import com.lzhz.lxh.sleepmonitor.tools.LogUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 作者：lxh on 2018-01-11:15:21
 * 邮箱：15911638454@163.com
 */

public class LinsView extends View {
    private Paint mPaint;
    private int lins1Color;
    private int lins2Color;
    private float mWidth;
    private float mHeight;
    private float linsSize;
    List<HomeLinsBean> list;
    public LinsView(Context context) {
        super(context);
        initPaint();
    }

    public LinsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context,attrs);
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }
    public void setList(ArrayList<HomeLinsBean> list){
        this.list = list;
        invalidate();
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        //设置线的宽度
        mPaint.setStrokeWidth(linsSize);
    }

    private void getAttrs(Context context, @Nullable AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.LinsView);
        lins1Color = ta.getColor(R.styleable.LinsView_lins1Color, Color.BLUE);
        lins2Color = ta.getColor(R.styleable.LinsView_lins2Color, Color.BLUE);
        linsSize = ta.getDimension(R.styleable.LinsView_linsSize, 20f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        float sum = 0;
        float count = 0;
        if(list == null || list.size() ==0)return;

        for (int i = 0;i<list.size();i++){
            sum += list.get(i).getLins();
        }
        count = mWidth / sum;
        float startX = 0;
        float startY = 20;
        float endX = list.get(0).getLins()*count;
        float endY = 20;
        for (int i = 0;i<list.size() - 1;i++){
            if(list.get(i).getLinsState() == 0){
                mPaint.setColor(lins1Color);
                canvas.drawLine(startX,startY,endX,endY,mPaint);
            }else if(list.get(i).getLinsState() == 1){
                mPaint.setColor(lins2Color);
                canvas.drawLine(startX,startY,endX,endY,mPaint);
            }else{}
            startX+=list.get(i).getLins()*count;
            endX=startX + list.get(i+1).getLins()*count;
        }

    }
}

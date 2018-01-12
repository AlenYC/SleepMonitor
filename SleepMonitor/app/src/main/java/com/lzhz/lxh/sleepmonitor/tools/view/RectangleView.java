package com.lzhz.lxh.sleepmonitor.tools.view;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.lzhz.lxh.sleepmonitor.R;
import com.lzhz.lxh.sleepmonitor.analyzed.bean.AnalyzedViewBean;
import com.lzhz.lxh.sleepmonitor.home.bean.HomeLinsBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：lxh on 2018-01-11:15:21
 * 邮箱：15911638454@163.com
 * 矩形view
 */

public class RectangleView extends View {
    private Paint mPaint;
    private int d1Color;
    private int d2Color;
    private int d3Color;

    private Paint textTimePaint;
    private float TextTimeSize;
    private int TextTimeColor;

    private float mWidth;
    private float mHeight;
    List<AnalyzedViewBean> list;
    public RectangleView(Context context) {
        super(context);
        initPaint();
    }

    public RectangleView(Context context, @Nullable AttributeSet attrs) {
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
    public void setList(ArrayList<AnalyzedViewBean> list){
        this.list = list;
        invalidate();
    }

    private void initPaint(){
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(d1Color);
        //设置线的宽度

        textTimePaint = new Paint();
        textTimePaint.setStyle(Paint.Style.FILL);
        textTimePaint.setStrokeWidth(20f);
        //设置线的宽度
        textTimePaint.setTextSize(TextTimeSize);
        textTimePaint.setColor(TextTimeColor);

    }

    private void getAttrs(Context context, @Nullable AttributeSet attrs){
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.DetectionView);
        d1Color = ta.getColor(R.styleable.DetectionView_d1Color, Color.BLUE);
        d2Color = ta.getColor(R.styleable.DetectionView_d2Color, Color.BLUE);
        d3Color = ta.getColor(R.styleable.DetectionView_d3Color, Color.BLUE);

        TextTimeColor = ta.getColor(R.styleable.DetectionView_TimeColor, Color.BLUE);
        TextTimeSize = ta.getDimension(R.styleable.DetectionView_TextTimeSize, 30f);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        if(list == null||list.size() == 0){
            return;
        }
        float timeX = 40;
        float timeY = mHeight/12*11;
        float startX;
        float startY ;
        float endX;
        float endY;
        for (int i = 0; i < list.size(); i++){
            if(i!=0){
                timeX = (mWidth - 80)/8*i + 40;
            }
            canvas.drawText(list.get(i).getaTime(),timeX,timeY,textTimePaint);
             List<Integer> widths = list.get(i).getWidth();

             startX = timeX;
             endX = startX + (mWidth -80)/14;
            endY = mHeight / 7 * 6;
            for (int j = 0; j <widths.size();j++ ){
                startY = endY - widths.get(j);
                if(j == 0){
                    mPaint.setColor(d1Color);
                    canvas.drawRect(startX,startY,endX,endY,mPaint);
                }else if(j == 1){
                    mPaint.setColor(d2Color);
                    canvas.drawRect(startX,startY,endX,endY,mPaint);
                }else if(j == 2){
                    mPaint.setColor(d3Color);
                    canvas.drawRect(startX,startY,endX,endY,mPaint);
                }
                endY = startY;
            }
        }

    }
}

package com.lzhz.lxh.sleepmonitor.tools.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：lxh on 2018-03-02:11:11
 * 邮箱：15911638454@163.com
 */

public class Cardiograph1View extends View {
    //画笔
    protected Paint mPaint;
    //折现的颜色
    protected int mLineColor = Color.parseColor("#76f112");
    //网格颜色
    protected int mGridColor = Color.parseColor("#1b4200");

    //小网格颜色
    protected int mSGridColor = Color.parseColor("#092100");
    //背景颜色
    protected int mBackgroundColor = Color.BLACK;
    //自身的大小
    protected int mWidth,mHeight;

    //网格宽度
    protected int mGridWidth = 50;
    //小网格的宽度
    protected int mSGridWidth = 10;

    //心电图折现
    protected Path mPath ;

    double hbrRes[]= new double[2];
    double HD[]= new double[300];
    double RD[]= new double[300];
    double snore[]= new double[3000];

    public void setData(double hbrRes[],double HD[],double RD[],double snore[]){
        this.hbrRes = hbrRes;
        this.HD = HD;
        this.RD = RD;
        this.snore = snore;
        postInvalidate();
    }
    public Cardiograph1View(Context context) {
        this(context,null);
    }

    public Cardiograph1View(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Cardiograph1View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        mWidth = w;
        mHeight = h;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        scrollBy(1,0);
        drawPath(canvas);
        postInvalidateDelayed(10);
        super.onDraw(canvas);
    }

    private void drawPath(Canvas canvas) {
        // 重置path

        mPath.reset();
        //用path模拟一个心电图样式
        mPath.moveTo(0,mHeight/2);
        int tmp = 0;
        for(int i = 0;i<RD.length;i++) {
            mPath.lineTo(tmp+20, (float) (mHeight / 2 + RD[i])-200);
            tmp = tmp+20;
        }
        //设置画笔style
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mLineColor);
        mPaint.setStrokeWidth(5);
        canvas.drawPath(mPath,mPaint);
    }
}

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
import com.lzhz.lxh.sleepmonitor.tools.LogUtils;

/**
 * 作者：lxh on 2018-01-10:16:50
 * 邮箱：15911638454@163.com
 */

public class HistogramView extends View {
    private Paint mPaintLineGray; //绘制灰线
    private int lineGrayColor;
    private int lineGray1Color;
    private float lineGraySize;
    private float mViewWidth;
    private float mViewHeight;
    private int[] data;
    float count = 0;
    public HistogramView(Context context) {
        super(context);
        initPaint();
    }

    public HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context,attrs);
        initPaint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.ChartView);

        lineGrayColor = ta.getColor(R.styleable.ChartView_lineGrayColor, Color.YELLOW);
        lineGray1Color = ta.getColor(R.styleable.ChartView_textGrayColor, Color.YELLOW);
        lineGraySize = ta.getDimension(R.styleable.ChartView_lineGraySize,30);

    }

    public void setData(int[] data){
        this.data = data;
        for (int i = 0;i<data.length;i++){
            count += data[i];
        }
        invalidate();
    }
    private void initPaint(){
        mPaintLineGray = new Paint();
        mPaintLineGray.setStyle(Paint.Style.FILL);
        //设置线的宽度
        mPaintLineGray.setStrokeWidth(lineGraySize);
        //设置画笔的颜色
        mPaintLineGray.setColor(lineGrayColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        float mWidth = mViewWidth / count;
        int shiy = 0;
        for (int i = 0;i<data.length;i++){
            shiy = 0;
            for (int j = 0;j < i;j++){
                shiy+=data[j];
            }
            if(i%2==0){
                mPaintLineGray.setColor(lineGrayColor);
                canvas.drawRect(mWidth*shiy,0,mWidth*(data[i]+shiy),mViewHeight,mPaintLineGray);
            }else{
                mPaintLineGray.setColor(lineGray1Color);
                canvas.drawRect(mWidth*shiy,0,mWidth*(data[i]+shiy),mViewHeight,mPaintLineGray);
            }
            LogUtils.i("mWidth="+mWidth +"---shiy=" +shiy+"mWidth*shiy = "+mWidth*shiy+"--mWidth*data[i] = "+mWidth*data[i]);
        }

    }
}

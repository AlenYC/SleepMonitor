package com.lzhz.lxh.sleepmonitor.tools.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.lzhz.lxh.sleepmonitor.R;

/**
 * 作者：lxh on 2018-01-10:10:40
 * 邮箱：15911638454@163.com
 */

public class RoundWireView extends View {
    private int mViewHeight; //当前View的高
    private int mViewWidth; //当前View的宽

    private Paint mPaintLine; //绘制折线
    private float lineSize;   //线的宽
    private int lineColor;   //线的颜色

    private Paint mPaintLineGray; //绘制灰线
    private int lineGrayColor;
    private float lineGraySize;

    private Paint mPaintTextGray; //绘制灰线
    private int textGrayColor;
    private float textGraySize;

    private Paint mPaintTextTitle;
    private int TextTitleColor;
    private float TextTitleSize;
    private String TextTitle; //标题
    private String TextMean;  // 平均次数
    private String TextMinuteMean;  // 每分钟平均呼吸次数
    Bitmap bitmap;
    int[] count; //折线数组
    String[] date;//日期数组

    public RoundWireView(Context context) {
        super(context);
        InitPaint();
    }

    public RoundWireView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        getAttrs(context,attrs);
        InitPaint();
    }
    public void setCount(int[] count,String[] date){
        this.count =count;
        this.date = date;
        invalidate();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
    }

    //初始化画笔
    private void InitPaint() {
        mPaintLine = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置画线
        mPaintLine.setStyle(Paint.Style.STROKE);
        mPaintLine.setStrokeCap(Paint.Cap.ROUND);
        //设置线的宽度
        mPaintLine.setStrokeWidth(lineSize);
        //设置画笔的颜色
        mPaintLine.setColor(lineColor);


        mPaintLineGray = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintLineGray.setStrokeWidth(lineGraySize);
        //设置画笔的颜色
        mPaintLineGray.setColor(lineGrayColor);
        mPaintLineGray.setTextSize(30);

        mPaintTextGray = new Paint(Paint.ANTI_ALIAS_FLAG);
        //设置填充
        mPaintTextGray.setStyle(Paint.Style.FILL);
        mPaintTextGray.setColor(textGrayColor);
        mPaintTextGray.setTextSize(textGraySize);


        mPaintTextTitle = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTextTitle.setStyle(Paint.Style.FILL);
        mPaintTextTitle.setColor(TextTitleColor);
        mPaintTextTitle.setTextSize(TextTitleSize);


    }

    //获取设置的属性
    @SuppressLint("ResourceType")
    private void getAttrs(Context context, AttributeSet attrs) {
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ChartView);
        //折线颜色
        lineColor = ta.getColor(R.styleable.ChartView_lineColor, Color.BLACK);
        lineSize = ta.getDimension(R.styleable.ChartView_lineSize, 2f);

        //折线颜色
        lineGrayColor = ta.getColor(R.styleable.ChartView_lineGrayColor, Color.BLACK);
        lineGraySize = ta.getDimension(R.styleable.ChartView_lineGraySize, 2f);
        textGrayColor = ta.getColor(R.styleable.ChartView_textGrayColor, Color.BLACK);
        textGraySize = ta.getDimension(R.styleable.ChartView_textGraySize, 2f);

        TextTitleColor =  ta.getColor(R.styleable.ChartView_TextTitleColor, Color.BLACK);
        TextTitle = ta.getString(R.styleable.ChartView_TextTitle);
        TextMinuteMean = ta.getString(R.styleable.ChartView_TextMinuteMean);
        TextMean = ta.getString(R.styleable.ChartView_TextMean);
        TextTitleSize = ta.getDimension(R.styleable.ChartView_TextTitleSize, 40);

        bitmap  = BitmapFactory.decodeResource(context.getResources(), R.mipmap.inf3x);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 绘制折线
         */
      if(count.length == 0){
          return;
      }
      float xHeight = mViewHeight/3;
      float xwidth = mViewWidth/11*10;
        for(int i=0;i<count.length-1;i++){
            //坐标系内的点的位置
            float startX;
            if(i == 0){
                 startX = 80;
            }else{
                 startX = xwidth/(count.length-1)*i+80;
            }
            float startY = xHeight/100*count[i] + xHeight;
            float endX = startX;
            float endY = xHeight/100*count[i+1] + xHeight;
            //画折线图  圆点
            canvas.drawLine(startX,startY,endX,endY,mPaintLine);


        }
        //日期文字
        for (int i = 0;i <date.length;i++){
            canvas.drawText(date[i],xwidth/date.length*i+60,mViewHeight/4*3+50,mPaintLineGray);
        }
        canvas.drawLine(mViewWidth - 40,mViewHeight/3,40,mViewHeight/3,mPaintLineGray);
        canvas.drawLine(mViewWidth - 40,mViewHeight/4*3,40,mViewHeight/4*3,mPaintLineGray);

        canvas.drawText(TextTitle,40,mViewHeight/6,mPaintTextTitle);

        mPaintLineGray.setTextSize(40);
        canvas.drawText("日平均值:"+TextMinuteMean,mViewWidth/3,mViewHeight/6,mPaintLineGray);

        mPaintLineGray.setTextSize(36);
        canvas.drawText(TextMean,40,mViewHeight/3 - 30,mPaintLineGray);
        //画图
        canvas.drawBitmap(bitmap,mViewWidth - 40 - bitmap.getWidth(),mViewHeight/6 - bitmap.getWidth(),mPaintLineGray);

    }
}

package com.wxj.customview.circle.scale;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

import com.wxj.customview.utils.LogUtils;

/**
 * Created by wuxiaojun on 16-9-1.
 */
public class CircleScaleView extends View {

    //绘制圆弧画笔
    private Paint mArcPaint;
    //环形渐变
    private SweepGradient mSweepGradient;
    //圆环中心点
    private int mCenterX;
    private int mCenterY;
    //圆环所在区域
    private RectF mRectF;
    //渐变颜色 你可以添加很多种但是至少要保持在2种颜色以上
    int[] colors = {0xffff4639, 0xffCDD513, 0xff3CDF5F};
    //间距角度
    private float spaceingAngle = 10;
    //起始角度
    private float startAngle = -90;
    //结束角度
    private float endAngle = 360;
    //绘制线条的画笔
    private Paint mTextPaint;


    public CircleScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();

    }

    private void init() {
        mArcPaint = new Paint();
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(20);

        mRectF = new RectF();

        mTextPaint = new Paint();
        mTextPaint.setAntiAlias(true);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if(changed){
            mCenterX = getMeasuredWidth() / 2;
            mCenterY = getMeasuredHeight() / 2;
            mRectF.left = mArcPaint.getStrokeWidth();
            mRectF.top = mArcPaint.getStrokeWidth();
            mRectF.right = right - mArcPaint.getStrokeWidth() - left;
            mRectF.bottom = bottom - mArcPaint.getStrokeWidth() - top;
            //环形渐变
            mSweepGradient = new SweepGradient(mCenterX,mCenterY,colors,null);
            Matrix mMatrix = new Matrix();
            mMatrix.setRotate(-90,mCenterX,mCenterY);
            mSweepGradient.setLocalMatrix(mMatrix);
            mArcPaint.setShader(mSweepGradient);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawArc(canvas);
        drawScale(canvas);
        drawText(canvas);
    }


    /***
     * 绘制文本
     * @param canvas
     */
    private void drawText(Canvas canvas) {
        for (int i = 1; i <= 12; i++) {
            canvas.rotate(360 / 12, mCenterX, mCenterY);
            float measureText = mTextPaint.measureText(i + "");
            canvas.drawText(i+"",(mCenterX-measureText/2),mCenterY-(mCenterY-mArcPaint.getStrokeWidth())+mArcPaint.getStrokeWidth()+20,mTextPaint);
        }
    }

    /***
     * 绘制刻度
     * @param canvas
     */
    private void drawScale(Canvas canvas) {
        mArcPaint.setColor(Color.WHITE);
        mArcPaint.setShader(null);
        int count = (int) (endAngle / spaceingAngle);
        for (int i = 0; i < count; i++) {
            canvas.drawArc(mRectF,(startAngle + (i*spaceingAngle)),0.8f,false,mArcPaint);
        }
    }

    /***
     * 绘制圆环
     * @param canvas
     */
    private void drawArc(Canvas canvas) {
        mArcPaint.setShader(mSweepGradient);
        canvas.drawArc(mRectF,startAngle,endAngle,false,mArcPaint);
    }

}

package com.wxj.customview.flow.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.wxj.customview.utils.LogUtils;

/**
 * Created by wuxiaojun on 16-9-1.
 */
public class FloatView extends View {

    private int width = 150;
    private int height = 150;

    private Paint mCirclePaint;
    private Paint mTextPaint;
    private float radius;
    private String mText = "50%";

    public FloatView(Context context) {
        this(context, null);
    }

    public FloatView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FloatView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setColor(Color.GRAY);

        radius = width/2;

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(25);
        mTextPaint.setFakeBoldText(true);//文字加粗
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制圆和字体
        canvas.drawCircle(width / 2, height / 2, radius, mCirclePaint);

        float measureText = mTextPaint.measureText(mText);
        float mTextX = radius/2 - measureText/2;

        Paint.FontMetrics metrics = mTextPaint.getFontMetrics();
        float dy = -(metrics.ascent+metrics.descent)/2;
        LogUtils.e("ascent=" + metrics.ascent + "====descent=" + metrics.descent);
        float mTextY = height/2 + dy;
        canvas.drawText(mText,mTextX,mTextY,mTextPaint);
    }


}

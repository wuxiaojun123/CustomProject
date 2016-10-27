package com.wxj.customview.viewgroupEvent;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.wxj.customview.utils.LogUtils;

/**
 * Created by wuxiaojun on 16-6-29.
 */
public class EventView extends View {

    private Rect mRect;
    private Paint mPaint;

    public EventView(Context context) {
        this(context, null);
    }

    public EventView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EventView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mRect = new Rect(0,0,200,200);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(mRect,mPaint);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                LogUtils.e("dispatchTouchEvent中ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.e("dispatchTouchEvent中ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.e("dispatchTouchEvent中ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                LogUtils.e("onTouchEvent中ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.e("onTouchEvent中ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.e("onTouchEvent中ACTION_UP");
                break;
        }
        return true;
    }


}

package com.wxj.customview.water.ripple;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * 需求
 * 　　用户点击某一个区域，产生一个水波纹，然后慢慢扩散的效果
 * 思路
 * 　　先获取点击了那个view，然后获取这个view的宽和高(按下的时候就获取)
 * 　　获取宽和高之后绘制一个圆形，松开手之后，扩散到区域之外
 * <p/>
 * Created by wuxiaojun on 16-10-31.
 */
public class MyWaterRippleView extends LinearLayout {

    public static final int DURATION = 1000;//动画扩散的时间

    private int targetViewWidth;//目标的宽和高
    private int targetViewHeight;
    private int increment = 10;//增量，用户松开之后，每次增加多少扩散
    private int mCircleRadius;//开始时候的圆的半径

    private Paint mPaint;//绘制的画笔


    public MyWaterRippleView(Context context) {
        this(context, null);
    }

    public MyWaterRippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWaterRippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
    }

    /***
     * 获取用户点击在那个view上
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int currentX = (int) ev.getRawX();
        int currentY = (int) ev.getRawY();
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:

                break;
        }
        return super.dispatchTouchEvent(ev);
    }


}

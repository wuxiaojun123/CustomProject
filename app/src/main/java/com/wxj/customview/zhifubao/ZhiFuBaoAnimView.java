package com.wxj.customview.zhifubao;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import com.wxj.customview.utils.LogUtils;

/**
 * Created by wuxiaojun on 16-7-18.
 */
public class ZhiFuBaoAnimView extends View {

    //绘制√和×及快速旋转弧形的动画时间
    //private static final long NORMAL_ANIMATION_DURATION = 350L;
    //绘制正常弧形的动画时间
    private static final long NORMAL_ANGLE_ANIMATION_DURATION = 10000L;
    //绘制偏移角度的动画时间
    private static final long NORMAL_CIRCLE_ANIMATION_DURATION = 20000L;

    private Paint mArcPaint;//圆弧
    private RectF ovalRectF;//圆弧的范围
    private float mStartAngle;
    private float mEndAngle;
    private float mCircleAngle;

    public ZhiFuBaoAnimView(Context context) {
        this(context, null);
    }

    public ZhiFuBaoAnimView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZhiFuBaoAnimView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mArcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mArcPaint.setStyle(Paint.Style.STROKE);//设置画笔样式
        mArcPaint.setStrokeWidth(10);
        mArcPaint.setStrokeJoin(Paint.Join.ROUND);
        mArcPaint.setStrokeCap(Paint.Cap.ROUND);
        mArcPaint.setColor(Color.RED);


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //矩形的大小
        ovalRectF = new RectF(w / 2 - 100, h / 2 - 100, w / 2 + 100, h / 2 + 100);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        //画第一段圆弧
        //canvas.drawArc(ovalRectF, startAngle, sweepAngle, false, mArcPaint);

        float offsetAngle = mCircleAngle * 360;
        float startAngle = mEndAngle * 360;
        float sweepAngle = mStartAngle * 360;

        if (startAngle == 360)
            startAngle = 0;
        sweepAngle = sweepAngle - startAngle;
        startAngle += offsetAngle;

        if (sweepAngle < 0)
            sweepAngle = 1;

        LogUtils.e("开始角度是"+startAngle+"====扫过的角度是:"+sweepAngle);

        canvas.drawArc(ovalRectF, startAngle, sweepAngle, false, mArcPaint);
    }

    private ValueAnimator mStartAngleAnimator;
    private ValueAnimator mEndAngleAnimator;
    private ValueAnimator mCircleAnimator;

    /***
     * 开始动画
     */
    public void startAnima() {
        mStartAngleAnimator = ValueAnimator.ofFloat(0.0F, 1.0F);
        mEndAngleAnimator = ValueAnimator.ofFloat(0.0F, 1.0F);
        mCircleAnimator = ValueAnimator.ofFloat(0.0F, 1.0F);

        mStartAngleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                setStartAngle(value);
            }
        });
        mEndAngleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (Float) animation.getAnimatedValue();
                setEndAngle(value);
            }
        });

        mStartAngleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                if (mEndAngleAnimator != null) {
                    mEndAngleAnimator.setStartDelay(4000l);
                    mEndAngleAnimator.start();
                }
            }
        });

        mEndAngleAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (mStartAngleAnimator != null) {
                    mStartAngleAnimator.start();
                }
            }
        });

        mCircleAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                setCircleAngle(value);
            }
        });


        mStartAngleAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mEndAngleAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        mCircleAnimator.setInterpolator(new LinearInterpolator());
        mCircleAnimator.setRepeatCount(-1);


        mStartAngleAnimator.setDuration(NORMAL_ANGLE_ANIMATION_DURATION);
        mEndAngleAnimator.setDuration(NORMAL_ANGLE_ANIMATION_DURATION);
        mCircleAnimator.setDuration(NORMAL_CIRCLE_ANIMATION_DURATION);
        mStartAngleAnimator.start();
    }

    private void setStartAngle(float startAngle) {
        this.mStartAngle = startAngle;
        invalidate();
    }

    private void setEndAngle(float endAngle) {
        this.mEndAngle = endAngle;
        invalidate();
    }

    private void setCircleAngle(float circleAngle) {
        this.mCircleAngle = circleAngle;
        invalidate();
    }
}

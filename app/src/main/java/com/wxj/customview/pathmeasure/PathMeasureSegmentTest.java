package com.wxj.customview.pathmeasure;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.wxj.customview.utils.LogUtils;

/**
 * Created by wuxiaojun on 16-9-6.
 */
public class PathMeasureSegmentTest extends View {

    private Paint mPaint;
    private Path mPath;
    private PathMeasure mPathMeasure;
    private Path dstPath;

    public PathMeasureSegmentTest(Context context, AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);

        mPath = new Path();
        mPathMeasure = new PathMeasure();
        dstPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPath.moveTo(100, 100);
        mPath.rLineTo(100, 100);
        mPath.moveTo(200, 100);
        mPath.rLineTo(-100, 100);

        mPaint.setColor(Color.RED);
        canvas.drawPath(dstPath, mPaint);
    }

    private float lenght;
    private boolean isNextFlag;

    public void startAnim() {
        //这里每次都重置，因为每次点击就都可以看到效果
        dstPath.rewind();
        isNextFlag = true;
        mPathMeasure.setPath(mPath, false);
        lenght = mPathMeasure.getLength();
        final ValueAnimator mValueAnimator = ValueAnimator.ofFloat(0.0f, 1.0f);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setDuration(200);
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                //19版本之前要想看到效果最简单的方法就是使用rLineTo(0,0)
                dstPath.rLineTo(0, 0);
                //获取一个段落
                mPathMeasure.getSegment(0, lenght * value, dstPath, true);
                invalidate();
            }
        });
        mValueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (isNextFlag) {
                    //绘制完一条线之后，再绘制另外一条线
                    mPathMeasure.nextContour();
                    lenght = mPathMeasure.getLength();
                    mValueAnimator.start();
                    isNextFlag = false;
                }
            }
        });
        mValueAnimator.start();
    }

}

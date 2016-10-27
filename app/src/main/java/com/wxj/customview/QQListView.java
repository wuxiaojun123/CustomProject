package com.wxj.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by wuxiaojun on 16-8-1.
 */
public class QQListView extends ListView {


    public QQListView(Context context) {
        super(context);
    }

    public QQListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QQListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ImageView mImageView;
    private int mImageViewHeight;

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        /***ang
         * 监听ListView(ScrollView)的滑动过度
         * dx,dy增量
         * deltaY:
         * -:下拉过度
         * +:上拉过度
         */
        if (deltaY < 0) {
            mImageView.getLayoutParams().height = mImageView.getHeight() - deltaY;
            mImageView.requestLayout();
        } else {
            if (mImageView.getHeight() > mImageViewHeight) {


            }
        }


        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //让ImageView在上滑时候放大，监听
        View header = (View) mImageView.getParent();
        //header.getTop()<0的头部滑出去的距离
        if (header.getTop() < 0 && mImageView.getHeight() > mImageViewHeight) {
            mImageView.getLayoutParams().height = mImageView.getHeight();
            header.layout(header.getLeft(), 0, header.getRight(), header.getBottom());
            mImageView.requestLayout();
        }
        super.onScrollChanged(l, t, oldl, oldt);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP) {
            ResetAnimation animation = new ResetAnimation();
            animation.setDuration(300);
            mImageView.startAnimation(animation);
        }
        return super.onTouchEvent(ev);
    }

    public class ResetAnimation extends Animation {

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {


            super.applyTransformation(interpolatedTime, t);
        }
    }


}

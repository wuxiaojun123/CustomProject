package com.wxj.customview.qq;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.AbsListView;
import android.widget.ListView;

import com.wxj.customview.utils.DensityUtil;
import com.wxj.customview.utils.LogUtils;

/**
 * Created by wuxiaojun on 16-8-17.
 */
public class ImitateQQListView extends ListView {

    public View mHeaderView;
    private int mHeaderHeight;
    public View mHeaderTitleView;
    public float mMaxScrollY;

    public ImitateQQListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY,
                                   int scrollX, int scrollY,
                                   int scrollRangeX, int scrollRangeY,
                                   int maxOverScrollX, int maxOverScrollY,
                                   boolean isTouchEvent) {
        if (deltaY < 0) {
            //表示下拉，需要改变高度
            if (mHeaderView != null) {
                mHeaderView.getLayoutParams().height = mHeaderView.getHeight() - deltaY;
                mHeaderView.requestLayout();
            }
        }

        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (t < 0) {
            mHeaderView.getLayoutParams().height = mHeaderHeight;
            mHeaderView.requestLayout();
        }
        //获取到listview滚动的y轴距离
        if (mHeaderView != null) {
            calculationAlpha(mHeaderView.getTop());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                //判断头部的高度是否与初始化时候的高度一致，如果不一致则需要执行动画缓慢撤回到原来状态
                if (mHeaderHeight != mHeaderView.getHeight()) {
                    //执行动画缓慢回到原来位置
                    resetHeadViewHeight();
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    /***
     * 执行动画缓慢回到原来状态
     * 计算思路:总共多出(mHeaderView.getHeight() - mHeaderHeight)
     */
    private void resetHeadViewHeight() {
        ValueAnimator mValueAnimator = ValueAnimator.ofFloat(1.0f);
        mValueAnimator.setDuration(600);
        mValueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                //计算高度
                mHeaderView.getLayoutParams().height = (int) (mHeaderView.getHeight() - value * (mHeaderView.getHeight() - mHeaderHeight));
                mHeaderView.requestLayout();
            }
        });
        mValueAnimator.start();
    }

    /****
     * 根据滚动距离计算alpha值
     *
     * @param top
     */
    private void calculationAlpha(int top) {
        mHeaderTitleView.setAlpha(Math.abs(1.0f / mMaxScrollY * top));
    }

    @Override
    public void addHeaderView(View v, Object data, boolean isSelectable) {
        super.addHeaderView(v, data, isSelectable);
        if (mHeaderView == null) {
            mHeaderView = v;
            mHeaderHeight = v.getMeasuredHeight();
            mMaxScrollY = mHeaderHeight - DensityUtil.dip2px(getContext(), 50);
        }
    }
}

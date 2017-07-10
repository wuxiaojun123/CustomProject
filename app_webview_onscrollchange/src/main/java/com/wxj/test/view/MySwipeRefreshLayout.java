package com.wxj.test.view;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.wxj.test.util.LogUtils;

/**
 * Created by wuxiaojun on 17-2-13.
 */

public class MySwipeRefreshLayout extends SwipeRefreshLayout {


    public MySwipeRefreshLayout(Context context) {
        super(context);
    }

    public MySwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*@Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        LogUtils.e("onInterceptTouchEvent 返回值是" + super.onInterceptTouchEvent(ev));
        return false;
    }*/

    /***
     * 调用这个方法时，表示向下滑动就会刷新swiperefreshlayout，但是webview还是可以滑动的,
     * 关键：判断webview是否滑动到了顶端?
     * <p>
     * 第一种情况，确实滑动到了最顶端
     * 第二种情况，还没有滑动到最顶端
     * 难点：如何区分是否滑动到了顶端
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        LogUtils.e("onTouchEvent  " + super.onTouchEvent(ev));
        return super.onTouchEvent(ev);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        LogUtils.e("onScrollChanged 中t=" + t);
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        LogUtils.e("  scrollY = " + scrollY + "   clampedY=" + clampedY);
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {
        LogUtils.e("swiperefreshlayout中  deltaY=" + deltaY + " scrollY=" + scrollY + " scrollRangeY=" + scrollRangeY);

        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }


}

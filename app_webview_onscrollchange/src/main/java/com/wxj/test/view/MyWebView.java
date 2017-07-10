package com.wxj.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.webkit.WebView;

import com.wxj.test.util.LogUtils;

/**
 * Created by wuxiaojun on 17-2-13.
 */

public class MyWebView extends WebView {

    public MySwipeRefreshLayout swipeRefreshLayout;
    public boolean isTop = false;


    public MyWebView(Context context) {
        this(context, null);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtils.e("onInterceptTouchEvent 返回值是" + super.onInterceptTouchEvent(ev));

        return super.onInterceptTouchEvent(ev);
    }

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
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    @Override
    protected boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX, int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent) {

        /*LogUtils.e("deltaY=" + deltaY + " scrollY=" + scrollY + " scrollRangeY=" + scrollRangeY);
        if (deltaY < 0 && scrollY == 0) { // 表示向下滑动,并且scrollY=0表示滑动到最顶端
            isTop = true;
        } else {
            isTop = false;
        }*/

        return super.overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX, maxOverScrollY, isTouchEvent);
    }


}

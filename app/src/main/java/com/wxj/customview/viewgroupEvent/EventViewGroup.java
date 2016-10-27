package com.wxj.customview.viewgroupEvent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.wxj.customview.utils.LogUtils;

/**
 * Created by wuxiaojun on 16-6-29.
 */
public class EventViewGroup extends ViewGroup {

    public EventViewGroup(Context context) {
        this(context, null);
    }

    public EventViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EventViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        for (int i = 0; i< count; i++){
            View child = getChildAt(i);
            child.layout(child.getLeft(), child.getTop(), child.getLeft() + child.getMeasuredWidth(), child.getTop() + child.getMeasuredHeight());
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
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
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_DOWN:
                LogUtils.e("onInterceptTouchEvent中ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                LogUtils.e("onInterceptTouchEvent中ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                LogUtils.e("onInterceptTouchEvent中ACTION_UP");
                break;
        }

        return super.onInterceptTouchEvent(ev);
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
        return super.onTouchEvent(event);
    }

}

package com.wxj.customview.viewdraghelpertest;

import android.content.Context;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.wxj.customview.R;

/**
 * Created by wuxiaojun on 17-7-10.
 */

public class VerticalDragView extends LinearLayout {

    public static final int MIN_TOP = 100;

    private ScrollView topScrollView;
    private Button dragBtn;
    private ScrollView bottomScrollView;

    private int dragBtnHeight;
    private ViewDragHelper viewDragHelper;

    public VerticalDragView(Context context) {
        this(context, null);
    }

    public VerticalDragView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalDragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        viewDragHelper = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {


            @Override
            public boolean tryCaptureView(View child, int pointerId) {
                // 捕获view
                return dragBtn == child;
            }

            @Override
            public int clampViewPositionVertical(View child, int top, int dy) {
                if (top > getHeight() - dragBtnHeight) { // 顶部边界

                    top = getHeight() - dragBtnHeight;

                } else if (top < MIN_TOP) { // 底部边界

                    top = MIN_TOP;

                }
                return top;
            }

            @Override
            public int getViewVerticalDragRange(View child) {
                return getMeasuredHeight() - child.getMeasuredHeight();
            }

            @Override
            public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
                super.onViewPositionChanged(changedView, left, top, dx, dy);

                LinearLayout.LayoutParams bottomParams = (LayoutParams) bottomScrollView.getLayoutParams();
                bottomParams.height = bottomParams.height + dy * -1;
                bottomScrollView.setLayoutParams(bottomParams);

                LinearLayout.LayoutParams topParams = (LayoutParams) topScrollView.getLayoutParams();
                topParams.height = topParams.height + dy;
                topScrollView.setLayoutParams(topParams);

            }

        });

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return viewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        viewDragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        topScrollView = (ScrollView) findViewById(R.id.id_top_scrollview);
        dragBtn = (Button) findViewById(R.id.id_btn_drag);
        bottomScrollView = (ScrollView) findViewById(R.id.id_bottom_scrollview);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        dragBtnHeight = dragBtn.getMeasuredHeight();

    }


}

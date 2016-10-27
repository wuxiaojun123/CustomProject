package com.wxj.customview.flowView;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;

import com.wxj.customview.utils.LogUtils;

/**
 * Created by wuxiaojun on 16-7-31.
 */
public class ZHYFlowViewGroup1 extends ViewGroup {


    public ZHYFlowViewGroup1(Context context) {
        this(context, null);
    }

    public ZHYFlowViewGroup1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        //自定义控件，但是没有使用自定义属性才会调用

    }

    public ZHYFlowViewGroup1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //有自定义属性

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);

        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        //wrap_content = AT_MOST
        int width = 0;
        int height = 0;

        //每一行的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;
        //得到内部元素的个数
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            //测量子view的宽和高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            //得到LayoutParams
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();


            int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            if (lineWidth + childWidth > sizeWidth) {
                //对比得到最大的宽度，需要换行
                width = Math.max(width, lineWidth);
                //充值lineWidth
                lineWidth = childWidth;
                //记录行高
                height += lineHeight;

                lineHeight = childHeight;
            } else {
                //未换行
                //叠加行宽
                lineWidth += childWidth;
                //得到当前行最大的高度
                lineHeight = Math.max(lineHeight, childHeight);
            }

            //最后一个控件,叠加高度，对比宽度
            if (i == cCount - 1) {
                width = Math.max(lineWidth, width);
                height += lineHeight;
            }
        }

        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY ? sizeWidth : width,
                modeHeight == MeasureSpec.EXACTLY ? sizeHeight : height);

    }

    /***
     * 存储所有的View
     */
    private List<List<View>> mAllViews = new ArrayList<>();

    /***
     * 每一行的高度
     */
    private List<Integer> mLineHeight = new ArrayList<>();


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //摆放位置
        mAllViews.clear();
        mLineHeight.clear();

        //当前ViewGroup的宽度
        int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;

        List<View> lineViews = new ArrayList<>();

        int cCount = getChildCount();

        for (int i = 0; i < cCount; i++) {
            View child = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childWidth = child.getMeasuredWidth();
            int childHeight = child.getMeasuredHeight();

            //如果需要换行
            if (childWidth + lineWidth + lp.leftMargin + lp.rightMargin > width) {
                //记录LineHeight
                mLineHeight.add(lineHeight);
                //记录当前行的Views
                mAllViews.add(lineViews);
                //重置我们的行宽和行高
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;

                lineViews = new ArrayList<View>();
            }

            lineWidth += childWidth + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, childHeight + lp.topMargin + lp.bottomMargin);

            lineViews.add(child);
        }
        //处理最后一行
        mLineHeight.add(lineHeight);
        mAllViews.add(lineViews);

        //设置子View的位置
        int left = 0;
        int top = 0;
        //行数
        int lineNum = mAllViews.size();

        for (int i = 0; i < lineNum; i++) {
            //当前行的所有的View
            lineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            for (int j = 0; j < lineViews.size(); j++) {
                View child = lineViews.get(j);
                //判断child的状态
                if (child.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

                int lc = left + lp.leftMargin;
                int tc = top + lp.topMargin;
                int rc = lc + child.getMeasuredWidth();
                int bc = tc + child.getMeasuredHeight();

                //对子View进行布局
                child.layout(lc, tc, rc, bc);

                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }

            left = 0;
            top += lineHeight;
        }
    }

    /**
     * 与当前ViewGroup对应的LayoutParams
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        Log.e("TAG11111111111","generateLayoutParams1111111111111111111111");
        System.out.println("和哈哈哈哈哈哈哈哈哈");
        LogUtils.d("执行generateLayoutParams");
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        Log.e("TAG22222222222","generateLayoutParams1111111111111111111111");
        return super.generateDefaultLayoutParams();
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        Log.e("TAG33333333333","generateLayoutParams1111111111111111111111");
        return super.generateLayoutParams(p);
    }
}

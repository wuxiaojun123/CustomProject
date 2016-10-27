package com.wxj.customview.flowView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.wxj.customview.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 思路：
 * 测量：获取到最宽的宽度，然后每一个childView+lineWidth跟最宽的宽度比较得出结果为是否需要换行
 * 布局：没一行到达最宽的宽度那么需要换行显示，每一行是水平布局，水平布局达到最大之后，就需要换行
 * 显示，把每一行的所有childView放到一个集合中，然后集合遍历摆放就能实现
 * <p/>
 * Created by wuxiaojun on 16-7-31.
 */
public class ZHYFlowViewGroup2 extends ViewGroup {


    public ZHYFlowViewGroup2(Context context) {
        this(context, null);
    }

    public ZHYFlowViewGroup2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZHYFlowViewGroup2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /***
     * 需要支持wrap_content(为什么自定义布局设置wrap_content的结果跟march_parent的效果一样？)
     * 因为如果不重写的话，那么会执行view的getDefaultSize(),而getDefaultSize方法中只分为
     * UNSPECIFIED和AT_MOST,EXACTLY模式，也就是说AT_MOST和EXACTLY的处理方式是一样的都是测量大小
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        //总共的宽度和高度
        int width = 0;
        int height = 0;

        //每一行的宽度和高度
        int lineWidth = 0;
        int lineHeight = 0;

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            //获取子类
            View childView = getChildAt(i);
            //测量子view的大小
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();

            int childWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            //判断是否需要换行
            if (lineWidth + childWidth > widthSize) {
                //将原来最大的宽度和现在这一行的宽度进行对比，为了对比出最大的宽度
                width = Math.max(width, lineWidth);
                //行宽重置为0
                lineWidth = childWidth;
                //行高需要叠加
                height += lineHeight;
                //重置行高
                lineHeight = childHeight;
            } else {
                //对比高度，叠加宽度
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight, childHeight);
            }
            //如果是最后一个,需要对比子view的宽度
            if (i == childCount - 1) {
                //当最后一个view是处于某一行的最后一个的时候，那么需要对比宽度找出最大宽度
                width = Math.max(lineWidth, width);
                //当最后一个view是处于某一行的某一个的时候，那么需要把高度进行叠加，因为这个子view是走的上边else方法没有将行高进行叠加
                height += lineHeight;
            }
        }

        setMeasuredDimension(widthMode == MeasureSpec.EXACTLY ? widthSize : width, heightMode == MeasureSpec.EXACTLY ? heightSize : height);
    }

    //存放某一行中的所有view
    private List<List<View>> mAllViews = new ArrayList<>();
    //存放所有行的行高是为了调用childView.layout的时候计算出距离上边的高度
    private List<Integer> mLineHeight = new ArrayList<>();

    /***
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        mAllViews.clear();
        mLineHeight.clear();
        //摆放位置
        int width = getMeasuredWidth();
        int lineHeight = 0;
        int lineWidth = 0;
        //先把每一行的所有view都存放到mAllViews的集合中
        List<View> mLineViews = new ArrayList<>();
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            //判断是否需要换行
            if (lineWidth + lp.leftMargin + lp.rightMargin + childView.getMeasuredWidth() > width) {

                mLineHeight.add(lineHeight);
                mAllViews.add(mLineViews);
                mLineViews = new ArrayList<>();
                lineWidth = 0;
                lineHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            }
            //得到每一行中最高的行高
            lineHeight = Math.max(lineHeight, childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
            //把宽度进行累加
            lineWidth += childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            mLineViews.add(childView);
        }
        //处理最后一行
        mAllViews.add(mLineViews);
        mLineHeight.add(lineHeight);

        int left = 0;
        int top = 0;

        int size = mAllViews.size();
        for (int i = 0; i < size; i++) {
            mLineViews = mAllViews.get(i);
            lineHeight = mLineHeight.get(i);

            int mLineViewSize = mLineViews.size();

            for (int j = 0; j < mLineViewSize; j++) {
                View childView = mLineViews.get(j);
                if (childView.getVisibility() == View.GONE) {
                    continue;
                }
                MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
                int childLeft = lp.leftMargin + left;
                int childTop = lp.topMargin + top;
                int childRight = childView.getMeasuredWidth() + childLeft;
                int childBottom = childView.getMeasuredHeight() + childTop;

                childView.layout(childLeft, childTop, childRight, childBottom);

                left += childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = 0;
            top += lineHeight;
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }
}

package com.wxj.customview.flowView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.wxj.customview.utils.LogUtils;

/**
 * Created by wuxiaojun on 16-7-28.
 */
public class FlowViewGroup1 extends ViewGroup {

    private float mVerticalSpacing;//每个item纵向间距
    private float mHorizontalSpacing;//每个item横向间距


    public FlowViewGroup1(Context context) {
        this(context, null);
    }

    public FlowViewGroup1(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowViewGroup1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //根据提供的大小和模式获取大小  1073741824
        int selfWidth = resolveSize(0,widthMeasureSpec);
        LogUtils.d("selfWidth="+selfWidth+"========"+MeasureSpec.getMode(widthMeasureSpec));

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int childLeft = paddingLeft;
        int childTop = paddingTop;
        int lineHeight = 0;

        //通过计算每一个子控件的高度，得到自己的高度
        for(int i = 0,childCount = getChildCount();i < childCount;++i){
            View childView = getChildAt(i);
            LayoutParams childLayoutParams = childView.getLayoutParams();
            //测量一个view合适自己的大小
            childView.measure(getChildMeasureSpec(widthMeasureSpec,paddingLeft+paddingRight,childLayoutParams.width),
                    getChildMeasureSpec(heightMeasureSpec,paddingTop+paddingBottom,childLayoutParams.height));

            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            //这里每次都是lineHeight
            LogUtils.d("lineHeight="+lineHeight+"=====childHeight="+childHeight);
            lineHeight = Math.max(childHeight,lineHeight);

            //判断是否换行
            if(childLeft+childWidth+paddingRight > selfWidth){
                childLeft = paddingLeft;
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
            }else{
                childLeft += childWidth + mHorizontalSpacing;
            }
        }
        int wantedHeight = childTop + lineHeight + paddingBottom;
        setMeasuredDimension(selfWidth,resolveSize(wantedHeight,heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int myWidth = r - l;

        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();

        int childLeft = paddingLeft;
        int childTop = paddingTop;

        int lineHeight = 0;

        for(int i = 0,childCount=getChildCount();i<childCount;++i){
            View childView = getChildAt(i);

            if(childView.getVisibility() == View.GONE){
                continue;
            }

            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();

            lineHeight = Math.max(childHeight,lineHeight);

            if(childLeft + childWidth + paddingRight > myWidth){
                childLeft = paddingLeft;
                childTop += mVerticalSpacing + lineHeight;
                lineHeight = childHeight;
            }

            childView.layout(childLeft,childTop,childLeft+childWidth,childTop+childHeight);
            childLeft += childWidth + mHorizontalSpacing;
        }
    }

    public void setmVerticalSpacing(float mVerticalSpacing) {
        this.mVerticalSpacing = mVerticalSpacing;
    }

    public void setmHorizontalSpacing(float mHorizontalSpacing) {
        this.mHorizontalSpacing = mHorizontalSpacing;
    }
}

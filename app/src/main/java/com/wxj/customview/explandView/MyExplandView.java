package com.wxj.customview.explandView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wxj.customview.R;

/**
 * Created by wuxiaojun on 16-6-29.
 */
public class MyExplandView extends LinearLayout {

    private long duration = 350;
    private Context mContext;
    private TextView mNumberTextView;
    private TextView mTitleTextView;
    private RelativeLayout mContentRelativeLayout;
    private RelativeLayout mTitleRelativeLayout;
    private ImageView mArrowImageView;
    int parentWidthMeasureSpec;
    int parentHeightMeasureSpec;

    public MyExplandView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public MyExplandView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public MyExplandView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }

    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.layout_expland, this);
        mNumberTextView = (TextView) findViewById(R.id.tv_number);
        mTitleTextView = (TextView) findViewById(R.id.tv_title);
        mContentRelativeLayout = (RelativeLayout) findViewById(R.id.rl_content);
        mArrowImageView = (ImageView) findViewById(R.id.iv_arrow);
        mTitleRelativeLayout = (RelativeLayout) findViewById(R.id.rl_title);

        mTitleRelativeLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                rotateArrow();
            }
        });
        collapse(mContentRelativeLayout);
    }

    public void setContent(int resID) {
        View mView = LayoutInflater.from(mContext).inflate(resID, null);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        mView.setLayoutParams(params);
        mContentRelativeLayout.addView(mView, params);
    }


    private void rotateArrow() {
        int degree = 0;
        if(mArrowImageView.getTag() == null || mArrowImageView.getTag().equals(true)){
            mArrowImageView.setTag(false);
            degree = 180;
            expand(mContentRelativeLayout);
        }else{
            degree = 0;
            mArrowImageView.setTag(true);
            collapse(mContentRelativeLayout);
        }
        mArrowImageView.animate().setDuration(duration).rotation(degree);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        parentWidthMeasureSpec = widthMeasureSpec;
        parentHeightMeasureSpec = heightMeasureSpec;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    private void expand(final View view) {
        view.measure(parentWidthMeasureSpec, parentHeightMeasureSpec);
        final int measureHeight = view.getMeasuredHeight();
        view.setVisibility(View.VISIBLE);

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    view.getLayoutParams().height = measureHeight;
                }else{
                    view.getLayoutParams().height = (int) (measureHeight*interpolatedTime);
                }
                view.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

    private void collapse(final View view) {
        //view.measure(parentWidthMeasureSpec, parentHeightMeasureSpec);
        final int measureHeight = view.getMeasuredHeight();
        //view.setVisibility(View.VISIBLE);

        Animation animation = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if(interpolatedTime == 1){
                    view.setVisibility(View.GONE);
                }else{
                    view.getLayoutParams().height = measureHeight - (int) (measureHeight*interpolatedTime);
                    view.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        animation.setDuration(duration);
        view.startAnimation(animation);
    }

}

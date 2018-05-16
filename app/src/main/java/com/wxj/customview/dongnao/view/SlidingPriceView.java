package com.wxj.customview.dongnao.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.wxj.customview.utils.DensityUtil;

/**
 * Created by wuxiaojun on 2018/1/8.
 */

public class SlidingPriceView extends View {

	public static final String	TAG	= "SlidingPriceView";

	private Paint				mPaint;

	public SlidingPriceView(Context context) {
		this(context, null);
	}

	public SlidingPriceView(Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public SlidingPriceView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();

	}

	private void init() {
		mPaint = new Paint();
		mPaint.setAntiAlias(true);
		mPaint.setStyle(Paint.Style.STROKE);
		mPaint.setColor(Color.BLACK);
		mPaint.setTextSize(DensityUtil.dip2px(getContext(), 15));
	}

	@Override protected void onDraw(Canvas canvas) {
		Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
		canvas.drawText("abcdefg", (getMeasuredWidth()-mPaint.measureText("abcdefg")) / 2, getMeasuredHeight() / 2 + (fontMetrics.bottom - fontMetrics.top) / 2 - fontMetrics.bottom, mPaint);
	}

}

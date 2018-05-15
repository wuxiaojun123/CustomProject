package com.wxj.customview.tanqian.game;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.wxj.customview.R;

/**
 * Created by 54966 on 2018/5/14.
 */

public class GameView extends FrameLayout {

	private Paint		mRectPaint;					// 矩形画笔

	private Paint		mPersonPaint;				// 小人画笔

	private Bitmap		mPersonBitmap;				// 小人

	private int			forwardStep;				// 向前几步

	private RectF		currentLocationRectF;		// 当前位置

	private int			currentRow, currentColumn;	// 当前位置

	private int			singleRectSize;				// 单个矩形大小

	private RectF[][]	mRectFArray;				// 数组

	private int			maxWidthCount	= 5;		// 一行最大可显示矩形数

	public GameView(@NonNull Context context) {
		this(context, null);
	}

	public GameView(@NonNull Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public GameView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init();
	}

	private void init() {
		mRectPaint = new Paint();
		mRectPaint.setColor(Color.RED);
		mRectPaint.setStyle(Paint.Style.STROKE);
		mRectPaint.setStrokeWidth(5f);
		mRectPaint.setAntiAlias(true);

		mPersonPaint = new Paint();

		mPersonBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
	}

	@Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		/*
		 * int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec); int widthSpecSize
		 * = MeasureSpec.getSize(widthMeasureSpec); switch (widthSpecMode) { case
		 * MeasureSpec.EXACTLY: break; case MeasureSpec.AT_MOST: case
		 * MeasureSpec.UNSPECIFIED: // 自己测量所有childView，得到最大宽度，然后拿最大宽度和屏幕宽度相比较 break; }
		 * int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec); int
		 * heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
		 * switch(heightSpecMode){ case MeasureSpec.EXACTLY: break; case
		 * MeasureSpec.AT_MOST: case MeasureSpec.UNSPECIFIED: break; }
		 * setMeasuredDimension();
		 */
	}

	@Override protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
		super.onLayout(changed, left, top, right, bottom);
	}

	@Override protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		drawAllRect(canvas);
		drawPersonBitmap(canvas);
	}

	private void drawPersonBitmap(Canvas canvas) {
		canvas.drawBitmap(mPersonBitmap, currentLocationRectF.left, currentLocationRectF.top, mPersonPaint);
	}

	private void drawAllRect(Canvas canvas) {
		for (int i = 0; i < mRectFArray.length; i++) {
			for (int j = 0; j < mRectFArray[i].length; j++) {
				canvas.drawRect(mRectFArray[i][j], mRectPaint);
			}
		}
	}

	@Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (w <= 0 || h <= 0) {
			return;
		}
		singleRectSize = w / maxWidthCount;
		initRectF(h / singleRectSize, maxWidthCount);
		initLocation();

	}

	private void initLocation() {
		currentRow = mRectFArray.length - 1;
		currentColumn = mRectFArray[mRectFArray.length - 1].length - 1;

		// currentLocationRectF = mRectFArray[mRectFArray.length -
		// 1][mRectFArray[mRectFArray.length - 1].length - 1];
		currentLocationRectF = mRectFArray[currentRow][currentColumn];

		float left = currentLocationRectF.left + (singleRectSize - mPersonBitmap.getWidth()) / 2;
		float top = currentLocationRectF.top + (singleRectSize - mPersonBitmap.getHeight()) / 2;
		currentLocationRectF.left = left;
		currentLocationRectF.top = top;
	}

	private void initRectF(int heightCount, int widthCount) {
		mRectFArray = new RectF[heightCount][widthCount];
		RectF rectF = null;
		for (int i = 0; i < mRectFArray.length; i++) { // 行
			for (int j = 0; j < mRectFArray[i].length; j++) { // 列
				rectF = new RectF(j * singleRectSize, i * singleRectSize, (j + 1) * singleRectSize, (i + 1) * singleRectSize);
				mRectFArray[i][j] = rectF;
			}
		}
	}

	public void setForwardStep(int step) {
		switch (step) {
			case 1:
				if (currentRow == 0 && currentColumn == 0) { // 替换场景
					return;
				}
				if (currentColumn == 0) {
					currentRow = currentRow - 1;
					currentColumn = maxWidthCount - 1;
				} else {
					currentRow = currentRow;
					currentColumn = currentColumn - 1;
				}
				break;
		}

		currentLocationRectF = mRectFArray[currentRow][currentColumn];
		invalidate();
	}

	private void logE(String msg) {
		Log.e("日志", msg);
	}

}

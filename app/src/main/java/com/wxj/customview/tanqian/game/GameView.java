package com.wxj.customview.tanqian.game;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.wxj.customview.R;

import java.util.List;

/**
 * Created by 54966 on 2018/5/14.
 */

public class GameView extends FrameLayout {

	private Paint			mRectPaint;						// 矩形画笔

	private Paint			mPersonPaint;					// 小人画笔

	private Bitmap			mPersonBitmap;					// 小人


	private int				currentRow, currentColumn;		// 当前位置

	private int				singleRectSize;					// 单个矩形大小

	private int				maxWidthCount	= 5;			// 一行最大可显示矩形数

	private GameMapManager	gameMapManager;

	public List<GameBean>	gameBeanList;

	private HeroAnimation	mHeroAnimation;

	private int				mAnimationState	= 0;			// 当前绘制动画状态ID

	private GameBean		currentGameBean;

	private int				currentPosition;

	private boolean			startGo;

	public int				HERO_STEP		= 2;			// 速度

	private int				currentPointX, currentPointY;	// 当前x和y坐标

	private int				forwardStep;					// 前进步数

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

		mHeroAnimation = new HeroAnimation();
		mHeroAnimation.initAnimation(getContext());
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
		if (startGo) {
			mHeroAnimation.mHeroAnim[mAnimationState].DrawAnimation(canvas, mPersonPaint, currentPointX, currentPointY, singleRectSize);
			drawPersonDirection();
		} else {
			mHeroAnimation.mHeroAnim[mAnimationState].DrawFrame(canvas, mPersonPaint, currentPointX, currentPointY, 0);
		}
	}

	private void drawPersonDirection() {
		switch (mAnimationState) {
			case HeroAnimation.ANIM_LEFT:
				currentPointX -= HERO_STEP;
				if (currentPointX <= currentGameBean.mRectF.left) {
					forwardStep--;
					if (forwardStep > 0) {
						setPersonDirectionAndSetCurrentGameBean();
					}
				} else {
					invalidate();
				}

				break;
			case HeroAnimation.ANIM_UP:
				currentPointY -= HERO_STEP;
				if (currentPointY <= currentGameBean.mRectF.top) {
					forwardStep--;
					if (forwardStep > 0) {
						setPersonDirectionAndSetCurrentGameBean();
					}
				} else {
					invalidate();
				}

				break;
			case HeroAnimation.ANIM_RIGHT:
				currentPointX += HERO_STEP;
				if (currentPointX >= currentGameBean.mRectF.left) {
					forwardStep--;
					if (forwardStep > 0) {
						setPersonDirectionAndSetCurrentGameBean();
					}
				} else {
					invalidate();
				}

				break;
			case HeroAnimation.ANIM_DOWN:
				currentPointY += HERO_STEP;
				if (currentPointY >= currentGameBean.mRectF.bottom) {
				} else {
					invalidate();
				}

				break;
		}
	}

	private void drawAllRect(Canvas canvas) {
		for (int i = 0; i < gameBeanList.size(); i++) {
			if (gameBeanList.get(i).hide) {
				canvas.drawRect(gameBeanList.get(i).mRectF, mRectPaint);
			}
		}
	}

	@Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (w <= 0 || h <= 0) {
			return;
		}
		singleRectSize = w / maxWidthCount;

		gameMapManager = new GameMapManager();
		gameBeanList = gameMapManager.getMapFirst(h / singleRectSize, maxWidthCount, singleRectSize);

		currentPosition = 0;
		currentGameBean = gameBeanList.get(currentPosition);
		mAnimationState = HeroAnimation.ANIM_UP;
		currentPointX = (int) currentGameBean.mRectF.left;
		currentPointY = (int) currentGameBean.mRectF.top;
	}

	/***
	 *
	 * 如何计算小人是往左，上，右，下四个方向走 拿到当前currentGameBean 向前: 拿到前一个frontGameBean
	 * 比较rowX，如果frontGameBean.rowX < currentGameBean.rowX
	 *
	 * @param step
	 */
	public void setForwardStep(int step) {
		this.forwardStep = step;
		if (forwardStep > 0) {
			setPersonDirectionAndSetCurrentGameBean();
		}
	}

	private void setPersonDirectionAndSetCurrentGameBean() {
		if (currentPosition + 1 >= gameBeanList.size()) {
			showToast("需要换场景了");
			return;
		}
		// 例如当前currentGameBean的rowX=5,columnY=2
		GameBean frontGameBean = gameBeanList.get(currentPosition + 1);
		logE("currentGameBean.columnY=" + currentGameBean.columnY + "--frontGameBean.columnY=" + frontGameBean.columnY + "--" + currentPosition);
		if (currentGameBean.rowX > frontGameBean.rowX) { // 换行 rowX为5，需要换成4
			if (currentGameBean.columnY == frontGameBean.columnY) {
				mAnimationState = HeroAnimation.ANIM_UP;
				setCurrentGameBean(frontGameBean);
			}
		} else if (currentGameBean.rowX == frontGameBean.rowX) { // 判断左右方向 currentGameBean.columnY=0--frontGameBean.columnY=1
			if (frontGameBean.columnY > currentGameBean.columnY) { // 向右
				mAnimationState = HeroAnimation.ANIM_RIGHT;
				setCurrentGameBean(frontGameBean);
			} else if (frontGameBean.columnY < currentGameBean.columnY) { // 向左
				mAnimationState = HeroAnimation.ANIM_LEFT;
				setCurrentGameBean(frontGameBean);
			}
		}
	}

	private void setCurrentGameBean(GameBean frontGameBean) {
		currentPosition = currentPosition + 1;
		currentGameBean = frontGameBean;
		startGo = true;
		invalidate();
	}

	private void logE(String msg) {
		Log.e("日志", msg);
	}

	private void showToast(String msg) {
		Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
	}

}

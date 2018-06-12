package com.wxj.customview.city;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.FrameLayout;

import com.wxj.customview.R;
import com.wxj.customview.utils.DevicesUtils;

/**
 * Created by 54966 on 2018/6/8.
 */

public class CommunityLayout2 extends FrameLayout {

	private static final String	TAG						= "communityLayout2";

	private List<CommunityBean>	communityBeanList;															// 建筑物集合

	private LayoutInflater		layoutInflater;																// 布局解析

	private int					halfWidth, halfHeight;														// w:540 h:1004

	private Matrix				matrix;

	private Matrix				tempMatrix;

	private float[]				matrixValues			= new float[9];

	private boolean				pointer;																	// 是否是多个手指

	private boolean				isScale;																	// 是否正在缩放

	private int					downX, downY;

	private int					lastX, lastY;

	private boolean				isFirstScale			= true;

	private Bitmap				mBuilderEmptyBitmap;

	private Bitmap				mVillageBitmap;

	private Paint				mPaint;

	private float				scaleX, scaleY;

	private HeroAnimation		mHeroAnimation;

	private List<Float>			randomSpeed				= Arrays.asList(0.45f, 0.5f, 0.55f, 0.6f, 0.65f);

	private final int			MSG_WHAT_PERSON_ANIM	= 0;

	private final int			MSG_WHAT_PERSON_SCALE	= 1;

	public CommunityLayout2(@NonNull Context context) {
		this(context, null);
	}

	public CommunityLayout2(@NonNull Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public CommunityLayout2(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		layoutInflater = LayoutInflater.from(context);
		halfWidth = DevicesUtils.getScreenWidth(context) / 2;
		halfHeight = DevicesUtils.getScreenHeight(context) / 2;

		matrix = getMatrix();
		tempMatrix = new Matrix();

		mBuilderEmptyBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_community_pic_emptyland);
		mVillageBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.img_community_pic_village);

		mPaint = new Paint();

		mHeroAnimation = new HeroAnimation();
		mHeroAnimation.initAnimation(getContext());

	}

	@Override protected void onDraw(Canvas canvas) {
		if (communityBeanList == null || communityBeanList.isEmpty()) {
			return;
		}

		float zoom = getMatrixScaleX();
		float scaleX = zoom;
		float scaleY = zoom;

		int size = communityBeanList.size();
		CommunityBean communityBean = null;
		float left = 0;
		float top = 0;

		for (int i = 0; i < size; i++) {
			communityBean = communityBeanList.get(i);

			left = (halfWidth - (mBuilderEmptyBitmap.getWidth()) / 2) + (communityBean.x) * mBuilderEmptyBitmap.getWidth() * scaleX;
			top = (halfHeight - (mBuilderEmptyBitmap.getHeight()) / 2) + (communityBean.y) * mBuilderEmptyBitmap.getHeight() * scaleY;

			communityBean.left = (int) left;
			communityBean.top = (int) top;

			tempMatrix.setTranslate(left, top);
			tempMatrix.postScale(scaleX, scaleY, left, top);

			canvas.drawBitmap(mBuilderEmptyBitmap, tempMatrix, mPaint);
		}

	}

	@Override public boolean onTouchEvent(MotionEvent event) {

		super.onTouchEvent(event);
		scaleGestureDetector.onTouchEvent(event);

		if (event.getPointerCount() > 1) {
			pointer = true;
		}
		int x = (int) event.getX();
		int y = (int) event.getY();

		int action = event.getAction();
		switch (action) {
			case MotionEvent.ACTION_DOWN:
				pointer = false;
				downX = x;
				downY = y;

				break;
			case MotionEvent.ACTION_MOVE:
				if (!isScale) {
					int dx = Math.abs(x - downX);
					int dy = Math.abs(y - downY);
					if ((dx > 10 || dy > 10) && !pointer) {
						dx = x - lastX;
						dy = y - lastY;
						// 处理边界问题

						scrollBy(-dx, -dy);
					}
				}

				break;
			case MotionEvent.ACTION_UP:

				break;
		}
		lastX = x;
		lastY = y;

		return true;
	}

	ScaleGestureDetector scaleGestureDetector = new ScaleGestureDetector(getContext(), new ScaleGestureDetector.OnScaleGestureListener() {

		@Override public boolean onScale(ScaleGestureDetector detector) { // false：不处理缩放事件
			isScale = true;
			float scaleFactor = detector.getScaleFactor();

			if (getMatrixScaleY() * scaleFactor > 3) {
				scaleFactor = 3 / getMatrixScaleY();
			}
			if (getMatrixScaleY() * scaleFactor < 0.5) {
				scaleFactor = 0.5f / getMatrixScaleY();
			}

			if (isFirstScale) {
				scaleX = detector.getCurrentSpanX();
				scaleY = detector.getCurrentSpanY();
				isFirstScale = false;
			}

			matrix.postScale(scaleFactor, scaleFactor, scaleX, scaleY);
			float zoom = getMatrixScaleX();
			if (zoom < 1) {
				zoom = 1;
			} else if (zoom > 2) {
				zoom = 2;
			} else {
				invalidate();
				mHandler.sendEmptyMessage(MSG_WHAT_PERSON_SCALE);
			}

			return true;
		}

		@Override public boolean onScaleBegin(ScaleGestureDetector detector) {
			float zoom = getMatrixScaleX();
			if (zoom >= 1 && zoom <= 2) {
				isScale = true;
				return true;
			}
			return false;
		}

		@Override public void onScaleEnd(ScaleGestureDetector detector) {
			isScale = false;
			float zoom = getMatrixScaleX();

			if (zoom < 1) {
				matrix.setScale(1, 1);
				invalidate();
			} else if (zoom > 2) {
				matrix.setScale(2, 2);
				invalidate();
			}
		}
	});

	@Override public void scrollTo(int x, int y) {
		super.scrollTo(x, y);

		int scrollX = getScrollX();
		int scrollY = getScrollY();
	}

	private int childViewHeight; // 子view的高度

	public void setChildViewHeight(int height) {
		this.childViewHeight = height;
	}

	public void setDatas(List<CommunityBean> list) {
		if (list == null || list.isEmpty()) {
			return;
		}
		communityBeanList = list;
		invalidate();

		for (int i = 0; i < communityBeanList.size(); i++) {
			addItemLayout(communityBeanList.get(i));
		}
		mHandler.sendEmptyMessageDelayed(MSG_WHAT_PERSON_ANIM, 800);
	}

	public void addCommunityBean(final CommunityBean communityBean) {
		communityBeanList.add(communityBean);
		invalidate();
		addItemLayout(communityBean);
	}

	public void addItemLayout(final CommunityBean communityBean) {
		if (communityBean == null) {
			return;
		}
		final float zoom = getMatrixScaleX();
		final View view = layoutInflater.inflate(R.layout.community_item, null);
		int left = getChildViewLeft(communityBean.x, zoom);
		final int top = getChildViewTop(communityBean.y, zoom) - view.getHeight();

		LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		params.setMargins(left, top, 0, 0);
		view.setLayoutParams(params);

		if (childViewHeight == 0) {
			view.post(new Runnable() {

				@Override public void run() {
					childViewHeight = view.getHeight();
					initViewTag(view, top, zoom);
				}
			});
		} else {
			initViewTag(view, top, zoom);
		}

		addView(view);
	}

	private void initViewTag(View view, int top, float zoom) {
		view.setTag(R.string.string_speed, 0.5f);
		view.setTag(R.string.string_init_location, top);
		view.setTag(R.string.string_direction_isup, true);
		view.setTag(R.string.string_origin_location, (int) (top + mBuilderEmptyBitmap.getHeight() * zoom - childViewHeight));
	}

	private int getChildViewLeft(int x, float zoom) {
		int left = (int) ((halfWidth - (mBuilderEmptyBitmap.getWidth()) / 2) + (x) * mBuilderEmptyBitmap.getWidth() * zoom);
		return left;
	}

	private int getChildViewTop(int y, float zoom) {
		int top = (int) ((halfHeight - (mBuilderEmptyBitmap.getHeight()) / 2) + (y) * mBuilderEmptyBitmap.getHeight() * zoom);
		return top;
	}

	private Handler mHandler = new Handler(getContext().getMainLooper()) {

		@Override public void handleMessage(Message msg) {
			int what = msg.what;
			switch (what) {
				case MSG_WHAT_PERSON_ANIM: // 处理平移动画
					setChildViewAnim();
					mHandler.sendEmptyMessageDelayed(MSG_WHAT_PERSON_ANIM, 12);

					break;
				case MSG_WHAT_PERSON_SCALE: // 处理缩放问题
					int childCount = getChildCount();
					float zoom = getMatrixScaleX();
					CommunityBean communityBean = null;
					int left = 0;
					int top = 0;

					for (int i = 0; i < childCount; i++) {
						View childView = getChildAt(i);

						LayoutParams params = (LayoutParams) childView.getLayoutParams();
						communityBean = communityBeanList.get(i);

						left = getChildViewLeft(communityBean.x, zoom);
						top = getChildViewTop(communityBean.y, zoom);

						params.setMargins(left, top, 0, 0);
						childView.setLayoutParams(params);

						childView.setTag(R.string.string_init_location, (int) (top));
						childView.setTag(R.string.string_origin_location, (int) (top + mBuilderEmptyBitmap.getHeight() * zoom - childView.getHeight()));
					}

					break;
			}
		}
	};

	private void setChildViewAnim() {
		int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			View childView = getChildAt(i);
			float speed = (float) childView.getTag(R.string.string_speed);
			boolean isUp = (boolean) childView.getTag(R.string.string_direction_isup);
			int initLocation = (int) childView.getTag(R.string.string_init_location);
			int originLocation = (int) childView.getTag(R.string.string_origin_location);

			float translationY = 0;
			if (isUp) { // 向上运动
				translationY = childView.getY() - speed;
			} else { // 向下运动
				translationY = childView.getY() + speed;
			}

			if (translationY >= originLocation) { // 向上运动
				translationY = originLocation;
				childView.setTag(R.string.string_direction_isup, true);
			} else if (translationY <= initLocation) {
				translationY = initLocation;
				childView.setTag(R.string.string_direction_isup, false);
			}

			childView.setY(translationY);
		}
	}

	private float getMatrixTranslateX() {
		matrix.getValues(matrixValues);
		return matrixValues[Matrix.MTRANS_X];
	}

	private float getMatrixTranslateY() {
		matrix.getValues(matrixValues);
		return matrixValues[Matrix.MTRANS_Y];
	}

	private float getMatrixScaleX() {
		matrix.getValues(matrixValues);
		return matrixValues[Matrix.MSCALE_X];
	}

	private float getMatrixScaleY() {
		matrix.getValues(matrixValues);
		return matrixValues[Matrix.MSCALE_Y];
	}

	public void reduction() {
		scrollTo(0, 0);
		setScaleX(1);
		setScaleY(1);
	}

	private OnCommunityClickListener mOnCommunityClickListener;

	public void setOnCommunityClickListener(OnCommunityClickListener onCommunityClickListener) {
		this.mOnCommunityClickListener = onCommunityClickListener;
	}

	public interface OnCommunityClickListener {

		void onPersonClick(int position);

		void onBuildingClick(int position);

	}

}

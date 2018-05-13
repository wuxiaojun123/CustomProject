package com.wxj.customview.zhifubao.antForest;

import android.animation.Animator;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.TimeUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wxj.customview.R;
import com.wxj.customview.utils.DensityUtil;
import com.wxj.customview.utils.LogUtils;
import com.wxj.customview.utils.ToastUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created by 54966 on 2018/5/12.
 */

public class AntForestView extends FrameLayout {

	private List<WaterBallBean>	beanList				= new ArrayList<>();

	private List<WaterBallBean>	surplusBeanList			= new ArrayList<>();																										// 如果服务器返回的不止10个，其他的就都放到这个集合中

	private List<View>			viewList				= new ArrayList<>();

	public List<WaterBallBean>	disappearWaterBallList	= new ArrayList<>();																										// 需要上传到服务器的数据

	private LayoutInflater		mInflater;																																			// 加载

	private int					width;																																				// 宽度

	private int					height;																																				// 高度

	private Random				mRandom					= new Random();																												// 随机数

	private List<Float>			xCanChooseList			= Arrays.asList(0.35f, 0.55f, 0.32f, 0.52f, 0.72f, 0.78f, 0.15f, 0.63f, 0.20f, 0.30f, 0.47f, 0.21f, 0.76f, 0.65f, 0.38f);	// 0.15f,0.15f,0.41f,

	private List<Float>			yCanChooseList			= Arrays.asList(0.36f, 0.45f, 0.50f, 0.28f, 0.33f, 0.50f, 0.40f, 0.63f, 0.62f, 0.75f, 0.57f, 0.20f, 0.70f, 0.17f, 0.21f);	// 0.77f,0.40f,0.10f,

	private List<Float>			xCurrentList			= new ArrayList<>(xCanChooseList);

	private List<Float>			yCurrentList			= new ArrayList<>(yCanChooseList);

	private final int			RANGE_OF_MOTION			= 20;																														// 初始运动范围

	private final int			MSG_WHART				= 0;																														// 发送消息的标志

	private int					childViewWidth, childViewHeight;

	private final int			DURATION_APPEARANCE		= 800;																														// 显示

	private final int			DURATION_DISAPPEARANCE	= 800;																														// 显示

	private final int			MAX_COUNT				= 10;																														// 一个页面最多能显示多少个

	private int					totalWaterBeanSize;																																	// 全部小球集合长度

	private boolean				needShowDefaultDynamic	= true;																														// 是否需要显示默认弹力球

	private boolean				isNewUserDefault;																																	// 新注册用户，默认弹力球

	private int					viewDisappearX, viewDisAppearY;																														// 小球动画消失的x,y轴

	private List<Float>			randomSpeed				= Arrays.asList(0.45f, 0.5f, 0.55f, 0.6f, 0.65f);

	private int					everyTimeMaxListSize	= 10;																														// 每次最大显示多少

	private float				lastViewLocationX, lastViewLocationY;

	private Handler				handler					= new Handler() {

															@Override public void handleMessage(Message msg) {
																setAllViewTranslation();
																handler.sendEmptyMessageDelayed(MSG_WHART, 12);
															}
														};

	/***
	 * 以Y轴为例，设置view的初始Y坐标为20,方向是y轴，当前运动到了30,辣么 view.setTranslationY();
	 */
	private void setAllViewTranslation() {
		int size = viewList.size();
		for (int i = 0; i < size; i++) {
			View view = viewList.get(i);
			// 拿到上次view保存的速度
			float spd = (float) view.getTag(R.string.string_origin_spe);
			// 水滴初始的位置
			float original = (float) view.getTag(R.string.string_origin_location);
			float step = spd;
			boolean isUp = (boolean) view.getTag(R.string.string_origin_direction);
			float translationY;
			// 根据水滴tag中的上下移动标识移动view
			if (isUp) {
				translationY = view.getY() - step;
			} else {
				translationY = view.getY() + step;
			}
			// 对水滴位移范围的控制
			if (translationY - original > RANGE_OF_MOTION) {
				translationY = original + RANGE_OF_MOTION;
				view.setTag(R.string.string_origin_direction, true);
			} else if (translationY - original < -RANGE_OF_MOTION) {
				translationY = original - RANGE_OF_MOTION;
				// FIXME:每次当水滴回到初始点时再一次设置水滴的速度，从而达到时而快时而慢
				// view.setTag(R.string.string_origin_spe, spd);
				view.setTag(R.string.string_origin_spe, randomSpeed.get(mRandom.nextInt(randomSpeed.size())));
				view.setTag(R.string.string_origin_direction, false);
			}
			view.setY(translationY);
		}
	}

	public AntForestView(@NonNull Context context) {
		this(context, null);
	}

	public AntForestView(@NonNull Context context, @Nullable AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public AntForestView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context);
	}

	private void init(Context context) {
		mInflater = LayoutInflater.from(context);
	}


	public void setData(List<WaterBallBean> list) {
		if (list == null) {
			return;
		}
		beanList.clear();
		viewList.clear();
		totalWaterBeanSize = list.size();
		cuttingList(list);

		postRunnable();
	}

	private void postRunnable() {
		post(new Runnable() {

			@Override public void run() {
				for (int i = 0; i < beanList.size(); i++) {
					WaterBallBean bean = beanList.get(i);
					addViewList(bean, i);
				}
				handler.sendEmptyMessageDelayed(MSG_WHART, DURATION_APPEARANCE);
			}
		});
	}


	private void cuttingList(List<WaterBallBean> list) {
		if (list.size() > everyTimeMaxListSize) {
			beanList = list.subList(0, everyTimeMaxListSize);
			for (int i = everyTimeMaxListSize; i < list.size(); i++) {
				surplusBeanList.add(list.get(i));
			}
		} else {
			beanList = list;
		}
	}


	public void addViewList(WaterBallBean bean, int i) {
		View view = mInflater.inflate(R.layout.layout_water_ball, this, false);
		findView(bean, view);

		if (bean.isDefault) {
			setDefaultViewLocation(view);
		} else {
			setOnClickBallView(view, bean, i);
			if (i == -1) {
				setViewLocation(view);
			} else {
				setViewLocation(view, i);
			}
		}
		addView(view);
		setViewAnimation(view);
		viewList.add(view);
	}

	private void findView(WaterBallBean bean, View view) {
		TextView id_tv_waterball = view.findViewById(R.id.id_tv_waterball);
		ImageView id_iv_waterball = view.findViewById(R.id.id_iv_waterball);
		id_tv_waterball.setText(bean.number);
		id_iv_waterball.setImageResource(R.mipmap.img_round_ball);
		if (bean.isDefault) id_tv_waterball.setText(bean.name);
	}

	private void setOnClickBallView(final View view, final WaterBallBean bean, final int position) {
		view.setOnClickListener(new OnClickListener() {

			@Override public void onClick(View v) {
				if (!disappearWaterBallList.contains(bean)) {
					disappearWaterBallList.add(bean);
				}
				viewList.remove(view);
				beanList.remove(bean);

				startClickBallDisappearAnim(view, bean, position);
			}
		});
	}

	private void startClickBallDisappearAnim(final View view, final WaterBallBean bean, final int position) {
		lastViewLocationX = view.getX();
		lastViewLocationY = view.getY();

		view.animate().translationY(viewDisAppearY).translationX(viewDisappearX).alpha(0).setListener(new Animator.AnimatorListener() {

			@Override public void onAnimationStart(Animator animation) {}

			@Override public void onAnimationEnd(Animator animation) {
				// 弹力值全部收取完成的时候，才出现默认的小球，不可重复出现默认小球
				onBallDisappearAnimEnd(view, bean);
			}

			@Override public void onAnimationCancel(Animator animation) {}

			@Override public void onAnimationRepeat(Animator animation) {}
		}).setDuration(DURATION_DISAPPEARANCE).start();
	}

	private void onBallDisappearAnimEnd(View view, WaterBallBean bean) {
		removeView(view);
		if (onStopAnimateListener != null) {
			onStopAnimateListener.onBallDisappearAnimListener(bean.number);
		}

		if (!surplusBeanList.isEmpty()) {
			addViewList(surplusBeanList.get(0), -1);
			surplusBeanList.remove(0);
		} else {
			if (needShowDefaultDynamic && totalWaterBeanSize == disappearWaterBallList.size()) {
				needShowDefaultDynamic = false;
				addViewList(getDefaultWaterBallBean(), -1);
			}
		}
	}

	private void setViewAnimation(final View view) {
		view.setAlpha(0);
		view.setScaleX(0);
		view.setScaleY(0);
		view.animate().alpha(1).scaleX(1).scaleY(1).setDuration(DURATION_APPEARANCE).start();
	}

	private void setViewLocation(View view, int i) {
		int randomInt = 0;
		if (i >= 6) {
			randomInt = mRandom.nextInt(xCurrentList.size());
		}

		float x = xCurrentList.get(randomInt) * width;
		view.setX(x);

		float y = yCurrentList.get(randomInt) * height;
		view.setY(y);

		// LogUtils.e("设置的x位置是" + x + "y轴位置是" + y);
		LogUtils.e("randomInt=" + randomInt + "设置的x位置是" + xCurrentList.get(randomInt) + "y轴位置是" + yCurrentList.get(randomInt));

		view.setTag(R.string.string_origin_location, (float) y);
		view.setTag(R.string.string_origin_direction, mRandom.nextBoolean());
		view.setTag(R.string.string_origin_spe, randomSpeed.get(mRandom.nextInt(randomSpeed.size())));

		xCurrentList.remove(randomInt);
		yCurrentList.remove(randomInt);
	}

	private void setViewLocation(View view) {
		view.setX(lastViewLocationX);
		view.setY(lastViewLocationY);

		view.setTag(R.string.string_origin_location, lastViewLocationY);
		view.setTag(R.string.string_origin_direction, mRandom.nextBoolean());
		view.setTag(R.string.string_origin_spe, randomSpeed.get(mRandom.nextInt(randomSpeed.size())));
	}

	/***
	 * 设置默认的小球位置
	 *
	 * @param view
	 */
	private void setDefaultViewLocation(View view) {
		view.setX((width - childViewWidth) / 2);
		view.setY(height / 2);

		view.setTag(R.string.string_origin_location, (float) height / 2);
		view.setTag(R.string.string_origin_direction, mRandom.nextBoolean());
		view.setTag(R.string.string_origin_spe, 0.5f);
	}

	@Override protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		stopAnimate();
	}

	/**
	 * 获取默认的弹力值
	 *
	 * @return
	 */
	public WaterBallBean getDefaultWaterBallBean() {
		return new WaterBallBean("", getResources().getString(R.string.string_production_of_money), "", true);
	}

	public void stopAnimate() {
		if (viewList == null || viewList.isEmpty()) {
			destroy();
			return;
		}

		for (int i = 0; i < viewList.size(); i++) {
			startDisappearAnim(viewList.get(i), i);
		}
	}

	private void startDisappearAnim(View childView, final int i) {
		ViewPropertyAnimator viewPropertyAnimator = childView.animate().alpha(0).scaleX(0).scaleY(0).setDuration(DURATION_APPEARANCE);
		if (i + 1 == viewList.size()) {
			viewPropertyAnimator.setListener(new Animator.AnimatorListener() {

				@Override public void onAnimationStart(Animator animation) {}

				@Override public void onAnimationEnd(Animator animation) {
					destroy();
				}

				@Override public void onAnimationCancel(Animator animation) {}

				@Override public void onAnimationRepeat(Animator animation) {}
			});
		}
		viewPropertyAnimator.start();
	}

	@Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		width = w - getPaddingLeft() - getPaddingRight();
		height = h - getPaddingTop() - getPaddingBottom();

		childViewWidth = DensityUtil.dip2px(getContext(), 40);
		childViewHeight = DensityUtil.dip2px(getContext(), 40);
	}

	public void setViewDisappearLocation(int[] location) {
		viewDisappearX = location[0];
		viewDisAppearY = location[1];
	}

	public void destroy() {
		handler.removeMessages(MSG_WHART);
		if (onStopAnimateListener != null) {
			onStopAnimateListener.onExitAnimateListener();
		}
		removeAllViews();
	}

	private OnStopAnimateListener onStopAnimateListener;

	public void setOnStopAnimateListener(OnStopAnimateListener listener) {
		this.onStopAnimateListener = listener;
	}

	public interface OnStopAnimateListener {

		void onBallDisappearAnimListener(String number);

		void onExitAnimateListener();
	}
}

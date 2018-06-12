package com.wxj.customview.tanqian.game;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.animation.LinearOutSlowInInterpolator;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.wxj.customview.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 54966 on 2018/5/14.
 */

public class GameActivity extends Activity {

	@BindView(R.id.id_game_view) GameView			idGameView;

	@BindView(R.id.id_btn_start) Button				idBtnStart;

	@BindView(R.id.id_iv_user) ImageView			idIvUser;

	@BindView(R.id.id_iv_dice) ImageView			idIvDice;

	@BindView(R.id.id_rl_content) RelativeLayout	idRlContent;

	@BindView(R.id.id_fl_content) FrameLayout		idFlContent;

	AnimationDrawable								animationDrawable;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		ButterKnife.bind(this);

		initView();
	}

	private void initView() {
		animationDrawable = (AnimationDrawable) getResources().getDrawable(R.drawable.anim_dice);
		idIvDice.setBackgroundDrawable(animationDrawable);
	}

	private final int	DURATION	= 2000;

	// 获取当前线段的位置 pos[0]为x轴 pos[1]为y轴
	private float[]		pos			= new float[2];

	@OnClick({ R.id.id_btn_start }) public void click(View view) {
		int id = view.getId();
		switch (id) {
			case R.id.id_btn_start: // 向前或向后走几步
				Path mPath = new Path();
				mPath.moveTo(0, 0);
//				mPath.quadTo(100, 0, 300, 300);
				mPath.cubicTo(0, 0, 600, 300,0,300);

				final PathMeasure pathMeasure = new PathMeasure(mPath, false);
				ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, pathMeasure.getLength());
				valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

					@Override public void onAnimationUpdate(ValueAnimator animation) {
						float animatedValue = (float) animation.getAnimatedValue();
						pathMeasure.getPosTan(animatedValue, pos, null);
						idIvUser.setTranslationX(pos[0]);
						idIvUser.setTranslationY(pos[1]);
					}
				});
//				valueAnimator.setDuration(600);
//				valueAnimator.start();

				// ObjectAnimator translationXAnim =
				// ObjectAnimator.ofFloat(idIvUser,"translationX",0f,200f);
				// ObjectAnimator translationYAnim =
				// ObjectAnimator.ofFloat(idIvUser,"translationY",0f,200f,0f);
				ObjectAnimator rotationAnim = ObjectAnimator.ofFloat(idIvUser, "rotation", 0f, 360f);
				AnimatorSet animatorSet = new AnimatorSet();
				animatorSet.setInterpolator(new LinearOutSlowInInterpolator());
				animatorSet.setDuration(DURATION);
				animatorSet.playTogether(valueAnimator, rotationAnim);
				animatorSet.start();

				// idIvDice.setImageDrawable(null);
				// if (animationDrawable.isRunning()) {
				// animationDrawable.stop();
				// } else {
				// animationDrawable.start();
				// mHandler.sendEmptyMessageDelayed(1, 1000);
				// }
				break;
		}
	}

	private Handler mHandler = new Handler() {

		@Override public void handleMessage(Message msg) {
			if (animationDrawable.isRunning()) {
				animationDrawable.stop();
			}
			Random random = new Random();
			int step = random.nextInt(2) + 1;
			idBtnStart.setText("向前" + step);
			switch (step) {
				case 1:
					idIvDice.setImageDrawable(animationDrawable.getFrame(4));
					break;
				case 2:
					idIvDice.setImageDrawable(animationDrawable.getFrame(1));
					break;
				case 3:
					idIvDice.setImageDrawable(animationDrawable.getFrame(5));
					break;
				case 4:
					idIvDice.setImageDrawable(animationDrawable.getFrame(0));
					break;
				case 5:
					idIvDice.setImageDrawable(animationDrawable.getFrame(2));
					break;
				case 6:
					idIvDice.setImageDrawable(animationDrawable.getFrame(3));
					break;
			}
			idGameView.setForwardStep(step);
		}
	};

}

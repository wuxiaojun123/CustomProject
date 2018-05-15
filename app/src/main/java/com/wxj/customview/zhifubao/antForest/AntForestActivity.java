package com.wxj.customview.zhifubao.antForest;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.wxj.customview.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 54966 on 2018/5/12.
 */

public class AntForestActivity extends Activity implements AntForestView.OnStopAnimateListener {

	@BindView(R.id.id_iv_close) TextView				idTvClose;

	@BindView(R.id.id_waterball) AntForestView			idWaterball;

	@BindView(R.id.id_tv_my_assets) TextView			idTvAssets;			// 资产

	@BindView(R.id.id_ll_add_assets_anim) FrameLayout	idLlAddAssetsAnim;	// 资产layout

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ant_forest);
		ButterKnife.bind(this);

		final List<WaterBallBean> waterBallBeanList = new ArrayList<>();
		WaterBallBean waterBallBean = null;
		int size = 20;
		for (int i = 0; i < size; i++) {
			waterBallBean = new WaterBallBean("" + i, "哈哈", i + "", false);
			waterBallBeanList.add(waterBallBean);
		}
		idWaterball.setData(waterBallBeanList);
		idWaterball.setOnStopAnimateListener(this);
	}

	@OnClick({ R.id.id_iv_close, R.id.id_tv_my_assets }) public void click(View view) {
		int id = view.getId();
		switch (id) {
			case R.id.id_iv_close:
				idWaterball.stopAnimate();

				break;
			case R.id.id_tv_my_assets:

				break;
		}
	}

	@Override public void onBallDisappearAnimListener(String number) {
		startAssetTextAnimStep1(number);
	}

	@Override public void onExitAnimateListener() {
		finish();
	}

	private void startAssetTextAnimStep1(String number) {
		final TextView textView = new TextView(this);
		textView.setText("+" + number);
		textView.setTextColor(getResources().getColor(R.color.color_d1d4fe));
		textView.setTextSize(16);
		textView.setGravity(Gravity.CENTER);

		idLlAddAssetsAnim.addView(textView);
		startAssetTextAnimStep2(textView);
	}

	private void startAssetTextAnimStep2(final TextView textView) {
		textView.animate().translationY(-100).alpha(0).setDuration(700).setListener(new Animator.AnimatorListener() {

			@Override public void onAnimationStart(Animator animation) {}

			@Override public void onAnimationEnd(Animator animation) {

				runOnUiThread(new Runnable() {

					@Override public void run() {
						idLlAddAssetsAnim.removeView(textView);
					}
				});
			}

			@Override public void onAnimationCancel(Animator animation) {}

			@Override public void onAnimationRepeat(Animator animation) {}
		}).setInterpolator(new DecelerateInterpolator()).start();
	}

	@Override public void onBackPressed() {
		idTvClose.performClick();
	}

}

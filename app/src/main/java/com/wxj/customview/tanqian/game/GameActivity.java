package com.wxj.customview.tanqian.game;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wxj.customview.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by 54966 on 2018/5/14.
 */

public class GameActivity extends Activity {

	@BindView(R.id.id_game_view) GameView	idGameView;

	public List<GameBean>					gameBeanList;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		ButterKnife.bind(this);

		initView();
	}

	private void initView() {
		gameBeanList = new ArrayList<>();
		for (int i = 0; i < 25; i++) {
			gameBeanList.add(new GameBean());
		}
	}

	@OnClick({ R.id.id_btn_start }) public void click(View view) {
		int id = view.getId();
		switch (id) {
			case R.id.id_btn_start: // 向前或向后走几步
				idGameView.setForwardStep(1);

				break;
		}
	}

}

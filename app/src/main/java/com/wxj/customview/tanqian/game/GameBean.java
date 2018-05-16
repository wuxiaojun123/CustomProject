package com.wxj.customview.tanqian.game;

import android.graphics.RectF;

/**
 * Created by 54966 on 2018/5/14.
 */

public class GameBean {

	public RectF	mRectF;

	public boolean	hide;

	public int		rowX;		// 横

	public int		columnY;	// 列

	public GameBean(RectF rectF, boolean hide, int rowX, int columnY) {
		this.mRectF = rectF;
		this.hide = hide;
		this.rowX = rowX;
		this.columnY = columnY;
	}

}

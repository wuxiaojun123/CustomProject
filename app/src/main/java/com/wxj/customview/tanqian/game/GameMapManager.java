package com.wxj.customview.tanqian.game;

import android.graphics.RectF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 54966 on 2018/5/16.
 */

public class GameMapManager {

	public List<GameBean>	gameBeanList;

	private RectF[][]		mRectFArray;	// 数组

	public List<GameBean> getMapFirst(int heightCount, int widthCount, int singleRectSize) { // 获取第一个场景

		initRectF(heightCount, widthCount, singleRectSize);
		return gameBeanList;
	}

	/***
	 * 一个二维数组
	 * 
	 * @param heightCount
	 * @param widthCount
	 * @param singleRectSize
	 */
	private void initRectF(int heightCount, int widthCount, int singleRectSize) {
		gameBeanList = new ArrayList<>();

		mRectFArray = new RectF[heightCount][widthCount];
		RectF rectF = null;
		for (int i = mRectFArray.length - 1; i >= 0; i--) { // 行
			if (i == 5) {

				for (int j = 0; j < mRectFArray[i].length; j++) { // 列
					rectF = new RectF(j * singleRectSize, i * singleRectSize, (j + 1) * singleRectSize, (i + 1) * singleRectSize);
					mRectFArray[i][j] = rectF;
					if (j == 2) {
						gameBeanList.add(new GameBean(rectF, true, i, j));
					}
				}

			} else if (i == 4) {

				for (int j = 0; j < mRectFArray[i].length; j++) { // 列
					rectF = new RectF(j * singleRectSize, i * singleRectSize, (j + 1) * singleRectSize, (i + 1) * singleRectSize);
					mRectFArray[i][j] = rectF;
					if (j <= 2) {
						gameBeanList.add(new GameBean(rectF, true, i, j));
					}
				}

			} else if (i == 3) {

				for (int j = 0; j < mRectFArray[i].length; j++) { // 列
					rectF = new RectF(j * singleRectSize, i * singleRectSize, (j + 1) * singleRectSize, (i + 1) * singleRectSize);
					mRectFArray[i][j] = rectF;
					if (j == 0) {
						gameBeanList.add(new GameBean(rectF, true, i, j));
					}
				}

			} else if (i == 2) {

				for (int j = 0; j < mRectFArray[i].length; j++) { // 列
					rectF = new RectF(j * singleRectSize, i * singleRectSize, (j + 1) * singleRectSize, (i + 1) * singleRectSize);
					mRectFArray[i][j] = rectF;
					if (j == 0) {
						gameBeanList.add(new GameBean(rectF, true, i, j));
					}
				}

			} else if (i == 1) {

				for (int j = 0; j < mRectFArray[i].length; j++) { // 列
					rectF = new RectF(j * singleRectSize, i * singleRectSize, (j + 1) * singleRectSize, (i + 1) * singleRectSize);
					mRectFArray[i][j] = rectF;
					if (j <= mRectFArray[i].length - 2) {
						gameBeanList.add(new GameBean(rectF, true, i, j));
					}
				}

			} else if (i == 0) {

				for (int j = 0; j < mRectFArray[i].length; j++) { // 列
					rectF = new RectF(j * singleRectSize, i * singleRectSize, (j + 1) * singleRectSize, (i + 1) * singleRectSize);
					mRectFArray[i][j] = rectF;
					if (j == mRectFArray[i].length - 2) {
						gameBeanList.add(new GameBean(rectF, true, i, j));
					}
				}

			}

		}
	}

}

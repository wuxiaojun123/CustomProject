package com.wxj.customview.tanqian.game;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wxj.customview.R;

/**
 * 任务移动 Created by 54966 on 2018/5/16.
 */

public class HeroAnimation {

    /**
     * 向下移动动画
     **/
    public final static int ANIM_DOWN = 0;
    /**
     * 向左移动动画
     **/
    public final static int ANIM_LEFT = 1;
    /**
     * 向右移动动画
     **/
    public final static int ANIM_RIGHT = 2;
    /**
     * 向上移动动画
     **/
    public final static int ANIM_UP = 3;
    /**
     * 动画的总数量
     **/
    public final static int ANIM_COUNT = 4;

    GameAnimation mHeroAnim[] = new GameAnimation[ANIM_COUNT];

    public void initAnimation(Context context) {
        // 这里可以用循环来处理总之我们需要把动画的ID传进去
        mHeroAnim[ANIM_DOWN] = new GameAnimation(context, new int[]{
                R.mipmap.hero_down_a, R.mipmap.hero_down_b,
                R.mipmap.hero_down_c, R.mipmap.hero_down_d}, true);
        mHeroAnim[ANIM_LEFT] = new GameAnimation(context, new int[]{
                R.mipmap.hero_left_a, R.mipmap.hero_left_b,
                R.mipmap.hero_left_c, R.mipmap.hero_left_d}, true);
        mHeroAnim[ANIM_RIGHT] = new GameAnimation(context, new int[]{
                R.mipmap.hero_right_a, R.mipmap.hero_right_b,
                R.mipmap.hero_right_c, R.mipmap.hero_right_d}, true);
        mHeroAnim[ANIM_UP] = new GameAnimation(context, new int[]{
                R.mipmap.hero_up_a, R.mipmap.hero_up_b,
                R.mipmap.hero_up_c, R.mipmap.hero_up_d}, true);

//        mHeroAnim[ANIM_DOWN] = new GameAnimation(context, new int[]{
//                R.mipmap.img_pic_user}, true);
//        mHeroAnim[ANIM_LEFT] = new GameAnimation(context, new int[]{
//                R.mipmap.img_pic_user}, true);
//        mHeroAnim[ANIM_RIGHT] = new GameAnimation(context, new int[]{
//                R.mipmap.img_pic_user}, true);
//        mHeroAnim[ANIM_UP] = new GameAnimation(context, new int[]{
//                R.mipmap.img_pic_user}, true);
    }

}

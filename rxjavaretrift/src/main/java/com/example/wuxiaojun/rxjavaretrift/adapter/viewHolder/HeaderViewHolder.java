package com.example.wuxiaojun.rxjavaretrift.adapter.viewHolder;

import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.wuxiaojun.rxjavaretrift.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 创建头部viewHolder
 * Created by wuxiaojun on 16-11-29.
 */

public class HeaderViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.id_viewpager)
    public ViewPager id_viewpager;
    @BindView(R.id.id_ll_dot)
    public LinearLayout id_ll_dot;
    @BindView(R.id.id_iv_one)
    public ImageView id_iv_one;

    public HeaderViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}

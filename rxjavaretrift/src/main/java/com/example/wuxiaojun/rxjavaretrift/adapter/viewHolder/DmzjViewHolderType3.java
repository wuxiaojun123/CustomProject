package com.example.wuxiaojun.rxjavaretrift.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wuxiaojun.rxjavaretrift.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wuxiaojun on 16-11-29.
 */

public class DmzjViewHolderType3 extends RecyclerView.ViewHolder {

    @BindView(R.id.id_iv_img_left)
    public ImageView id_iv_img_left;
    @BindView(R.id.id_iv_img_center)
    public ImageView id_iv_img_center;
    @BindView(R.id.id_iv_img_right)
    public ImageView id_iv_img_right;
    @BindView(R.id.id_tv_title)
    public TextView id_tv_title;
    @BindView(R.id.id_tv_tag)
    public TextView id_tv_tag;
    @BindView(R.id.id_ll_item)
    public LinearLayout id_ll_item;

    public DmzjViewHolderType3(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
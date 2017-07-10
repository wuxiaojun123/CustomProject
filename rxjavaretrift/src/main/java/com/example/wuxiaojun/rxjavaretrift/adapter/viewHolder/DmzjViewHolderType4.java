package com.example.wuxiaojun.rxjavaretrift.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wuxiaojun.rxjavaretrift.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wuxiaojun on 16-11-29.
 */

public class DmzjViewHolderType4 extends RecyclerView.ViewHolder {
    @BindView(R.id.id_tv_title)
    public TextView id_tv_title;
    @BindView(R.id.id_tv_content)
    public TextView id_tv_tag;
    @BindView(R.id.id_tv_description)
    public TextView id_tv_description;
    @BindView(R.id.id_rl_item)
    public RelativeLayout id_rl_item;

    public DmzjViewHolderType4(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
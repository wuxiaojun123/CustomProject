package com.example.wuxiaojun.rxjavaretrift.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wuxiaojun.rxjavaretrift.R;
import com.example.wuxiaojun.rxjavaretrift.view.DilatingDotsProgressBar;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wuxiaojun on 16-11-29.
 */

public class FooterViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.id_ll_footer)
    public LinearLayout id_ll_footer;
    @BindView(R.id.id_tv_tips)
    public TextView id_tv_tips;
    @BindView(R.id.rcv_load_more)
    public DilatingDotsProgressBar progressBar;

    public FooterViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}
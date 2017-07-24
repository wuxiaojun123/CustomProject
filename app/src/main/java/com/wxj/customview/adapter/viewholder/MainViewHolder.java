package com.wxj.customview.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.wxj.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wuxiaojun on 17-7-24.
 */

public class MainViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_content)
    public TextView tv_content;

    public MainViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
        
    }


}

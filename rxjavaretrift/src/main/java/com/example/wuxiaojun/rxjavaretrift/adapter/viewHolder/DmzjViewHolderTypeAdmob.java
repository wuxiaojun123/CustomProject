package com.example.wuxiaojun.rxjavaretrift.adapter.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wuxiaojun.rxjavaretrift.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wuxiaojun on 16-11-29.
 */

public class DmzjViewHolderTypeAdmob extends RecyclerView.ViewHolder {

    /*@BindView(R.id.native_ad_unit)
    public LinearLayout native_ad_unit;
    @BindView(R.id.native_ad_title)
    public TextView nativeAdTitle;
    @BindView(R.id.native_ad_media)
    public MediaView nativeAdMedia;
    @BindView(R.id.native_ad_body)
    public TextView nativeAdBody;
    @BindView(R.id.native_ad_call_to_action)
    public Button nativeAdCallToAction;
    @BindView(R.id.ad_choices_container)
    public LinearLayout adChoicesContainer;*/


    public DmzjViewHolderTypeAdmob(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

}

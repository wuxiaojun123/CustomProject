package com.wxj.customview.recyclerview.snaphelper;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import com.wxj.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wuxiaojun on 2017/12/13.
 */

public class SnapHelperRecyclerViewActivity extends Activity {

    @BindView(R.id.id_recycler) RecyclerView recyclerView;


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snaphelper_recyclerview);
        ButterKnife.bind(this);

    }


}

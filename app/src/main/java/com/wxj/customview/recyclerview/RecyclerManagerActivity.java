package com.wxj.customview.recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wxj.customview.R;
import com.wxj.customview.recyclerview.snaphelper.SnapHelperRecyclerViewActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wuxiaojun on 2017/12/13.
 */

public class RecyclerManagerActivity extends Activity {



    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_manager);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.id_tv_snaphelper})
    public void onClick(View v){
        int id = v.getId();
        switch (id){
            case R.id.id_tv_snaphelper:
                startActivity(new Intent(RecyclerManagerActivity.this,SnapHelperRecyclerViewActivity.class));
                break;

        }
    }

}

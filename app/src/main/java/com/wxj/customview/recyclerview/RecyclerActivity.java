package com.wxj.customview.recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.AbsListView;

import com.wxj.customview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiaojun on 16-9-22.
 */
public class RecyclerActivity extends Activity {

    private RecyclerView mRecyclerView;
    private RecyclerAdapter mAdapter;
    private List<String> mList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler);
        mRecyclerView = (RecyclerView) findViewById(R.id.id_recycler);
        mList = new ArrayList<>();
        int size = 20;
        for (int i = 0; i < size; i++) {
            mList.add("第"+(i+1)+"个条目");
        }
        mAdapter = new RecyclerAdapter(this,mList);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter);
    }


}

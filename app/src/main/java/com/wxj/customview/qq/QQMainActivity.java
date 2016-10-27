package com.wxj.customview.qq;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AbsListView;
import android.widget.FrameLayout;

import com.wxj.customview.R;
import com.wxj.customview.utils.DensityUtil;
import com.wxj.customview.utils.DevicesUtils;
import com.wxj.customview.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxiaojun on 16-8-17.
 */
public class QQMainActivity extends Activity {

    private Context mContext;
    private ImitateQQListView listview;
    private List<String> mList;
    private FrameLayout id_fl_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq);
        listview = (ImitateQQListView) findViewById(R.id.listview);
        id_fl_title = (FrameLayout) findViewById(R.id.id_fl_title);
        listview.mHeaderTitleView = id_fl_title;
        mContext = getApplicationContext();
        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            mList.add("哈哈哈,条目" + i);
        }
        //添加头部布局
        LayoutInflater inflater = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.layout_listview_head_imageview, null);
        //测量view的高度
        measureHeadViewHeight(view);
        listview.addHeaderView(view, null, false);
        listview.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mList));
    }

    /***
     * 测量view的高度
     *
     * @param view
     */
    private void measureHeadViewHeight(View view) {
        AbsListView.LayoutParams params = (AbsListView.LayoutParams) view.getLayoutParams();
        if (params == null) {
            params = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        }
        int width = params.width;
        int widthSpec = 0;
        if (width <= 0) {
            widthSpec = View.MeasureSpec.makeMeasureSpec(DevicesUtils.getScreenWidth(mContext), View.MeasureSpec.EXACTLY);
        }
        //获取高度
        int height = params.height;
        int heightMeasureSpec = 0;
        if (height <= 0) {
            heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(DensityUtil.dip2px(mContext, 220), View.MeasureSpec.EXACTLY);
        }
        view.measure(widthSpec, heightMeasureSpec);
        height = view.getMeasuredHeight();
        params.height = height;
        view.setLayoutParams(params);
    }

}

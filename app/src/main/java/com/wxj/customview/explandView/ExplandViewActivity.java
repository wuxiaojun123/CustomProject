package com.wxj.customview.explandView;

import android.app.Activity;
import android.os.Bundle;

import com.wxj.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wuxiaojun on 16-6-29.
 */
public class ExplandViewActivity extends Activity {

    @BindView(R.id.id_myexpland_view)
    MyExplandView view;
    @BindView(R.id.id_myexpland_view2)
    MyExplandView view2;
    @BindView(R.id.id_myexpland_view3)
    MyExplandView view3;
    @BindView(R.id.id_myexpland_view4)
    MyExplandView view4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expland);
        ButterKnife.bind(this);

        view.setContent(R.layout.layout_expland_item);

        view2.setContent(R.layout.layout_expland_item);

        view3.setContent(R.layout.layout_expland_item);

        view4.setContent(R.layout.layout_expland_item);
    }


}

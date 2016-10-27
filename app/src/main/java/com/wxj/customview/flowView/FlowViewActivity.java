package com.wxj.customview.flowView;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.wxj.customview.R;
import com.wxj.customview.utils.LogUtils;
import com.wxj.customview.utils.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wuxiaojun on 16-7-28.
 */
public class FlowViewActivity extends Activity implements View.OnClickListener {

    @BindView(R.id.btn_add_flow)
    Button btn_add_flow;
    @BindView(R.id.id_flowview1)
    ZHYFlowViewGroup2 id_flowview1;

    private String mNames[] = {
            "welcome", "android", "TextView",
            "apple", "jamy", "kobe bryant",
            "jordan", "layout", "viewgroup",
            "margin", "padding", "text",
            "name", "type", "search", "logcat"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_view);
        ButterKnife.bind(this);
        btn_add_flow.setOnClickListener(this);
        //id_flowview1.setmHorizontalSpacing(10);
        //id_flowview1.setmVerticalSpacing(10);
        addView();
    }

    private void addView() {
        for (int i = 0; i < mNames.length; i++) {
            TextView view = new TextView(this);
            view.setText(mNames[i]);
            view.setTextColor(Color.WHITE);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
            ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                    ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                    ViewGroup.MarginLayoutParams.WRAP_CONTENT);
            lp.leftMargin = 10;
            lp.topMargin = 10;
            id_flowview1.addView(view, lp);
        }

        int size = id_flowview1.getChildCount();
        for (int s = 0; s < size; s++) {
            final int f = s;
            View myView = id_flowview1.getChildAt(f);
            myView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showToast(FlowViewActivity.this, "点击了" + mNames[f]);
                }
            });
        }
    }

    @OnClick({R.id.btn_add_flow})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_add_flow:
                addOneView();
                break;
        }
    }


    private void addOneView() {
        TextView view = new TextView(this);
        view.setText(mNames[(int) (Math.random() * 16)]);
        view.setTextColor(Color.RED);
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.MarginLayoutParams.WRAP_CONTENT,
                ViewGroup.MarginLayoutParams.WRAP_CONTENT);
        lp.leftMargin = 10;
        lp.topMargin = 10;
        view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
        id_flowview1.addView(view, lp);
    }


}

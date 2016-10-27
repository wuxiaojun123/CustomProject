package com.wxj.customview.zhifubao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wxj.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wuxiaojun on 16-7-18.
 */
public class ZhiFuBaoAnimActivity extends Activity implements View.OnClickListener{

    @BindView(R.id.btn_start)
    Button btn_start;

    @BindView(R.id.zfb_view)
    ZhiFuBaoAnimView view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhifubao_anim);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_start})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_start:
                //开始动画效果
                view.startAnima();

                break;
        }
    }


}

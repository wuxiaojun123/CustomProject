package com.wxj.customview.pathmeasure;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wxj.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wuxiaojun on 16-9-5.
 */
public class PathMeasureActivity extends Activity implements View.OnClickListener{

    @BindView(R.id.btn_start)
    Button btn_start;
    @BindView(R.id.id_path_measure)
    PathMeasureTest id_path_measure;

    @BindView(R.id.id_path_segment)
    PathMeasureSegmentTest id_path_segment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path_measure);
        ButterKnife.bind(this);


    }

    @OnClick({R.id.btn_start})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btn_start:
                id_path_measure.startAnima();
                id_path_segment.startAnim();
                break;
        }
    }


}

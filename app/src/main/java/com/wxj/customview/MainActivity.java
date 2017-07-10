package com.wxj.customview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wxj.customview.GuaGuaKaActivity.GuaGuaKaActivity;
import com.wxj.customview.aidl.AIDLActivity;
import com.wxj.customview.aidl.Book;
import com.wxj.customview.arrow.ArrowActivity;
import com.wxj.customview.circle.scale.CircleScaleActivity;
import com.wxj.customview.explandView.ExplandViewActivity;
import com.wxj.customview.flowView.FlowViewActivity;
import com.wxj.customview.matrix.MatrixActivity;
import com.wxj.customview.pathmeasure.PathMeasureActivity;
import com.wxj.customview.qq.QQMainActivity;
import com.wxj.customview.recyclerview.RecyclerActivity;
import com.wxj.customview.sqlite.SqliteActivity;
import com.wxj.customview.surfaceview.SurfaceViewActivity;
import com.wxj.customview.viewdraghelper.ViewDragHelperActivity;
import com.wxj.customview.viewdraghelpertest.VerticalDragActivity;
import com.wxj.customview.viewgroupEvent.ViewGroupActivity;
import com.wxj.customview.waterfallFlow.WaterFallFlowActivity;
import com.wxj.customview.webview.WebViewActivity;
import com.wxj.customview.zhifubao.ZhiFuBaoAnimActivity;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.btn_guaguaka)
    Button btn_guaguaka;
    @BindView(R.id.btn_arrow)
    Button btn_arrow;
    @BindView(R.id.btn_viewgroup_event)
    Button btn_viewgroup_event;
    @BindView(R.id.btn_expland_view)
    Button btn_expland_view;
    @BindView(R.id.btn_zhifubao_view)
    Button btn_zhifubao_view;
    @BindView(R.id.btn_waterfall)
    Button btn_waterfall;
    @BindView(R.id.btn_flow_view)
    Button btn_flow_view;
    @BindView(R.id.btn_locky_pan)
    Button btn_locky_pan;
    @BindView(R.id.btn_qq)
    Button btn_qq;
    @BindView(R.id.btn_circle_scale)
    Button btn_circle_scale;
    @BindView(R.id.btn_path_measure)
    Button btn_path_measure;
    @BindView(R.id.btn_webview)
    Button btn_webview;
    @BindView(R.id.btn_aidl)
    Button btn_aidl;
    @BindView(R.id.btn_sqlite)
    Button btn_sqlite;
    @BindView(R.id.btn_matrix)
    Button btn_matrix;
    @BindView(R.id.btn_recycler)
    Button btn_recycler;
    @BindView(R.id.btn_popup_window)
    Button btn_popup_window;
    @BindView(R.id.btn_view_drag_helper)
    Button btn_view_drag_helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
    }

    @OnClick({R.id.btn_guaguaka, R.id.btn_arrow, R.id.btn_viewgroup_event,
            R.id.btn_expland_view, R.id.btn_zhifubao_view,
            R.id.btn_waterfall, R.id.btn_flow_view, R.id.btn_locky_pan,
            R.id.btn_qq, R.id.btn_circle_scale, R.id.btn_path_measure,
            R.id.btn_webview, R.id.btn_aidl, R.id.btn_sqlite, R.id.btn_matrix,
            R.id.btn_recycler, R.id.btn_popup_window, R.id.btn_view_drag_helper,
            R.id.btn_vertical_drag})
    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_guaguaka:

                startActivity(GuaGuaKaActivity.class);
                break;
            case R.id.btn_arrow:

                startActivity(ArrowActivity.class);
                break;
            case R.id.btn_viewgroup_event:

                startActivity(ViewGroupActivity.class);
                break;
            case R.id.btn_expland_view:
                startActivity(ExplandViewActivity.class);
                break;
            case R.id.btn_zhifubao_view:
                startActivity(ZhiFuBaoAnimActivity.class);
                break;
            case R.id.btn_waterfall:
                startActivity(WaterFallFlowActivity.class);
                break;
            case R.id.btn_flow_view:
                startActivity(FlowViewActivity.class);
                break;
            case R.id.btn_locky_pan:
                startActivity(SurfaceViewActivity.class);
                break;
            case R.id.btn_qq:
                startActivity(QQMainActivity.class);
                break;
            case R.id.btn_circle_scale:
                startActivity(CircleScaleActivity.class);
                break;
            case R.id.btn_path_measure:
                startActivity(PathMeasureActivity.class);
                break;
            case R.id.btn_webview:
                startActivity(WebViewActivity.class);
                break;
            case R.id.btn_aidl:
                startActivity(AIDLActivity.class);
                break;
            case R.id.btn_sqlite:
                startActivity(SqliteActivity.class);
                break;
            case R.id.btn_matrix:
                startActivity(MatrixActivity.class);
                break;
            case R.id.btn_recycler:
                startActivity(RecyclerActivity.class);
                break;
            case R.id.btn_popup_window:

                break;
            case R.id.btn_view_drag_helper:
                //
                startActivity(ViewDragHelperActivity.class);
                break;
            case R.id.btn_vertical_drag:
                startActivity(VerticalDragActivity.class);

                break;
        }
    }

    private void startActivity(Class<?> cls) {
        Intent mIntent = new Intent(MainActivity.this, cls);
        startActivity(mIntent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

package com.wxj.customview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wxj.customview.GuaGuaKaActivity.GuaGuaKaActivity;
import com.wxj.customview.adapter.MainAdapter;
import com.wxj.customview.aidl.AIDLActivity;
import com.wxj.customview.arrow.ArrowActivity;
import com.wxj.customview.bean.MainBean;
import com.wxj.customview.circle.scale.CircleScaleActivity;
import com.wxj.customview.explandView.ExplandViewActivity;
import com.wxj.customview.flowView.FlowViewActivity;
import com.wxj.customview.mListener.OnItemClickListener;
import com.wxj.customview.matrix.MatrixActivity;
import com.wxj.customview.pathmeasure.PathMeasureActivity;
import com.wxj.customview.qq.QQMainActivity;
import com.wxj.customview.recyclerview.RecyclerActivity;
import com.wxj.customview.recyclerview.RecyclerManagerActivity;
import com.wxj.customview.sqlite.SqliteActivity;
import com.wxj.customview.surfaceview.SurfaceViewActivity;
import com.wxj.customview.viewdraghelper.ViewDragHelperActivity;
import com.wxj.customview.viewdraghelpertest.VerticalDragActivity;
import com.wxj.customview.viewgroupEvent.ViewGroupActivity;
import com.wxj.customview.waterfallFlow.WaterFallFlowActivity;
import com.wxj.customview.webview.WebViewActivity;
import com.wxj.customview.zhifubao.ZhiFuBaoAnimActivity;
import com.wxj.customview.zhifubao.antForest.AntForestActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {

    @BindView(R.id.id_recyclerview)
    RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private List<MainBean> mList;
    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
        initData();
        bindData();

    }

    private void bindData() {
        mAdapter = new MainAdapter(mList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(this);
    }

    private void initData() {
        mList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            if (i == 0) {
                mList.add(new MainBean(i, "刮刮卡效果"));
            } else if (i == 1) {
                mList.add(new MainBean(i, "箭头旋转动画"));
            } else if (i == 2) {
                mList.add(new MainBean(i, "viewGroup和view的事件传递和分发"));
            } else if (i == 3) {
                mList.add(new MainBean(i, "自定义展开收缩view"));
            } else if (i == 4) {
                mList.add(new MainBean(i, "仿照支付宝支付动画效果"));
            } else if (i == 5) {
                mList.add(new MainBean(i, "瀑布流效果"));
            } else if (i == 6) {
                mList.add(new MainBean(i, "流式布局效果"));
            } else if (i == 7) {
                mList.add(new MainBean(i, "抽奖转盘效果"));
            } else if (i == 8) {
                mList.add(new MainBean(i, "仿照qq下拉阻尼动画效果"));
            } else if (i == 9) {
                mList.add(new MainBean(i, "圆盘刻度"));
            } else if (i == 10) {
                mList.add(new MainBean(i, "pathmeasure的使用"));
            } else if (i == 11) {
                mList.add(new MainBean(i, "webview打开多窗口"));
            } else if (i == 12) {
                mList.add(new MainBean(i, "aidl的学习"));
            } else if (i == 13) {
                mList.add(new MainBean(i, "sqlite的学习"));
            } else if (i == 14) {
                mList.add(new MainBean(i, "matrix的学习"));
            } else if (i == 15) {
                mList.add(new MainBean(i, "recyclerview的实例"));
            } else if (i == 16) {
                mList.add(new MainBean(i, "popupwindow动画"));
            } else if (i == 17) {
                mList.add(new MainBean(i, "viewdraghelper的使用"));
            } else if (i == 18) {
                mList.add(new MainBean(i, "viewdraghelper的实例"));
            } else if (i == 19) {
                mList.add(new MainBean(i, "支付宝蚂蚁森林"));
            }
        }

    }

    private void initView() {
        mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
    }

    @Override
    public void onClick(int i) {
        if (i == 0) {
            startActivity(GuaGuaKaActivity.class);
        } else if (i == 1) {
            startActivity(ArrowActivity.class);
        } else if (i == 2) {
            startActivity(ViewGroupActivity.class);
        } else if (i == 3) {
            startActivity(ExplandViewActivity.class);
        } else if (i == 4) {
            startActivity(ZhiFuBaoAnimActivity.class);
        } else if (i == 5) {
            startActivity(WaterFallFlowActivity.class);
        } else if (i == 6) {
            startActivity(FlowViewActivity.class);
        } else if (i == 7) {
            startActivity(SurfaceViewActivity.class);
        } else if (i == 8) {
            startActivity(QQMainActivity.class);
        } else if (i == 9) {
            startActivity(CircleScaleActivity.class);
        } else if (i == 10) {
            startActivity(PathMeasureActivity.class);
        } else if (i == 11) {
            startActivity(WebViewActivity.class);
        } else if (i == 12) {
            startActivity(AIDLActivity.class);
        } else if (i == 13) {
            startActivity(SqliteActivity.class);
        } else if (i == 14) {
            startActivity(MatrixActivity.class);
        } else if (i == 15) {
            startActivity(RecyclerManagerActivity.class);
        } else if (i == 16) {

        } else if (i == 17) {
            startActivity(ViewDragHelperActivity.class);
        } else if (i == 18) {
            startActivity(VerticalDragActivity.class);
        } else if (i == 19) {
            startActivity(AntForestActivity.class);
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

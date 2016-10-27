package com.example.wuxiaojun.rxjavaretrift.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.wuxiaojun.rxjavaretrift.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by wuxiaojun on 16-8-31.
 */
public class SecondFragment extends BaseFragment {

    @BindView(R.id.tv_content)
    TextView tv_content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first, null);
        ButterKnife.bind(this,view);
        tv_content.setText(SecondFragment.class.getSimpleName());
        return view;
    }

}

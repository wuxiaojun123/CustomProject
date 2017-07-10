package com.example.wuxiaojun.rxjavaretrift.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.wuxiaojun.rxjavaretrift.R;
import com.example.wuxiaojun.rxjavaretrift.activity.FirstActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by wuxiaojun on 16-8-31.
 */
public class SecondFragment extends BaseFragment implements View.OnClickListener {


    @BindView(R.id.id_btn_open)
    Button id_btn_open;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, null);
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @OnClick({R.id.id_btn_open})
    @Override
    public void onClick(View v) {

        startActivity(new Intent(getActivity(), FirstActivity.class));

    }


}

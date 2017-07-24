package com.wxj.customview.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wxj.customview.R;
import com.wxj.customview.adapter.viewholder.MainViewHolder;
import com.wxj.customview.bean.MainBean;
import com.wxj.customview.mListener.OnItemClickListener;

import java.util.List;

/**
 * Created by wuxiaojun on 17-7-24.
 */

public class MainAdapter extends RecyclerView.Adapter<MainViewHolder> {

    private List<MainBean> mList;

    public MainAdapter(List<MainBean> list) {
        this.mList = list;
    }


    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, final int position) {
        holder.tv_content.setText(mList.get(position).name);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemCLickListener != null) {
                    mOnItemCLickListener.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public OnItemClickListener mOnItemCLickListener;

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemCLickListener = listener;
    }

}

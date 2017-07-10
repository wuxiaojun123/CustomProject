package com.example.wuxiaojun.rxjavaretrift.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.wuxiaojun.rxjavaretrift.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import nsu.edu.com.library.SwipeBackActivity;

/**
 * Created by wuxiaojun on 16-12-22.
 */

public class FirstActivity extends SwipeBackActivity {

    @BindView(R.id.listview)
    ListView listview;

    List<String> list = new ArrayList<String>();
    private int itemPosition = -1;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        ButterKnife.bind(this);
        for (int i = 'a'; i <= 'z'; i++) {
            list.add("条目 " + i);
        }
        myAdapter = new MyAdapter();
        listview.setAdapter(myAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemPosition = position;
                myAdapter.notifyDataSetChanged();
            }
        });
    }


    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            convertView = LayoutInflater.from(FirstActivity.this).inflate(R.layout.item_listview, null);
            TextView textView = (TextView) convertView.findViewById(R.id.tv_item);
            textView.setText(list.get(position));
            if (itemPosition == position) {
                textView.setBackgroundColor(getResources().getColor(R.color.color_main_title));
            } else {
                textView.setBackgroundColor(getResources().getColor(R.color.color_while));
            }

            return convertView;
        }
    }

}

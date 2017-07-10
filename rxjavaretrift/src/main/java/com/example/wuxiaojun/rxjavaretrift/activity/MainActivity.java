package com.example.wuxiaojun.rxjavaretrift.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.example.wuxiaojun.rxjavaretrift.R;
import com.example.wuxiaojun.rxjavaretrift.fragment.FirstFragment;
import com.example.wuxiaojun.rxjavaretrift.fragment.SecondFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import nsu.edu.com.library.SwipeBackActivity;

/*****
 * http://api.douban.com/v2/movie/top250?start=5&count=20  (请求数据链接)
 * http://api.douban.com/v2/movie/coming_soon?start=0&count=20
 * http://api.douban.com/v2/movie/in_theaters?city=杭州
 */
public class MainActivity extends SwipeBackActivity {

    @BindView(R.id.id_tab_layout)
    TabLayout id_tab_layout;
    @BindView(R.id.id_viewpager)
    ViewPager id_viewpager;

    private Fragment[] fragments = new Fragment[2];
    private String[] titles = new String[2];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSwipeBackEnable(false);
        ButterKnife.bind(this);
        fragments[0] = new FirstFragment();
        fragments[1] = new SecondFragment();
        titles[0] = "tab1";
        titles[1] = "tab2";
        init();
        id_tab_layout.setupWithViewPager(id_viewpager);
    }

    private void init() {
        id_viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments[position];
            }

            @Override
            public int getCount() {
                return fragments.length;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return titles[position];
            }
        });
    }


}

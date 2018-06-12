package com.wxj.customview.city;

import java.util.ArrayList;
import java.util.List;

import com.qfdqc.views.demo.bean.CommunityBean;
import com.qfdqc.views.demo.view.CommunityLayout2;
import com.wxj.customview.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

/**
 * Created by 54966 on 2018/6/11.
 */

public class MainActivity3 extends Activity implements View.OnClickListener {

	private TextView			id_tv_add;

	private TextView			id_tv_reduction;

	private CommunityLayout2	id_community_layout2;

	private List<CommunityBean>	communityBeanList;

	private CoordsGenerator		coordsGenerator;

	int							childViewHeight	= 0;

	@Override protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_seatview2);

		id_tv_add = findViewById(R.id.id_tv_add);
		id_tv_reduction = findViewById(R.id.id_tv_reduction);
		id_community_layout2 = findViewById(R.id.id_community_layout2);
		id_tv_add.setOnClickListener(this);
		id_tv_reduction.setOnClickListener(this);

		coordsGenerator = new CoordsGenerator();
		coordsGenerator.init(151);
		communityBeanList = new ArrayList<>();

		CoordsGenerator.Coords coords = coordsGenerator.getCoords(0);
		CommunityBean bean = new CommunityBean();
		bean.x = coords.getX();
		bean.y = coords.getY();
		communityBeanList.add(bean);

		coords = coordsGenerator.getCoords(1);
		CommunityBean bean1 = new CommunityBean();
		bean1.x = coords.getX();
		bean1.y = coords.getY();
		// communityBeanList.add(bean1);

		if (childViewHeight == 0) {
			final View view = LayoutInflater.from(this).inflate(R.layout.community_item, null);
			FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
			view.setLayoutParams(params);
			view.post(new Runnable() {

				@Override public void run() {
					childViewHeight = view.getHeight();

				}
			});
		}
		id_community_layout2.setChildViewHeight(childViewHeight);
		id_community_layout2.setDatas(communityBeanList);
	}

	@Override public void onClick(View v) {
		int id = v.getId();
		switch (id) {
			case R.id.id_tv_add:

				int size = communityBeanList.size();

				CoordsGenerator.Coords coords = coordsGenerator.getCoords(size);

				CommunityBean communityBean = new CommunityBean();
				communityBean.x = coords.getX();
				communityBean.y = coords.getY();

				id_community_layout2.addCommunityBean(communityBean);

				break;
			case R.id.id_tv_reduction:

				id_community_layout2.reduction();

				break;
		}
	}

}

package com.dristy.talkingkids;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.dristy.talkingkids.R;
import com.dristy.talkingkids.adapters.ViewPagerAdapter;

public class ViewPagerFragmentsActivity extends DrawerActivity {
	ViewPager viewPager;
	ViewPagerAdapter adapter;
	@SuppressWarnings("static-access")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.replaceContentLayout(R.layout.view_pager_fragments,
				super.CONTENT_LAYOUT_ID);
		viewPager = (ViewPager) findViewById(R.id.pager);
		adapter =new ViewPagerAdapter(getSupportFragmentManager());
		viewPager.setAdapter(adapter);
		viewPager.setOffscreenPageLimit(10);
	}
}

package com.exmyth.wechat.slidinglistview.activity;

import com.exmyth.wechat.R;
import com.exmyth.wechat.slidinglistview.view.SlideListView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity {

	private SlideListView slideListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slidinglistview_activity_main);
		slideListView = (SlideListView)findViewById(R.id.slv_list);
		slideListView.initSlideMode(SlideListView.MOD_RIGHT);
		String[] arr = new String[]{"黄家驹","黄贯中","叶世荣","黄家强"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
		slideListView.setAdapter(adapter);
	}
}

package com.exmyth.wechat.slidingmenu.activity;

import com.exmyth.wechat.R;
import com.exmyth.wechat.slidingmenu.view.SlidingMenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class MainActivity extends Activity
{
	private SlidingMenu mMenu;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.slidingmenu_activity_main);
		mMenu = (SlidingMenu) findViewById(R.id.id_menu);
	}
}

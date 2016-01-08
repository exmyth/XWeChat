package com.exmyth.wechat.activity;

import com.exmyth.wechat.R;
import com.exmyth.wechat.view.NavigationBar;
import com.exmyth.wechat.view.NavigationBar.NavigationBarClickListener;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class NavigationBarActivity extends Activity {
	private NavigationBar navbar;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.navigation_bar_activity);
		navbar = (NavigationBar) findViewById(R.id.id_navigationBar);
		navbar.setLeftVisibility(false);
		navbar.setClickListener(new NavigationBarClickListener() {
			

			@Override
			public void onLeftClick(View v) {
				Toast.makeText(context, "LEFT", Toast.LENGTH_SHORT).show();
			}
			
			@Override
			public void onRightClick(View ov) {
				Toast.makeText(context, "RIGHT", Toast.LENGTH_SHORT).show();
			}
		});
	}
}

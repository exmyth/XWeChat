package com.exmyth.wechat.backhome.activity;

import com.exmyth.wechat.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.backhome_activity_main);
		initView();
	}

	private void initView() {
		findViewById(R.id.btnSecondActivity).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this,SecondActivity.class);
				startActivity(intent);
			}
		});
	}
}

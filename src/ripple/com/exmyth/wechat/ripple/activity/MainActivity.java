package com.exmyth.wechat.ripple.activity;

import com.exmyth.wechat.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

	private Button btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ripple_activity_main);
		initView();
	}

	private void initView() {
		btn = (Button)findViewById(R.id.ripple_activity_main_btn);
		btn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Log.d("myth", "ripple Click");
			}
		});
		btn.setOnLongClickListener(new View.OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				Log.d("myth", "ripple LongClick");
				return true;
			}
		});
	}
}

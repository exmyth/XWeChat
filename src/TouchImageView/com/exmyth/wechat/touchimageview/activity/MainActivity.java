package com.exmyth.wechat.touchimageview.activity;

import com.exmyth.wechat.R;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		TouchImageView imageView = new TouchImageView(this);
//		imageView.setImageResource(R.drawable.ic_launcher);
//		imageView.setBackgroundResource(R.drawable.ic_launcher);
//		setContentView(imageView);
		setContentView(R.layout.touch_image_view_activity_main);
	}
}

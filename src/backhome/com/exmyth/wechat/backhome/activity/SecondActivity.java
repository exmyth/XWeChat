package com.exmyth.wechat.backhome.activity;

import com.exmyth.wechat.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SecondActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.backhome_activity_second);
	}
	
	@Override
	public void onBackPressed() {
		//必须注释下面代码
		//super.onBackPressed();
		Intent intent= new Intent(Intent.ACTION_MAIN); 
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); 
		//如果是服务里调用，必须加入new task标识
		intent.addCategory(Intent.CATEGORY_HOME);
		startActivity(intent);
	}
}

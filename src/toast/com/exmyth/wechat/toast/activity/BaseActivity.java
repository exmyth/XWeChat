package com.exmyth.wechat.toast.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class BaseActivity extends Activity {
	private Toast toast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		showToast("");
	}

	private void showToast(CharSequence text) {
		if(toast == null){
			toast = Toast.makeText(BaseActivity.this, text, Toast.LENGTH_SHORT);
		}
		else{
			toast.setText(text);
		}
		toast.show();
	}
	
	
}

package com.exmyth.wechat.lockpattern.activity;

import com.exmyth.wechat.R;
import com.exmyth.wechat.lockpattern.view.LockPatternView;
import com.exmyth.wechat.lockpattern.view.LockPatternView.OnPatternChangeLister;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnPatternChangeLister {

	LockPatternView lock;
	TextView text;
	String p = "14789";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lock_pattern_activity_main);
		text = (TextView) findViewById(R.id.text);
		lock = (LockPatternView) findViewById(R.id.lock);
		lock.setOnPatternChangeLister(this);
	}

	@Override
	public void onPatternChange(String passwordStr) {
		if (!TextUtils.isEmpty(passwordStr)) {
			if (passwordStr.equals(p)) {
				text.setText(passwordStr);
			} else {
				text.setText("密码错误");
				lock.errorPoint();
			}
		}else {
			Toast.makeText(MainActivity.this, "至少连接5点", 0).show();
		}

	}

	@Override
	public void onPatterStart(boolean isStart) {
		if (isStart) {
			text.setText("请绘制图案");
		}
	}

}

package com.exmyth.wechat.launch.task;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class FlagActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		int flagActivityNewTask = Intent.FLAG_ACTIVITY_NEW_TASK;
		int flagActivitySingleTop = Intent.FLAG_ACTIVITY_SINGLE_TOP;
		int flagActivityClearTop = Intent.FLAG_ACTIVITY_CLEAR_TOP;
		int flagActivityBroughtToFront = Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT;
		int flagActivityNoUserAction = Intent.FLAG_ACTIVITY_NO_USER_ACTION;
	}
	
	@Override
	public void onUserInteraction() {
		// TODO Auto-generated method stub
		super.onUserInteraction();
	}
	
	@Override
	protected void onUserLeaveHint() {
		// TODO Auto-generated method stub
		super.onUserLeaveHint();
	}
}

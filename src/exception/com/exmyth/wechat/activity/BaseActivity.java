package com.exmyth.wechat.activity;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Thread.setDefaultUncaughtExceptionHandler(new MyUnCaughtExceptionHandler());
	}
	class MyUnCaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            ex.printStackTrace();
            // do some work here
  			android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }
}

package com.exmyth.wechat.exception.activity;

import android.app.Activity;
import android.os.Bundle;

public class BaseActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//参考http://sixwolf.net/blog/2016/04/11/Android去除烦人的闪退弹窗/
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

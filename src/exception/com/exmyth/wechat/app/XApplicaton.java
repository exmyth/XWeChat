package com.exmyth.wechat.app;

import android.app.Application;

public class XApplicaton extends Application {
	@Override
	public void onCreate() {
		super.onCreate();
		
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

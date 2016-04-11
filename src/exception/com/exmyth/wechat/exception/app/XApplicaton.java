package com.exmyth.wechat.exception.app;

import com.exmyth.wechat.exception.activity.MainActivity;

import android.app.Application;
import android.content.Intent;

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
            /*
			注意setFlags这一步是必需的，因为使用的Context是App的Context，
			所以必需打开一个新的任务队列，否则打开Activity无法生效，
			如果你替换Handler是在Activity做的，
			拿到的Context是Activity的Context，则无需这一步。
			*/
            Intent intent = new Intent(XApplicaton.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            XApplicaton.this.startActivity(intent);
            
  			android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }
}

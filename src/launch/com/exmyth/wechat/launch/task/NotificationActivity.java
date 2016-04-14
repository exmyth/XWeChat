package com.exmyth.wechat.launch.task;

import com.exmyth.wechat.R;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class NotificationActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
		
		mBuilder.setContentTitle("测试标题")//设置通知栏标题  
	    .setContentText("测试内容") //设置通知栏显示内容  
	    .setContentIntent(getDefalutIntent(Notification.FLAG_AUTO_CANCEL)) //设置通知栏点击意图  
	//  .setNumber(number) //设置通知集合的数量  
	    .setTicker("测试通知来啦") //通知首次出现在通知栏，带上升动画效果的  
	    .setWhen(System.currentTimeMillis())//通知产生的时间，会在通知信息里显示，一般是系统获取到的时间  
	    .setPriority(Notification.PRIORITY_DEFAULT) //设置该通知优先级  
	//  .setAutoCancel(true)//设置这个标志当用户单击面板就可以让通知将自动取消    
	    .setOngoing(false)//ture，设置他为一个正在进行的通知。他们通常是用来表示一个后台任务,用户积极参与(如播放音乐)或以某种方式正在等待,因此占用设备(如一个文件下载,同步操作,主动网络连接)  
	    .setDefaults(Notification.DEFAULT_VIBRATE)//向通知添加声音、闪灯和振动效果的最简单、最一致的方式是使用当前的用户默认设置，使用defaults属性，可以组合  
	    //Notification.DEFAULT_ALL  Notification.DEFAULT_SOUND 添加声音 // requires VIBRATE permission  
	    .setSmallIcon(R.drawable.ic_launcher);//设置通知小ICON 
		
		Notification notification = mBuilder.build();  
		notification.flags = Notification.FLAG_AUTO_CANCEL; 
		
		
		/*
		提醒标志符成员：
		Notification.FLAG_SHOW_LIGHTS              //三色灯提醒，在使用三色灯提醒时候必须加该标志符
		Notification.FLAG_ONGOING_EVENT          //发起正在运行事件（活动中）
		Notification.FLAG_INSISTENT   //让声音、振动无限循环，直到用户响应 （取消或者打开）
		Notification.FLAG_ONLY_ALERT_ONCE  //发起Notification后，铃声和震动均只执行一次
		Notification.FLAG_AUTO_CANCEL      //用户单击通知后自动消失
		Notification.FLAG_NO_CLEAR          //只有全部清除时，Notification才会清除 ，不清楚该通知(QQ的通知无法清除，就是用的这个)
		Notification.FLAG_FOREGROUND_SERVICE    //表示正在运行的服务
		*/
		
		int notifyId = 1;
		mNotificationManager.notify(notifyId, mBuilder.build()); 
	}
	
	public PendingIntent getDefalutIntent(int flags){  
	    PendingIntent pendingIntent= PendingIntent.getActivity(this, 1, new Intent(), flags);  
	    return pendingIntent;  
	}
}

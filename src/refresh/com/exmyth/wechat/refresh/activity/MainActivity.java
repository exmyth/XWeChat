package com.exmyth.wechat.refresh.activity;

import java.util.ArrayList;

import com.exmyth.wechat.R;
import com.exmyth.wechat.refresh.adapter.MyAdapter;
import com.exmyth.wechat.refresh.entity.ApkEntity;
import com.exmyth.wechat.refresh.view.PullRefreshListView;
import com.exmyth.wechat.refresh.view.PullRefreshListView.IRefreshListener;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends Activity implements IRefreshListener{
	ArrayList<ApkEntity> apk_list = new ArrayList<ApkEntity>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.refresh_activity_main);
		getData();
		showListView(apk_list);
	}

	MyAdapter adapter;
	PullRefreshListView listview;
	private void showListView(ArrayList<ApkEntity> apk_list) {
		if (adapter == null) {
			listview = (PullRefreshListView) findViewById(R.id.listview);
			listview.setRefreshInterface(this);
			adapter = new MyAdapter(this, apk_list);
			listview.setAdapter(adapter);
		} else {
			adapter.onDateChange(apk_list);
		}
	}

	private void getData() {
		for (int i = 0; i < 10; i++) {
			ApkEntity entity = new ApkEntity();
			entity.setName("测试程序");
			entity.setInfo("50w用户");
			entity.setDes("这是一个神奇的应用！");
			apk_list.add(entity);
		}
	}
	private void getLoadData() {
		for (int i = 0; i < 2; i++) {
			ApkEntity entity = new ApkEntity();
			entity.setName("更多程序");
			entity.setInfo("50w用户");
			entity.setDes("这是一个神奇的应用！");
			apk_list.add(entity);
		}
	}

	@Override
	public void onPullUp() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				//获取更多数据
				getLoadData();
				//更新listview显示；
				showListView(apk_list);
				//通知listview加载完毕
				listview.loadComplete();
			}
		}, 2000);
	}
	
	@Override
	public void onPullDown() {
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				//获取最新数据
				setReflashData();
				//通知界面显示
				showListView(apk_list);
				//通知listview 刷新数据完毕；
				listview.reflashComplete();
			}
		}, 2000);
	}
	
	private void setReflashData() {
		for (int i = 0; i < 2; i++) {
			ApkEntity entity = new ApkEntity();
			entity.setName("刷新数据");
			entity.setDes("这是一个神奇的应用");
			entity.setInfo("50w用户");
			apk_list.add(0,entity);
		}
	}
}

package com.exmyth.wechat.download.activity;

import com.exmyth.wechat.R;
import com.exmyth.wechat.download.entities.FileInfo;
import com.exmyth.wechat.download.service.DownloadService;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private TextView mFileName = null;
	private ProgressBar mProgressBar = null;
	private Button mStartBtn = null;
	private Button mStopBtn = null;
	public static MainActivity mMainActivity = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.download_activity_main);
        
        // 初始化控件
        mFileName = (TextView) findViewById(R.id.tv_fileName);
        mProgressBar = (ProgressBar) findViewById(R.id.pb_progress);
        mStartBtn = (Button) findViewById(R.id.btn_start);
        mStopBtn = (Button) findViewById(R.id.btn_stop);
        mProgressBar.setMax(100);
        // 初始化文件信息对象
        final FileInfo fileInfo = new FileInfo(0, 
        		"http://www.imooc.com/mobile/imooc.apk",
        		"imooc.apk", 0, 0);
        
        // 添加事件监听
        mStartBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 通过Intent传递参数给Service
				Intent intent = new Intent(MainActivity.this, DownloadService.class);
				intent.setAction(DownloadService.ACTION_START);
				intent.putExtra("fileInfo", fileInfo);
				startService(intent);
			}
		});
        
        mStopBtn.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				// 通过Intent传递参数给Service
				Intent intent = new Intent(MainActivity.this, DownloadService.class);
				intent.setAction(DownloadService.ACTION_STOP);
				intent.putExtra("fileInfo", fileInfo);
				startService(intent);
			}
		});
        
        // 注册广播接收器
        IntentFilter filter = new IntentFilter();
        filter.addAction(DownloadService.ACTION_UPDATE);
        registerReceiver(mReceiver, filter);
        
        mMainActivity = this;
    }
    
    protected void onDestroy() 
    {
    	super.onDestroy();
    	unregisterReceiver(mReceiver);
    }
    
    /** 
     * 更新UI的广播接收器
     */
    BroadcastReceiver mReceiver = new BroadcastReceiver()
	{
		@Override
		public void onReceive(Context context, Intent intent)
		{
			if (DownloadService.ACTION_UPDATE.equals(intent.getAction()))
			{
				int finised = intent.getIntExtra("finished", 0);
				mProgressBar.setProgress(finised);
			}
		}
	};
	
	/**
	 * 监听返回键
	 * @see android.app.Activity#onKeyUp(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) 
	{
		if (KeyEvent.KEYCODE_BACK == keyCode && mStartBtn != null)   // 按了返回键时应暂停下载
		{
			mStopBtn.performClick();  // 模拟按下暂停按钮
		}

		return super.onKeyUp(keyCode, event);
	}
	
	public Handler handler = new Handler()
	{
		public void handleMessage(android.os.Message msg) {
			Toast.makeText(mMainActivity, "下载完毕", 0).show();
		}
	};
}

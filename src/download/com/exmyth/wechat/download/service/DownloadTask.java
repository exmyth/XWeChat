/*
 * @Title DownloadTask.java
 * @Copyright Copyright 2010-2015 Yann Software Co,.Ltd All Rights Reserved.
 * @Description：
 * @author Yann
 * @date 2015-8-7 下午10:11:05
 * @version 1.0
 */
package com.exmyth.wechat.download.service;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import org.apache.http.HttpStatus;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.exmyth.wechat.download.activity.MainActivity;
import com.exmyth.wechat.download.db.ThreadDAO;
import com.exmyth.wechat.download.db.ThreadDAOImpl;
import com.exmyth.wechat.download.entities.FileInfo;
import com.exmyth.wechat.download.entities.ThreadInfo;

/** 
 * 下载任务类
 * @author Yann
 * @date 2015-8-7 下午10:11:05
 */
public class DownloadTask
{
	private Context mContext = null;
	private FileInfo mFileInfo = null;
	private ThreadDAO mDao = null;
	private int mFinised = 0;
	public boolean isPause = false;
	
	/** 
	 *@param mContext
	 *@param mFileInfo
	 */
	public DownloadTask(Context mContext, FileInfo mFileInfo)
	{
		this.mContext = mContext;
		this.mFileInfo = mFileInfo;
		mDao = new ThreadDAOImpl(mContext);
	}
	
	public void downLoad()
	{
		// 读取数据库的线程信息
		List<ThreadInfo> threads = mDao.getThreads(mFileInfo.getUrl());
		ThreadInfo threadInfo = null;
		
		if (0 == threads.size())
		{
			// 初始化线程信息对象
			threadInfo = new ThreadInfo(0, mFileInfo.getUrl(),
					0, mFileInfo.getLength(), 0);
		}
		else
		{
			threadInfo = threads.get(0);
		}
		
		// 创建子线程进行下载
		new DownloadThread(threadInfo).start();
	}
	
	/** 
	 * 下载线程
	 * @author Yann
	 * @date 2015-8-8 上午11:18:55
	 */ 
	private class DownloadThread extends Thread
	{
		private ThreadInfo mThreadInfo = null;

		/** 
		 *@param mInfo
		 */
		public DownloadThread(ThreadInfo mInfo)
		{
			this.mThreadInfo = mInfo;
		}
		
		/**
		 * @see java.lang.Thread#run()
		 */
		@Override
		public void run()
		{
			// 向数据库插入线程信息
			if (!mDao.isExists(mThreadInfo.getUrl(), mThreadInfo.getId()))
			{
				mDao.insertThread(mThreadInfo);
			}
			
			HttpURLConnection connection = null;
			RandomAccessFile raf = null;
			InputStream inputStream = null;
			
			try
			{
				URL url = new URL(mThreadInfo.getUrl());
				connection = (HttpURLConnection) url.openConnection();
				connection.setConnectTimeout(5000);
				connection.setRequestMethod("GET");
				// 设置下载位置
				int start = mThreadInfo.getStart() + mThreadInfo.getFinished();
				connection.setRequestProperty("Range",
						"bytes=" + start + "-" + mThreadInfo.getEnd());
				// 设置文件写入位置
				File file = new File(DownloadService.DOWNLOAD_PATH,
						mFileInfo.getFileName());
				raf = new RandomAccessFile(file, "rwd");
				raf.seek(start);
				Intent intent = new Intent();
				intent.setAction(DownloadService.ACTION_UPDATE);
				mFinised += mThreadInfo.getFinished();
				// 开始下载
				if (connection.getResponseCode() == HttpStatus.SC_PARTIAL_CONTENT)
				{
					// 读取数据
					inputStream = connection.getInputStream();
					byte buf[] = new byte[1024 << 2];
					int len = -1;
					long time = System.currentTimeMillis();
					while ((len = inputStream.read(buf)) != -1)
					{
						// 写入文件
						raf.write(buf, 0, len);
						// 把下载进度发送广播给Activity
						mFinised += len;
						if (System.currentTimeMillis() - time > 500)
						{
							time = System.currentTimeMillis();
							intent.putExtra("finished", mFinised * 100 / mThreadInfo.getEnd());
							mContext.sendBroadcast(intent);
						}
						
						// 在下载暂停时，保存下载进度
						if (isPause)
						{
							mDao.updateThread(mThreadInfo.getUrl(),	mThreadInfo.getId(), mFinised);
							return;
						}
					}
					
					// 删除线程信息
					mDao.deleteThread(mThreadInfo.getUrl(),	mThreadInfo.getId());
					Log.i("DownloadTask", "下载完毕");
					MainActivity.mMainActivity.handler.sendEmptyMessage(0);
				}
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				try
				{
					if (connection != null)
					{
						connection.disconnect();
					}
					if (raf != null)
					{
						raf.close();
					}
					if (inputStream != null)
					{
						inputStream.close();
					}
				}
				catch (Exception e2)
				{
					e2.printStackTrace();
				}
			}
		}
	}
}

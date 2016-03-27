package com.exmyth.wechat.asynctask.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

import com.exmyth.wechat.R;
import com.exmyth.wechat.asynctask.adapter.NewsAdapter;
import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;


@SuppressLint("NewApi")
public class ImgLoader
{
	private ImageView mImageView;
	private String mUrl;

	private LruCache<String, Bitmap> mCache;

	private ListView mListView;
	private Set<NewsAynsTaskImgView> mtask;


	public ImgLoader(ListView listView)
	{
		int maxMemory = (int) Runtime.getRuntime().maxMemory();     //获取最大可用内存
		int cacheSize = maxMemory / 4;
		mListView = listView;
		mtask = new HashSet<NewsAynsTaskImgView>();
		mCache = new LruCache<String, Bitmap>(cacheSize)
		{
			@Override
			protected int sizeOf(String key, Bitmap value)
			{
				// 在每次存入缓存时调用
//				return value.getByteCount();
				return value.getRowBytes() * value.getHeight();
			}
		};
	}


	/**
	 * 增加到缓存
	 *
	 * @param url   图片的网站,也是图片的唯一标志
	 * @param bitmap
	 */
	public void addBitmapToCache(String url, Bitmap bitmap)
	{
		if(getBitmapFromCache(url) == null)
		{
			mCache.put(url, bitmap);
		}
	}

	/**
	 * 从缓存中获取数据
	 *
	 * @param url
	 * @return
	 */
	public Bitmap getBitmapFromCache(String url)
	{
		return mCache.get(url);
	}


	private Handler mHandler = new Handler()
	{
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);

			/**
			 * 第1 种方法: 判断 ListView 的绑定,避免listview 的错乱,因为 ListView 的缓存造成的
			 */
			if(mImageView.getTag().equals(mUrl))
			{
				mImageView.setImageBitmap((Bitmap) msg.obj);
			}
		}
	};


	/**
	 * 线程方法加载图片
	 *
	 * @param imageView
	 * @param url
	 */
	public void showImgByThread(ImageView imageView, final String url)
	{
		mImageView = imageView;
		this.mUrl = url;

		new Thread()
		{
			public void run()
			{
				super.run();
				Bitmap bitmap = getBitmapFromURL(url);
				Message message = Message.obtain();
				message.obj = bitmap;
				mHandler.sendMessage(message);
			}
		}.start();
	}


	/**
	 * 从网络中获取图片
	 *
	 * @param urlString 图片网址
	 * @return 获取到的图片
	 */
	public Bitmap getBitmapFromURL(String urlString)
	{
		Bitmap bitmap = null;
		InputStream is = null;
		try
		{
			URL url = new URL(urlString);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();

			is = new BufferedInputStream(conn.getInputStream());

			bitmap = BitmapFactory.decodeStream(is);
			conn.disconnect();
		}
		catch(MalformedURLException e)
		{
			e.printStackTrace();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				is.close();
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}

		return bitmap;
	}


	/**
	 * 展示图片 AysncTask() 这种方法
	 */
	public void showImgByAysncTask(ImageView imageView, String url)
	{
		Bitmap bitmap = getBitmapFromCache(url);

		// 从缓存中取出对应的图片
		//如果没有,就从网线上下载图片
		if(bitmap == null)
		{
			imageView.setImageResource(R.drawable.ic_launcher);
		}
		else        //有了,直接使用缓存的图片即可
		{
			imageView.setImageBitmap(bitmap);
		}

	}

	/**
	 * 停止加载图片 ,滑动时
	 */
	public void cancelAllTasks()
	{
		if(mtask != null)
		{
			for(NewsAynsTaskImgView task : mtask)
			{
				task.cancel(false);
			}
		}
	}

	private class NewsAynsTaskImgView extends AsyncTask<String, Void, Bitmap>
	{
		//private ImageView mImageView;
		private String mUrl;

		public NewsAynsTaskImgView(String url)
		{
			//		mImageView = imageView;
			mUrl = url;
		}

		@Override
		protected Bitmap doInBackground(String... params)
		{
			String url = params[0];
			Bitmap bitmap = getBitmapFromURL(url);
			/**
			 *	从网络获取图片
			 */
			if(bitmap != null)
			{
				//把图片加入缓存
				addBitmapToCache(url, bitmap);
			}
			return bitmap;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap)
		{
			super.onPostExecute(bitmap);
			ImageView imageView = (ImageView) mListView.findViewWithTag(mUrl);
			if(imageView.getTag().equals(mUrl) && bitmap != null)
			{
				imageView.setImageBitmap(bitmap);
			}
		}
	}


	/**
	 * 加载指定的图片
	 *
	 * @param start 开始图片标志
	 * @param end   结束图片标志
	 */
	public void loadImages(int start, int end)
	{
		for(int i = start; i < end; i++)
		{
			String url = NewsAdapter.URLS[i];
			Bitmap bitmap = getBitmapFromCache(url);
			if(bitmap == null)
			{
				NewsAynsTaskImgView task = new NewsAynsTaskImgView(url);
				task.execute(url);
				mtask.add(task);
			}
			else        //有了,直接使用缓存的图片即可
			{
				ImageView imageView = (ImageView) mListView.findViewWithTag(url);
				imageView.setImageBitmap(bitmap);
			}
		}
	}


}

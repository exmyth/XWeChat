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
import android.support.v4.util.LruCache;
import android.widget.ImageView;
import android.widget.ListView;


@SuppressLint("NewApi")
public class ImageLoader {
	private ImageView mImageView;
	private String mUrl;
	private ListView mListView;
	private Set<NewsAsyncTack> mTask;
	//创建lruCache
	private LruCache<String,Bitmap> mLruCache;
	public ImageLoader(ListView listView){
		mListView=listView;
		mTask=new HashSet<NewsAsyncTack>();
		int maxMemory=(int) Runtime.getRuntime().maxMemory();
		int cachSize=maxMemory/4;
		mLruCache=new LruCache<String, Bitmap>(cachSize){
			//内部方法sizeOf设置每一张图片的缓存大小
			protected int sizeOf(String key, Bitmap value) {
				//在每次存入缓存时调用，告诉系统这张缓存图片有多大
				return value.getByteCount();
			}
		};
	}

	public void addBitmapToCache(String url,Bitmap bitmap){
		if (getBitmapfromCache(url)==null) {//判断当前缓存是否存在
			mLruCache.put(url, bitmap);
		}
	}
	public Bitmap getBitmapfromCache(String url){
		return mLruCache.get(url);//可将lruCache看成map
	}
	
	@SuppressLint("HandlerLeak")
	private Handler handler=new Handler(){
		@SuppressLint("HandlerLeak")
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (mImageView.getTag().equals(mUrl)) {
				mImageView.setImageBitmap((Bitmap)msg.obj);
			}
		}
	};
	
	public void showimageThread(ImageView imageView,final String url){
		mImageView=imageView;
		mUrl=url;
		new Thread(){
			public void run() {
				super.run();
				Bitmap bitmap=getBitmapFromUrl(url);
				//此时的bitmap不能直接赋给ImageView，要创建Message将bitmap用handler发送出去给Handler，使之在主线程ui中更新
				Message message=Message.obtain();
				message.obj=bitmap;
				handler.sendMessage(message);
			}
		}.start();
	}
	//从url中获取一个Bitmap
	public Bitmap getBitmapFromUrl(String urlString){
		Bitmap bitmap;
		InputStream inputStream = null;
		try {
			URL url=new URL(urlString);
			HttpURLConnection connection=(HttpURLConnection) url.openConnection();
			inputStream=new BufferedInputStream(connection.getInputStream());
			bitmap=BitmapFactory.decodeStream(inputStream);
			connection.disconnect();
			return bitmap;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				inputStream.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;   //为什么要返回null
	}

	public void showImageByAsyncTask(ImageView imageView,String url){  //将默认图片imageView换成要加载的图片的url
		//从缓存中取出对应的图片
		Bitmap bitmap=getBitmapfromCache(url);
		//如果没有就从网上下载
		if (bitmap==null) {
//			new NewsAsyncTack(url).execute(url);
			imageView.setImageResource(R.drawable.ic_launcher);
		}else {
			imageView.setImageBitmap(bitmap);
		}
	}
	/*
	 * cancelAllTask()和loadImages方法是用来处理滚动时的优化
	 */
	public void cancelAllTasks(){
		if (mTask!=null) {
			for (NewsAsyncTack task : mTask) {
				task.cancel(false);
			}
		}
	}
	
	public void loadImages(int start ,int end){
		for (int i = start; i < end; i++) {
			String url=NewsAdapter.URLS[i];
			//从缓存中取出对应的图片
			Bitmap bitmap=getBitmapfromCache(url);
			//如果没有就从网上下载
			if (bitmap==null) {
				NewsAsyncTack task=new NewsAsyncTack(url);
				task.execute(url);
				mTask.add(task);
//				new NewsAsyncTack(imageView, url).execute(url);
			}else {
//				imageView.setImageBitmap(bitmap);
				ImageView imageView=(ImageView) mListView.findViewWithTag(url);
				imageView.setImageBitmap(bitmap);
			}
		}
	}
	
	private class NewsAsyncTack extends AsyncTask<String, Void, Bitmap>{
		//写一个构造方法传递imageview
//		private ImageView mimageView;
		private String mUrl;
		public NewsAsyncTack(String url){
//			mimageView=imageView;
			this.mUrl=url;
		}
		protected Bitmap doInBackground(String... arg0) {
			//			return getBitmapFormUrl(arg0[0]);// 不需要listview滚动优化缓存时
			String url=arg0[0];
			//从网络上获取图片
			Bitmap bitmap=getBitmapFromUrl( url);
			if (bitmap!=null) {
				//将不在缓存的图片加入缓存
				addBitmapToCache(url, bitmap);
			}
			return bitmap;
		}
		
		protected void onPostExecute(Bitmap bitmap) {//将bitmap设置给imageview
			super.onPostExecute(bitmap);
//			if (mimageView.getTag().equals(mUrl)) {
//				mimageView.setImageBitmap(bitmap);
//			}
//			mimageView.setImageBitmap(bitmap);
			ImageView imageView=(ImageView) mListView.findViewWithTag(mUrl);
			if (imageView!=null && bitmap!=null) {
				imageView.setImageBitmap(bitmap);
			}
			mTask.remove(this);  //此时AsyncTack已经结束，调用mtask结束AsyncTack自身
		}
	}
}

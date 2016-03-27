package com.exmyth.wechat.asynctask.adapter;

import java.util.List;

import com.exmyth.wechat.R;
import com.exmyth.wechat.asynctask.model.Bean;
import com.exmyth.wechat.asynctask.util.ImageLoader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener{
	private List<Bean> mList;
	private LayoutInflater mInflater;
	private ImageLoader mImageLoader;
	private int mStart,mEnd;
	public static String[] URLS;
	private boolean mFirstIn;
	public NewsAdapter(Context context,List<Bean> data,ListView listView){
		mList=data;
		mInflater=LayoutInflater.from(context);
		mImageLoader=new ImageLoader(listView);
		URLS=new String[data.size()];
		for (int i = 0; i < data.size(); i++) {
			URLS[i]=data.get(i).newsIconUrl;
		}
		//注册OnScrollListener
		listView.setOnScrollListener(this);
	}
	public int getCount() {
		return mList.size();
	}
	public Object getItem(int position) {
		return mList.get(position);
	}
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View v, ViewGroup parent) {
		//用ViewHolder,主要是进行一些性能优化,减少一些不必要的重复操作,将v的tag设置为ViewHolder,不为空时即可重复使用
		viewHolder holder=null;
		if (v==null) {
			holder=new viewHolder();
			v=mInflater.inflate(R.layout.async_task_list_item, parent,false);//laout文件转化成convertview
			holder.Icon=(ImageView) v.findViewById(R.id.imageView1);
			holder.title=(TextView) v.findViewById(R.id.title);
			holder.content=(TextView) v.findViewById(R.id.content);
			v.setTag(holder);
		}else {
			holder=(viewHolder) v.getTag();
		}
		holder.Icon.setImageResource(R.drawable.ic_launcher);
		String url=mList.get(position).newsIconUrl;
		holder.Icon.setTag(url);
		//通过showimageThread得到newsIconURl加载新图片
//		new imageLoader().showimageThread(vHolder.Icon, url);
		//不做图片缓存时
//		new imageLoader().showImageByAsyncTask(vHolder.Icon, url);
		mImageLoader.showImageByAsyncTask(holder.Icon, url);//做图片缓存时
		holder.title.setText(mList.get(position).newsTitle);
		holder.content.setText(mList.get(position).newsContent);
		return v;
	}
	class viewHolder{
		public TextView title,content;
		public ImageView Icon;
	}
	@Override
	public void onScroll(AbsListView view,int firstVisibleItem,int visibleItemCount,int endVisibleItem) {
		mStart=firstVisibleItem;
		mEnd=firstVisibleItem+visibleItemCount;
		//第一次显示的时候调用,增加visibleItemCount>0防止onScroll多次调用
		if (mFirstIn && visibleItemCount>0) {
			mImageLoader.loadImages(mStart,mEnd);
			mFirstIn=false;
		}
	}
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		if (scrollState==SCROLL_STATE_IDLE) {//闲置时
			//加载可见项
			mImageLoader.loadImages(mStart,mEnd);
		}else {
			mImageLoader.cancelAllTasks();
		}
	}
}

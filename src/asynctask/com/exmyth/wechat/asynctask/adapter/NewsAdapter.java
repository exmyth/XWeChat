package com.exmyth.wechat.asynctask.adapter;

import java.util.List;

import com.exmyth.wechat.R;
import com.exmyth.wechat.asynctask.model.NewsBeam;
import com.exmyth.wechat.asynctask.util.ImgLoader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class NewsAdapter extends BaseAdapter implements AbsListView.OnScrollListener
{
	private List<NewsBeam> mList;
	private LayoutInflater mInflater;

	private ImgLoader mImgLoader;

	private int mStart;
	private int mEnd;
	public static String[] URLS;

	private boolean mFirstIn;


	public NewsAdapter(Context context, List<NewsBeam> data, ListView listView)
	{
		mList = data;
		mInflater = LayoutInflater.from(context);
		mImgLoader = new ImgLoader(listView);
		URLS = new String[data.size()];
		for(int i = 0; i < data.size(); i++)
		{
			URLS[i] = data.get(i).newsIconUrl;
		}
		listView.setOnScrollListener(this);
		mFirstIn = true;
	}

	@Override
	public int getCount()
	{
		return mList.size();
	}

	@Override
	public Object getItem(int position)
	{
		return mList.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder;
		if(convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.async_task_list_item, null);
			viewHolder.icon = (ImageView) convertView.findViewById(R.id.id_icon);
			viewHolder.title = (TextView) convertView.findViewById(R.id.id_title);
			viewHolder.content = (TextView) convertView.findViewById(R.id.id_content);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.icon.setImageResource(R.drawable.ic_launcher);
		String url = mList.get(position).newsIconUrl;


		/**
		 * 第1 种方法: 绑定 listView ,避免 图片加载错乱
		 */
		viewHolder.icon.setTag(url);

		//多线程方式
		//new ImgLoader().showImgByThread(viewHolder.icon, url);

		//AysncTask 方式加载图片
		mImgLoader.showImgByAysncTask(viewHolder.icon, url);

		viewHolder.title.setText(mList.get(position).newsTitle);
		viewHolder.content.setText(mList.get(position).newsContent);
		return convertView;
	}

	class ViewHolder
	{
		public TextView title;
		public TextView content;
		public ImageView icon;
	}


	/**
	 * 下面是滑动状态监听
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState)
	{
		//处于停止状态
		if(scrollState == SCROLL_STATE_IDLE)
		{
			//加载可见项
			mImgLoader.loadImages(mStart, mEnd);
		}
		else
		{
			//停止加载
			mImgLoader.cancelAllTasks();
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
	{
		mStart = firstVisibleItem;
		mEnd = firstVisibleItem + visibleItemCount;

		//首次加载时 使用的,
		if(mFirstIn == true && visibleItemCount > 0)
		{
			mImgLoader.loadImages(mStart, mEnd);
			mFirstIn = false;
		}
	}


}

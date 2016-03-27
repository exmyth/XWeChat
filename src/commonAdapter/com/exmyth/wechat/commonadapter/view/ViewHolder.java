/*
 * @Title ViewHolder.java
 * @Copyright Copyright 2010-2015 Yann Software Co,.Ltd All Rights Reserved.
 * @Description：
 * @author Yann
 * @date 2015-8-5 下午9:08:31
 * @version 1.0
 */
package com.exmyth.wechat.commonadapter.view;


import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/** 
 * 类注释
 * @author Yann
 * @date 2015-8-5 下午9:08:31
 */
public class ViewHolder
{
	private SparseArray<View> mViews;
	private int mPosition;
	private View mConvertView;

	public View getConvertView()
	{
		return mConvertView;
	}

	private ViewHolder(Context context, ViewGroup parent, int layoutId, int position)
	{
		this.mViews = new SparseArray<View>();
		this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		this.mConvertView.setTag(this);
		this.mPosition = position;
	}
	
	public static ViewHolder get(Context context, View convertView, 
			ViewGroup parent, int layoutId, int position)
	{
		if (null == convertView)
		{
			return new ViewHolder(context, parent, layoutId, position);
		}
		else
		{
			ViewHolder holder = (ViewHolder) convertView.getTag();
			holder.mPosition = position;
			
			return holder;
		}
	}
	
	/** 
	 * 通过viewId获取控件
	 * @param viewId
	 * @return
	 * @return T
	 * @author Yann
	 * @date 2015-8-5 下午9:38:39
	 */ 
	public <T extends View>T getView(int viewId)
	{
		View view = mViews.get(viewId);
		
		if (null == view)
		{
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		
		return (T)view;
	}
	
	/** 
	 * 给ID为viewId的TextView设置文字text，并返回this
	 * @param viewId
	 * @param text
	 * @return
	 * @return ViewHolder
	 * @author Yann
	 * @date 2015-8-5 下午11:05:17
	 */ 
	public ViewHolder setText(int viewId, String text)
	{
		TextView tv = getView(viewId);
		tv.setText(text);
		
		return this;
	} 
}

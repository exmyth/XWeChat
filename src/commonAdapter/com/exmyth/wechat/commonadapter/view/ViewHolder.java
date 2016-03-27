/*
 * @Title ViewHolder.java
 * @Copyright Copyright 2010-2015 Yann Software Co,.Ltd All Rights Reserved.
 * @Description：
 * @author Yann
 * @date 2015-8-5 下午9:08:31
 * @version 1.0
 */
package com.exmyth.wechat.commonadapter.view;


import com.exmyth.wechat.util.ImageLoader;
import com.exmyth.wechat.util.ImageLoader.Type;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
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
	private int mLayoutId;
	private View mConvertView;
	private Context mContext;

	public View getConvertView()
	{
		return mConvertView;
	}

	private ViewHolder(Context context, ViewGroup parent, int layoutId, int position)
	{
		this.mViews = new SparseArray<View>();
		this.mConvertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
		this.mConvertView.setTag(this);
		this.mContext = context;
		this.mPosition = position;
		this.mLayoutId = layoutId;
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
	public int getLayoutId(){
		return mLayoutId;
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
	 */
	public ViewHolder setText(int viewId, String text)
	{
		TextView tv = getView(viewId);
		tv.setText(text);
		return this;
	}

	public ViewHolder setTextColor(int viewId, int textColor)
	{
		TextView view = getView(viewId);
		view.setTextColor(textColor);
		return this;
	}

	public ViewHolder setTextColorRes(int viewId, int textColorRes)
	{
		TextView view = getView(viewId);
		view.setTextColor(mContext.getResources().getColor(textColorRes));
		return this;
	}
	
	public ViewHolder setImageResource(int viewId, int resId)
	{
		ImageView view = getView(viewId);
		view.setImageResource(resId);
		return this;
	}

	public ViewHolder setImageBitmap(int viewId, Bitmap bitmap)
	{
		ImageView view = getView(viewId);
		view.setImageBitmap(bitmap);
		return this;
	}

	public ViewHolder setImageDrawable(int viewId, Drawable drawable)
	{
		ImageView view = getView(viewId);
		view.setImageDrawable(drawable);
		return this;
	}
	
	/**
	 * 为ImageView设置图片
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageByUrl(int viewId, String url)
	{
		ImageLoader.getInstance(3,Type.LIFO).loadImage(url, (ImageView) getView(viewId));
		return this;
	}

	public ViewHolder setBackgroundColor(int viewId, int color)
	{
		View view = getView(viewId);
		view.setBackgroundColor(color);
		return this;
	}

	public ViewHolder setBackgroundRes(int viewId, int backgroundRes)
	{
		View view = getView(viewId);
		view.setBackgroundResource(backgroundRes);
		return this;
	}

	@SuppressLint("NewApi")
	public ViewHolder setAlpha(int viewId, float value)
	{
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
			getView(viewId).setAlpha(value);
		} else
		{
			// Pre-honeycomb hack to set Alpha value
			AlphaAnimation alpha = new AlphaAnimation(value, value);
			alpha.setDuration(0);
			alpha.setFillAfter(true);
			getView(viewId).startAnimation(alpha);
		}
		return this;
	}

	public ViewHolder setVisible(int viewId, boolean visible)
	{
		View view = getView(viewId);
		view.setVisibility(visible ? View.VISIBLE : View.GONE);
		return this;
	}

	public ViewHolder linkify(int viewId)
	{
		TextView view = getView(viewId);
		Linkify.addLinks(view, Linkify.ALL);
		return this;
	}

	public ViewHolder setTypeface(Typeface typeface, int... viewIds)
	{
		for (int viewId : viewIds)
		{
			TextView view = getView(viewId);
			view.setTypeface(typeface);
			view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
		}
		return this;
	}

	public ViewHolder setProgress(int viewId, int progress)
	{
		ProgressBar view = getView(viewId);
		view.setProgress(progress);
		return this;
	}

	public ViewHolder setProgress(int viewId, int progress, int max)
	{
		ProgressBar view = getView(viewId);
		view.setMax(max);
		view.setProgress(progress);
		return this;
	}

	public ViewHolder setMax(int viewId, int max)
	{
		ProgressBar view = getView(viewId);
		view.setMax(max);
		return this;
	}

	public ViewHolder setRating(int viewId, float rating)
	{
		RatingBar view = getView(viewId);
		view.setRating(rating);
		return this;
	}

	public ViewHolder setRating(int viewId, float rating, int max)
	{
		RatingBar view = getView(viewId);
		view.setMax(max);
		view.setRating(rating);
		return this;
	}

	public ViewHolder setTag(int viewId, Object tag)
	{
		View view = getView(viewId);
		view.setTag(tag);
		return this;
	}

	public ViewHolder setTag(int viewId, int key, Object tag)
	{
		View view = getView(viewId);
		view.setTag(key, tag);
		return this;
	}

	public ViewHolder setChecked(int viewId, boolean checked)
	{
		Checkable view = (Checkable) getView(viewId);
		view.setChecked(checked);
		return this;
	}

	/**
	 * 关于事件的
	 */
	public ViewHolder setOnClickListener(int viewId,
			View.OnClickListener listener)
	{
		View view = getView(viewId);
		view.setOnClickListener(listener);
		return this;
	}

	public ViewHolder setOnTouchListener(int viewId,
			View.OnTouchListener listener)
	{
		View view = getView(viewId);
		view.setOnTouchListener(listener);
		return this;
	}

	public ViewHolder setOnLongClickListener(int viewId,
			View.OnLongClickListener listener)
	{
		View view = getView(viewId);
		view.setOnLongClickListener(listener);
		return this;
	}
}

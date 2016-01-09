package com.exmyth.wechat.indexable.view;

import com.exmyth.wechat.indexable.widget.IndexSroller;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListAdapter;
import android.widget.ListView;

public class IndexableListView extends ListView {

	private boolean mIsFastScrollEnabled;
	
	private IndexSroller mScroller = null;
	
	private GestureDetector mGestureDetector;

	public IndexableListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public IndexableListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public IndexableListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public boolean isFastScrollEnabled() {
		return mIsFastScrollEnabled;
	}
	
	@Override
	public void setFastScrollEnabled(boolean enabled) {
		mIsFastScrollEnabled = enabled;
		if(mIsFastScrollEnabled){
			if(mScroller == null){
				mScroller = new IndexSroller(getContext(),this);
			}
		}
		else{
			if(mScroller != null){
				mScroller.hide();
				mScroller = null;
			}
		}
	}
	
	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);
		
		if(mScroller != null){
			mScroller.draw(canvas);
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if(mScroller != null && mScroller.onTouchEvent(ev)){
			return true;
		}
		if(mGestureDetector == null){
			mGestureDetector = new GestureDetector(
					getContext(),
					new GestureDetector.SimpleOnGestureListener(){
						@Override
						public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
							mScroller.show();
							return super.onFling(e1, e2, velocityX, velocityY);
						}
					}); 
		}
		mGestureDetector.onTouchEvent(ev);
		return super.onTouchEvent(ev);
	}
	
	@Override
	public void setAdapter(ListAdapter adapter) {
		// TODO Auto-generated method stub
		super.setAdapter(adapter);
		if(mScroller != null){
			mScroller.setAdapter(adapter);
		}
	}
	
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if(mScroller != null){
			mScroller.onSizeChanged(w,h,oldw,oldh);
		}
	}
}

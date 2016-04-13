package com.exmyth.wechat.slidingview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class XRelativeLayout extends RelativeLayout {

	private boolean mIntercept = false;
	private int mHolderWidth = 120;

	public XRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mHolderWidth = Math.round(TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, mHolderWidth, getResources()
                        .getDisplayMetrics()));
	}

	public XRelativeLayout(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public XRelativeLayout(Context context) {
		this(context,null);
	}
	
	public void setIntercept(boolean mIntercept) {
		this.mIntercept = mIntercept;
//		invalidate();
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(mIntercept){
			float x = ev.getX();
			if(x<getWidth()-mHolderWidth){
				return true;
			}
			return super.onInterceptTouchEvent(ev);
		}
		else{
			return super.onInterceptTouchEvent(ev);
		}
	}
}

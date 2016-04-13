package com.exmyth.wechat.slidingview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.RelativeLayout;

public class XRelativeLayout extends RelativeLayout {

	private boolean mIntercept = false;

	public XRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
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
//			float x = ev.getX();
//			float y = ev.getY();
//			Log.d("exmyth", "x"+x+",y"+y);
//			if(x<getWidth()-SlideView.mHolderWidth){
				return true;
//			}
//			return super.onInterceptTouchEvent(ev);
		}
		else{
			return super.onInterceptTouchEvent(ev);
		}
	}
}

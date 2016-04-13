package com.exmyth.wechat.slidingview.view;

import com.exmyth.wechat.slidingview.view.SlideView.OnSlideListener;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

public class XRelativeLayout extends RelativeLayout {

	private boolean mIntercept = false;
	private int mHolderWidth = 120;
	private SlideView mLastSlideViewWithStatusOn;

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

	public void setLastSlideViewWithStatusOn(SlideView mLastSlideViewWithStatusOn) {
		this.mLastSlideViewWithStatusOn = mLastSlideViewWithStatusOn;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		if(mIntercept){
			float x = ev.getX();
			float y = ev.getY();
			int top = mLastSlideViewWithStatusOn.getTop();
			int bottom = mLastSlideViewWithStatusOn.getBottom();
			if((x>getWidth()-mHolderWidth)&&(top<y&&y<bottom)&&mLastSlideViewWithStatusOn.status == OnSlideListener.SLIDE_STATUS_ON){
				return super.onInterceptTouchEvent(ev);
			}
			return true;
		}
		else{
			return super.onInterceptTouchEvent(ev);
		}
	}
}

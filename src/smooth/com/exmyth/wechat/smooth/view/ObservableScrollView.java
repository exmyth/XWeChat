package com.exmyth.wechat.smooth.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

public class ObservableScrollView extends ScrollView {

	private OnScrollChangeListener onScrollChangeListener = null;

	private OnBorderListener onBorderListener = null;

	private boolean isBorderMonitor = false;

	public ObservableScrollView(Context context) {
		this(context, null);
		// TODO Auto-generated constructor stub
	}

	public ObservableScrollView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initObservableScrollView(context);
		// TODO Auto-generated constructor stub
	}

	public ObservableScrollView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initObservableScrollView(context);
		// TODO Auto-generated constructor stub
	}

	private void initObservableScrollView(Context context) {
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// requestDisallowInterceptTouchEvent(disallowIntercept);
		// disallowIntercept=true不让父元素拦截元素;disallowIntercept=false,没有拦截
//		getParent().requestDisallowInterceptTouchEvent(true);
		return super.dispatchTouchEvent(ev);
	}

	public void setOnScrollChangeListener(
			OnScrollChangeListener onScrollChangeListener) {
		this.onScrollChangeListener = onScrollChangeListener;
	}

	public void setOnBorderListener(OnBorderListener onBorderListener) {
		this.isBorderMonitor = true;
		this.onBorderListener = onBorderListener;
	}

	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// this.onScrollStart(l,t);
		super.onScrollChanged(l, t, oldl, oldt);

		if (onScrollChangeListener != null) {
			onScrollChangeListener.onScrollChanged(this, l, t, oldl, oldt);
		}
		if (isBorderMonitor) {
			if (0 == t && null != onBorderListener) {
				onBorderListener.onBottom();
			} else if ((getChildAt(0).getMeasuredHeight() <= t + getHeight())
					&& null != onBorderListener) {
				onBorderListener.onTop();
			}
		}
	}

	public interface OnScrollChangeListener {
		public void onScrollChanged(ObservableScrollView scrollView, int x,
				int y, int oldx, int oldy);
	}

	//此方法不好监控滑动暂停,改由onTouch事件的Handler处理,具体见ProductItemAdapter的ScrollViewTouchListener
//	public interface OnScrollStopListener {
//
//		public void onScrollStop(ObservableScrollView observableScrollView);
//	}

	/**
	 * OnBorderListener, Called when scroll to top or bottom
	 * 
	 * @author Myth 2013-5-22
	 */
	public static interface OnBorderListener {

		/**
		 * Called when scroll to bottom
		 */
		public void onBottom();

		/**
		 * Called when scroll to top
		 */
		public void onTop();
	}

	public void setBorderMonitor(boolean isBorderMonitor) {
		this.isBorderMonitor = isBorderMonitor;
	}
}

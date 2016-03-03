package com.exmyth.wechat.ripple.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

/**
 * 
 * 1. 如何得知用户点击了哪个元素 2. 如何取得被点击元素的信息 3. 如何通过layout进行重绘绘制水波纹 4. 如果延迟up事件的分发
 * 
 */
public class WaveView extends LinearLayout {
	private View mTargetTouchView;
	private Paint mHalfTransPaint;
	private Paint mTransPaint;
	private float[] mDownPositon;// 手指点击的坐标，也就是圆环的中心点
	private float rawRadius;// 原始的圆环半径
	private float drawedRadius;// 正在慢慢绘制的圆环半径
	private float drawingRadiusDegrees = 10;//慢慢绘制圆环的时候，半径的递增百分比
	private static final long INVALID_DURATION = 30;
	private static postUpEventDelayed delayedRunnable;
	public void init() {
		setOrientation(VERTICAL);
		mHalfTransPaint = new Paint();
		mHalfTransPaint.setColor(Color.parseColor("#55ffffff"));
		mHalfTransPaint.setAntiAlias(true);
		mTransPaint = new Paint();
		mTransPaint.setColor(Color.parseColor("#00ffffff"));
		mTransPaint.setAntiAlias(true);
		mDownPositon = new float[2];
		delayedRunnable = new postUpEventDelayed();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (ev.getAction() == MotionEvent.ACTION_DOWN) {
			mTargetTouchView = null;
			drawedRadius = 0;
			float x = ev.getRawX();
			float y = ev.getRawY();
			mTargetTouchView = findTargetView(x, y, this);
			if(mTargetTouchView!=null){
				Button msg = (Button) mTargetTouchView;
				RectF targetTouchRectF = getViewRectF(mTargetTouchView);
				mDownPositon = getCircleCenterPostion(x, y);
				// 所要绘制的圆环的中心点
				float circleCenterX = mDownPositon[0];
				float circleCenterY = mDownPositon[1];
				/**
				 * 圆环的半径： 圆环的中心点圆心当然是点击的那个点，但是半径是变化的
				 * 圆心可能落在mTargetTouchView区域的任意个方位之内，所以要想圆环绘制覆盖整个mTargetTouchView
				 * 则radius的取值为圆心的横坐标到mTargetTouchView的四个点的距离中的最大值
				 */
				float left = circleCenterX - targetTouchRectF.left;
				float right = targetTouchRectF.right - circleCenterX;
				float top = circleCenterY - targetTouchRectF.top;
				float bottom = targetTouchRectF.bottom - circleCenterY;
				// 计算出最大的值则为半径
				rawRadius = Math.max(bottom, Math.max(Math.max(left, right), top));
				postInvalidateDelayed(INVALID_DURATION);
			}
		}else if (ev.getAction() == MotionEvent.ACTION_UP) {
			// 需要让波纹绘制完毕后再执行在up中执行的方法
//			if(drawedRadius==0){
//				return false;
//			}
//			long totalTime = (long) (INVALID_DURATION * (drawingRadiusDegrees+5));
//			// 离波纹结束的时间
//			long time = (long) (totalTime - drawedRadius*totalTime / rawRadius);
			delayedRunnable.event = ev;
			return true;
		}
		return super.dispatchTouchEvent(ev);
	}

	class postUpEventDelayed implements Runnable{
		private MotionEvent event;
		@Override
		public void run() {
			if(mTargetTouchView!=null && mTargetTouchView.isClickable()
					&& event!=null){
				mTargetTouchView.dispatchTouchEvent(event);
			}
		}
	}
	
	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		/**
		 * 绘制完子元素后开始绘制波纹
		 */
		if (mTargetTouchView != null) {
			RectF clipRectF = clipRectF(mTargetTouchView);
			canvas.save();
			// 为了不让绘制的圆环超出所要绘制的范围
			canvas.clipRect(clipRectF);
			if(drawedRadius < rawRadius){
				drawedRadius += rawRadius / drawingRadiusDegrees;
				canvas.drawCircle(mDownPositon[0], mDownPositon[1], drawedRadius, mHalfTransPaint);
				postInvalidateDelayed(INVALID_DURATION);
			}else{
				canvas.drawCircle(mDownPositon[0], mDownPositon[1], rawRadius, mTransPaint);
				post(delayedRunnable);
			}
			canvas.restore();
		}
	}
	
	/**
	 * 获取圆环的中心坐标
	 */
	public float[] getCircleCenterPostion(float x,float y){
		int[] location = new int[2];
		float[] mDownPositon = new float[2];
		getLocationOnScreen(location );
		mDownPositon[0] = x;
		mDownPositon[1] = y -location[1];
		return mDownPositon;
	}
 
	/**
	 * 获取要剪切的区域
	 * @param mTargetTouchView
	 * @return
	 */
	public RectF clipRectF(View mTargetTouchView){
		RectF clipRectF = getViewRectF(mTargetTouchView);
		int[] location = new int[2];
		getLocationOnScreen(location);
		clipRectF.top -= location[1];
		clipRectF.bottom -=  location[1];
		return clipRectF;
	}
	
	/**
	 * 寻找目标view
	 * 
	 * @param x
	 * @param y
	 * @param anchorView
	 *            从哪个view开始往下寻找
	 * @return
	 */
	public View findTargetView(float x, float y, View anchorView) {
		ArrayList<View> touchablesView = anchorView.getTouchables();
		View targetView = null;
		for (View child : touchablesView) {
			RectF rectF = getViewRectF(child);
			if (rectF.contains(x, y) && child.isClickable()) {
				// 这说明被点击的view找到了
				targetView = child;
				break;
			}
		}
		return targetView;
	}

	public RectF getViewRectF(View view) {
		int[] location = new int[2];
		view.getLocationOnScreen(location);
		int childLeft = location[0];
		int childTop = location[1];
		int childRight = childLeft + view.getMeasuredWidth();
		int childBottom = childTop + view.getMeasuredHeight();
		return new RectF(childLeft, childTop, childRight, childBottom);
	}

	public WaveView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public WaveView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public WaveView(Context context) {
		this(context, null, 0);
	}

}

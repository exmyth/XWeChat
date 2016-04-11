package com.exmyth.wechat.zoom.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.ImageView;

/**
 * 
 * @author Administrator
 * 
 */
public class MeasureImageView extends ImageView implements OnGlobalLayoutListener{
	/**
	 * 值进行一次的图片缩放处理
	 */
	private boolean mOnce;

	/**
	 * 初始化时缩放的值
	 */
	private float mInitScale;
	/**
	 * 双击放大达到的值
	 */
	private float mMidScale; // 缩放的值在mInitScale和mMaxScale之间
	/**
	 * 放大的极限
	 */
	private float mMaxScale;

	/**
	 * 控制缩放位移的矩阵
	 */
	private Matrix mScaleMatrix;

	public MeasureImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		// init
		mScaleMatrix = new Matrix();
		setScaleType(ScaleType.MATRIX);
	}

	public MeasureImageView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public MeasureImageView(Context context) {
		this(context, null);
	}

	/**
	 * 当onAttachedToWindow
	 */
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		getViewTreeObserver().addOnGlobalLayoutListener(this);
	}

	/**
	 * 当onDetachedFromWindow
	 */
	@SuppressWarnings("deprecation")
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		getViewTreeObserver().removeGlobalOnLayoutListener(this);
	}

	/**
	 * 全局的布局加载完成后，调用此方法 。 获取ImageView加载完成的图片,使图片居中缩放
	 */
	@Override
	public void onGlobalLayout() {
		if (!mOnce) {
			// 得到图片的宽高
			int width = getWidth();
			int height = getHeight();
			// 得到图片，以及宽和高
			Drawable d = getDrawable();
			if (d == null)
				return;
			// 拉伸后的宽度.而不是真正图片的宽度
			int dw = d.getIntrinsicWidth();
			int dh = d.getIntrinsicHeight();

			Log.d("Debug", "width:" + width + ",height:" + height + ",dw:" + dw
					+ ",dh:" + dh);

			float scale = 1.0f;// 默认缩放的值

			// 将图片的宽高和控件的宽高作对比，如果图片比较小，则将图片放大，反之亦然。
			// 如果图片的宽度大于控件的宽度,并且图片高度小于控件高度
			if (dw > width && dh < height) {
				scale = width * 1.0f / dw;// 图片太宽，宽度缩放
				Log.d("Debug", "图片宽大，高小");
			}

			if (dh > height && dw < width) {
				scale = height * 1.0f / dh;// 图片太高，高度缩放
				Log.d("Debug", "图片高大，宽小");
			}
			// // 如果宽高都大于控件宽高??
			// if (dw > width && dh > height) {
			// scale = Math.min(width * 1.0f / dw, height * 1.0f / dw);
			// }
			// // 图片宽高都小于控件的宽高 上下两个一样
			// if (dw < width && dh < height) {
			// scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
			// }

			if (dw < width && dh < height || dw > width && dh > height) {
				scale = Math.min(width * 1.0f / dw, height * 1.0f / dh);
				Log.d("Debug", "图片宽高都大，或都小");
			}

			// 得到初始化缩放的比例
			mInitScale = scale;// 原大小
			mMaxScale = mInitScale * 4;// 4倍
			mMidScale = mInitScale * 2;// 2倍，双击放大达到的值

			// 将图片移动到控件的中心
			int dx = getWidth() / 2 - dw / 2;// 向x轴移动dx距离
			int dy = getHeight() / 2 - dh / 2;// 向y轴移动dx距离

			/**
			 * matrix: xScale xSkew xTrans 需要9个 ySkew yScale yTrans 0 0 0
			 */
			mScaleMatrix.postTranslate(dx, dy);// 平移
			mScaleMatrix.postScale(mInitScale, mInitScale, width / 2,
					height / 2);// 缩放,正常显示width/2,height/2中心
			setImageMatrix(mScaleMatrix);

			mOnce = true;
		}
	}
}

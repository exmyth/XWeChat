 package com.exmyth.wechat.smooth.view;

import com.exmyth.wechat.R;
import com.exmyth.wechat.smooth.util.DimensionUtil;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

public class MultiLineTextView extends View {

	private Context mContext;
	private int mTextColor = 0xFF999999;
	private String[] mText = new String[]{""};
	private float mTextSize;
	private float mTextHOffset;
	private float mItemHeight;
	
	private Paint mTextPaint;
//	private Rect mTextBound;
	private boolean isMeasure = false;
	private LayoutParams layoutParams;
//	private float ascent;

	public MultiLineTextView(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
	}

	public MultiLineTextView(Context context) {
		this(context,null);
	}
	
	public MultiLineTextView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		this.mContext = context;
		TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MultiLineTextView);
		int indexCount = ta.getIndexCount();
		for(int i = 0; i < indexCount; i++){
			int index = ta.getIndex(i);
			switch (index) {
			case R.styleable.MultiLineTextView_text:
				String text = ta.getString(index);
				mText = new String[]{text};
				break;
			case R.styleable.MultiLineTextView_textColor:
				mTextColor = ta.getColor(index, 0xFF999999);
				break;
			case R.styleable.MultiLineTextView_itemHeight:
				mItemHeight = ta.getDimension(index, 
						DimensionUtil.dip2px(context, 35));
				break;
			case R.styleable.MultiLineTextView_textHOffset:
				mTextHOffset = ta.getDimension(index, 
						DimensionUtil.dip2px(context, -1));
				break;
			case R.styleable.MultiLineTextView_textSize:
				mTextSize = ta.getDimension(index, 
						DimensionUtil.sp2px(context, 14));
				break;
			default:
				break;
			}
		}
		ta.recycle();
		
		
//		mTextBound = new Rect();
		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setTextSize(mTextSize);
		mTextPaint.setColor(mTextColor);
		mTextPaint.setAntiAlias(true);
		mTextPaint.setDither(true);
		mTextPaint.setAlpha(255);
		if(null == layoutParams){
			layoutParams = getLayoutParams();
		}
//		FontMetrics fontMetrics = mTextPaint.getFontMetrics();
//		float top = fontMetrics.top;
//		float bottom = fontMetrics.bottom;
//		ascent = fontMetrics.ascent;
//		float descent = fontMetrics.descent;
//		float leading = fontMetrics.leading;
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		if(!isMeasure){
			if(null != mText){
				if(null == layoutParams){
					layoutParams = getLayoutParams();
				}
				layoutParams.height = (int) (mText.length * mItemHeight);
			}
			isMeasure = true;
		}
		
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for(int i = 0; i < mText.length; i++){
//			if(mText.length > 0){
//				mTextPaint.getTextBounds(mText[i], 0, mText[i].length(), mTextBound);
//			}
			canvas.save();
//			canvas.translate(0, 80*i);
			//+(float)Math.floor(ascent)
			if(mTextHOffset < 0){
				// 下面这行是实现水平居中，drawText对应改为传入targetRect.centerX()  
				mTextPaint.setTextAlign(Paint.Align.CENTER);
				mTextHOffset = DimensionUtil.width/2;
			}
			canvas.drawText(mText[i], mTextHOffset, mItemHeight*i+(mItemHeight-mTextSize)/2+mTextSize, mTextPaint);
			canvas.restore();
		}
	}

	public void setText(String[] text) {
		if(this.mText.length != text.length){
			if(null == layoutParams){
				layoutParams = getLayoutParams();
			}
			layoutParams.height = (int) (text.length * mItemHeight);
			this.setLayoutParams(layoutParams);
		}
		this.mText = text;
		invalidateView();
	}

	private void invalidateView() {
		if(Looper.getMainLooper() == Looper.myLooper()){
			invalidate();
		}
		else{
			postInvalidate();
		}
	}
}

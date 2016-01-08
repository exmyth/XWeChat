package com.exmyth.wechat.view;

import com.exmyth.wechat.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class NavigationBar extends RelativeLayout {
	
	public class ViewOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if(listener != null){
				if(v == btnLeft){
					listener.onLeftClick(btnLeft);
				}
				else if(v == btnRight){
					listener.onRightClick(btnRight);
				}
			}
		}
	}

	private Button btnLeft;
	private Button btnRight;
	private TextView txtTitle;
	
	private int leftTextColor;
	private Drawable leftBackground;
	private String leftText;
	
	private int rightTextColor;
	private Drawable rightBackground;
	private String rightText;
	
	private float titleTextSize;
	private int titleTextColor;
	private String titleText;
	
	private LayoutParams paramLeft,paramRight,paramTitle;
	
	private NavigationBarClickListener listener;
	
	public interface NavigationBarClickListener{
		public void onLeftClick(View v);
		public void onRightClick(View ov);
	}

	public NavigationBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initAttribute(context, attrs);
		initView(context);
		initLayoutParam();
		
		btnLeft.setOnClickListener(new ViewOnClickListener());
		btnRight.setOnClickListener(new ViewOnClickListener());
		setBackgroundColor(0xFFF59563);
	}

	private void initLayoutParam() {
		paramLeft = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		paramLeft.addRule(RelativeLayout.ALIGN_PARENT_LEFT,RelativeLayout.TRUE);
		addView(btnLeft,paramLeft);
		
		paramRight = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		paramRight.addRule(RelativeLayout.ALIGN_PARENT_RIGHT,RelativeLayout.TRUE);
		addView(btnRight,paramRight);
		
		paramTitle = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
		paramTitle.addRule(RelativeLayout.CENTER_IN_PARENT,RelativeLayout.TRUE);
		addView(txtTitle,paramTitle);
	}

	@SuppressLint("NewApi")
	private void initView(Context context) {
		btnLeft = new Button(context);
		btnRight = new Button(context);
		txtTitle = new TextView(context);
		
		btnLeft.setTextColor(leftTextColor);
		btnLeft.setBackground(leftBackground);
		btnLeft.setText(leftText);
		
		btnRight.setTextColor(rightTextColor);
		btnRight.setBackground(rightBackground);
		btnRight.setText(rightText);
		
		txtTitle.setTextColor(titleTextColor);
		txtTitle.setTextSize(titleTextSize);
		txtTitle.setText(titleText);
		txtTitle.setGravity(Gravity.CENTER);
	}

	private void initAttribute(Context context, AttributeSet attrs) {
		TypedArray ta = context.obtainStyledAttributes(attrs,R.styleable.NavigationBar);
		leftTextColor = ta.getColor(R.styleable.NavigationBar_leftTextColor, 0);
		leftBackground = ta.getDrawable(R.styleable.NavigationBar_leftBackground);
		leftText= ta.getString(R.styleable.NavigationBar_leftText);
		
		rightTextColor = ta.getColor(R.styleable.NavigationBar_rightTextColor, 0);
		rightBackground = ta.getDrawable(R.styleable.NavigationBar_rightBackground);
		rightText= ta.getString(R.styleable.NavigationBar_rightText);
		
		titleTextSize = ta.getDimension(R.styleable.NavigationBar_titleTextSize, 0);
		titleTextColor = ta.getColor(R.styleable.NavigationBar_titleTextColor, 0);
		titleText = ta.getString(R.styleable.NavigationBar_titleText);
		ta.recycle();
	}

	public NavigationBar(Context context, AttributeSet attrs) {
		this(context, attrs,0);
	}

	public NavigationBar(Context context) {
		this(context,null);
	}

	public void setClickListener(NavigationBarClickListener listener) {
		this.listener = listener;
	}
	
	public void setLeftVisibility(boolean b){
		if(b){
			btnLeft.setVisibility(View.VISIBLE);
		}
		else{
			btnLeft.setVisibility(View.GONE);
		}
	}
}


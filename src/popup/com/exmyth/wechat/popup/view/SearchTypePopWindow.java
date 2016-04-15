package com.exmyth.wechat.popup.view;

import com.exmyth.wechat.R;
import com.exmyth.wechat.popup.util.DimensionUtil;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

/**
 * 自定义popupWindow
 * 
 * @author wwj
 * 
 * 
 */
public class SearchTypePopWindow extends PopupWindow {
	private static final CharSequence SEARCH_TYPE_CONSUMER_NAME = "用户名称";
	private static final CharSequence SEARCH_TYPE_PRODUCT_NAME = "产品名称";
	private static final CharSequence SEARCH_TYPE_CONSUMER_MOBILE = "用户手机";
	private View conentView;
	private Button targetButton;
	private PopWindowInterface popWindowInterface;
	protected int value;
	private LinearLayout lytConsumerName;
	private LinearLayout lytProductName;
	private LinearLayout lytConsumerMobile;
	private Activity activity;

	public SearchTypePopWindow(final Activity context,Button targetButton,PopWindowInterface popWindowInterface) {
		this.targetButton = targetButton;
		this.popWindowInterface = popWindowInterface;
		this.activity = context;
		init(context);
	}

	private void init(final Activity context) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		conentView = inflater.inflate(R.layout.win_sale_list_search_type, null);
//		int h = context.getWindowManager().getDefaultDisplay().getHeight();
//		int w = context.getWindowManager().getDefaultDisplay().getWidth();
		// 设置SelectPicPopupWindow的View
		this.setContentView(conentView);
		// 设置SelectPicPopupWindow弹出窗体的宽
		this.setWidth((int) DimensionUtil.dip2px(context, 139));
		// 设置SelectPicPopupWindow弹出窗体的高
		this.setHeight(LayoutParams.WRAP_CONTENT);
		// 设置SelectPicPopupWindow弹出窗体可点击
		this.setFocusable(true);
		this.setOutsideTouchable(true);
		// 刷新状态
		this.update();
		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0000000000);
		// 点back键和其他地方使其消失,设置了这个才能触发OnDismisslistener ，设置其他控件变化等操作
		this.setBackgroundDrawable(dw);
		// mPopupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
		// 设置SelectPicPopupWindow弹出窗体动画效果
//		this.setAnimationStyle(R.style.AnimationPreview);
		
		value = 0;
		targetButton.setText(SEARCH_TYPE_CONSUMER_NAME);
		
		lytConsumerName = (LinearLayout) conentView.findViewById(R.id.win_sale_list_search_type_consumerName);
		lytProductName = (LinearLayout) conentView.findViewById(R.id.win_sale_list_search_type_productName);
		lytConsumerMobile = (LinearLayout) conentView.findViewById(R.id.win_sale_list_search_type_consumerMobile);
		lytConsumerName.setOnClickListener(new OnViewClickListener());
		lytProductName.setOnClickListener(new OnViewClickListener());
		lytConsumerMobile.setOnClickListener(new OnViewClickListener());
		
		activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
	}
	
	/** 
     * 设置添加屏幕的背景透明度 
     * @param bgAlpha 
     */  
	public void backgroundAlpha(float bgAlpha)  
    {  
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0  
        activity.getWindow().setAttributes(lp);  
    }
	
	private class OnViewClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.win_sale_list_search_type_consumerName:
				value = 0;
				targetButton.setText(SEARCH_TYPE_CONSUMER_NAME);//用户名称
				break;
				
			case R.id.win_sale_list_search_type_productName:
				value = 1;
				targetButton.setText(SEARCH_TYPE_PRODUCT_NAME);//产品名称
				break;
				
			case R.id.win_sale_list_search_type_consumerMobile:
				value = 2;
				targetButton.setText(SEARCH_TYPE_CONSUMER_MOBILE);//用户手机
				break;

			default:
				break;
			}
			dismiss();
		}
	}
	
	public int getValue() {
		return value;
	}

	protected void focus(View view) {
		view.setFocusable(true);
		view.setFocusableInTouchMode(true);
		view.requestFocus();
		view.requestFocusFromTouch();
	}
	
	protected void defocus(View view) {
		view.setFocusable(false);
		view.setFocusableInTouchMode(false);
	}
	
	public void setFocus (int value){
		switch (value) {
		case 0:
			focus(lytConsumerName);
			defocus(lytProductName);
			defocus(lytConsumerMobile);
			break;
		case 1:
			focus(lytProductName);
			defocus(lytConsumerName);
			defocus(lytConsumerMobile);
			break;
		case 2:
			focus(lytConsumerMobile);
			defocus(lytConsumerName);
			defocus(lytProductName);
			break;
		default:
			break;
		}
	}
	
	@Override
	public void dismiss() {
		super.dismiss();
		backgroundAlpha(1.0f);
		popWindowInterface.updateDataWhenUnitChanged(value);
	}
	
	public interface PopWindowInterface {
		public void updateDataWhenUnitChanged(int value);
	}

	/**
	 * 显示popupWindow
	 * 
	 * @param parent
	 */
	public void showPopupWindow(View parent) {
		if (!this.isShowing()) {
			// 以下拉方式显示popupwindow
//			this.showAsDropDown(parent, parent.getLayoutParams().width / 2, 0);
			this.showAsDropDown(parent, 0, 0);
			setFocus(value);
			backgroundAlpha(0.5f);
//			this.showAtLocation(parent,Gravity.BOTTOM, 0, 0);
		} else {
			this.dismiss();
		}
	}
}

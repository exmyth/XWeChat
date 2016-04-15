package com.exmyth.wechat.popup.activity;

import com.exmyth.wechat.R;
import com.exmyth.wechat.popup.view.SearchTypePopWindow;
import com.exmyth.wechat.popup.view.SearchTypePopWindow.PopWindowInterface;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class PopupActivity extends Activity implements OnClickListener{
	private Button btnpopup;
	private int mSearchType;
	private SearchTypePopWindow popWin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.popup_activity_main);
		initView();
	}

	private void initView() {
		btnpopup = (Button)findViewById(R.id.btnpopup);
		btnpopup.setOnClickListener(this);
		popWin = new SearchTypePopWindow(PopupActivity.this, btnpopup,new PopWindowInterface() {
			

			@Override
			public void updateDataWhenUnitChanged(int value) {
				mSearchType = value;
//				mEdtKeyword.setText("");
				if(2 == mSearchType){
//					mEdtKeyword.setInputType(InputType.TYPE_CLASS_NUMBER);
//					mEdtKeyword.setMaxEms(11);
//					mEdtKeyword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
				}
				else{
//					mEdtKeyword.setInputType(InputType.TYPE_CLASS_TEXT);
//					mEdtKeyword.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
//					mEdtKeyword.setMaxEms(15);
				}
			}
		});
		
	}

	@Override
	public void onClick(View v) {
		popWin.showPopupWindow(v);
	}
}

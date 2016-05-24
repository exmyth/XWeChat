package com.exmyth.wechat.smooth.activity;

import com.exmyth.wechat.R;
import com.exmyth.wechat.smooth.view.MultiLineTextView;

import android.app.Activity;
import android.os.Bundle;

public class ScrollViewTest extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.smooth_activity_main);
		MultiLineTextView txt1 = (MultiLineTextView)findViewById(R.id.fragment_product_detail_entry_combo_txtEnums1);
		MultiLineTextView txt2 = (MultiLineTextView)findViewById(R.id.fragment_product_detail_entry_combo_txtEnums2);
		MultiLineTextView txt3 = (MultiLineTextView)findViewById(R.id.fragment_product_detail_entry_combo_txtEnums3);
		txt1.setText(new String[]{"1","2","ABC",""});
		txt2.setText(new String[]{"1","2","ABC",""});
		txt3.setText(new String[]{"1","2","ABC",""});
	}
}

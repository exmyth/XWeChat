package com.exmyth.wechat.scrollerview;

import com.exmyth.wechat.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private TextView tv;
	private Button bt_scrollLeft;
	private Button bt_scrollRight;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.scrollview_main);

		tv = (TextView) findViewById(R.id.tv_scroll);

		bt_scrollLeft = (Button) findViewById(R.id.bt_scrollLeft);
		bt_scrollRight = (Button) findViewById(R.id.bt_scrollRight);

		bt_scrollLeft.setOnClickListener(this);
		bt_scrollRight.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		switch (v.getId()) {
		case R.id.bt_scrollLeft:
			tv.scrollBy(20, 0);
			
			int tvscrllX = tv.getScrollX();
			int tvscrllY = tv.getScrollY();
			System.out.println( " tvscrllX ---> " + tvscrllX + " --- tvscrllY ---> "+tvscrllY);
			bt_scrollLeft.scrollBy(20, 0);
			break;
		case R.id.bt_scrollRight:
			tv.scrollTo(-100, 0);

			int tvscrllXx = tv.getScrollX();
			int tvscrllYx = tv.getScrollY();
			System.out.println( " tvscrllX ---> " + tvscrllXx + " --- tvscrllY ---> "+tvscrllYx);
			break;
		}

	}

}

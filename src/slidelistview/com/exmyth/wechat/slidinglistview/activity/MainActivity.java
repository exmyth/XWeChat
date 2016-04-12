package com.exmyth.wechat.slidinglistview.activity;

import com.exmyth.wechat.R;
import com.exmyth.wechat.slidinglistview.view.SlideListView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class MainActivity extends Activity {

	private SlideListView slideListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.slidinglistview_activity_main);
		slideListView = (SlideListView)findViewById(R.id.slv_list);
		slideListView.initSlideMode(SlideListView.MOD_RIGHT);
		String[] arr = new String[]{"黄家驹","黄贯中","叶世荣","黄家强"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, arr);
		slideListView.setAdapter(adapter);
		
		/*
		List<Map<String,Object>> mData= new ArrayList<Map<String,Object>>();;  
		for(int i =0; i < mData.size(); i++) {      
		    Map<String,Object> item = new HashMap<String,Object>();      
		    item.put("image", 0);      
		    item.put("title", "title");      
		    item.put("text", "text");      
		    mData.add(item);       
		}      
		SimpleAdapter sAdapter = new SimpleAdapter(  
		                this,  
		                mData,  
		                R.layout.item,      
		                new String[]{"image","title","text"},  
		                new int[]{R.id.image,R.id.title,R.id.text});
		*/
		
	}
}

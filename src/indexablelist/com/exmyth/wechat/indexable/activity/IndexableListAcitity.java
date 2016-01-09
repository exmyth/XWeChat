package com.exmyth.wechat.indexable.activity;

import java.util.ArrayList;
import java.util.Collections;

import com.exmyth.wechat.R;
import com.exmyth.wechat.indexable.adapter.ContentAdapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.ListView;

public class IndexableListAcitity extends Activity{
	 private ArrayList<String> mItems;
	private Context context;
	private ListView mListView;
	private ContentAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.indexable_list_activity_main);
		context = this;
		initData();
		adapter = new ContentAdapter(context, android.R.layout.simple_list_item_1, mItems);
		initView();
	}

	private void initView() {
		mListView = (ListView)findViewById(R.id.id_list);
		mListView.setAdapter(adapter);
		mListView.setFastScrollEnabled(true);
	}

	private void initData() {
		mItems = new ArrayList<String>();
		
		mItems.add("123456");
		mItems.add("A 123456");
		mItems.add("B 123456");
		mItems.add("C 123456");
		mItems.add("D 123456");
		mItems.add("E 123456");
		mItems.add("F 123456");
		mItems.add("G 123456");
		mItems.add("O 123456");
		mItems.add("P 123456");
		mItems.add("Q 123456");
		mItems.add("X 123456");
		mItems.add("Y 123456");
		mItems.add("Z 123456");
		
		Collections.sort(mItems);
	}
}

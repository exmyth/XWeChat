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
		initView();
	}

	private void initView() {
		adapter = new ContentAdapter(context, android.R.layout.simple_list_item_1, mItems);
		mListView = (ListView)findViewById(R.id.id_list);
		mListView.setAdapter(adapter);
		mListView.setFastScrollEnabled(true);
	}

	private void initData() {
		mItems = new ArrayList<String>();
		
		mItems.add("Diary of a Wimpy Kid 6: Cabin Fever");
        mItems.add("Steve Jobs");
        mItems.add("Inheritance (The Inheritance Cycle)");
        mItems.add("11/22/63: A Novel");
        mItems.add("The Hunger Games");
        mItems.add("The LEGO Ideas Book");
        mItems.add("Explosive Eighteen: A Stephanie Plum Novel");
        mItems.add("Catching Fire (The Second Book of the Hunger Games)");
        mItems.add("Elder Scrolls V: Skyrim: Prima Official Game Guide");
        mItems.add("Death Comes to Pemberley");
        mItems.add("Diary of a Wimpy Kid 6: Cabin Fever");
        mItems.add("Steve Jobs");
        mItems.add("Inheritance (The Inheritance Cycle)");
        mItems.add("11/22/63: A Novel");
        mItems.add("The Hunger Games");
        mItems.add("The LEGO Ideas Book");
        mItems.add("Explosive Eighteen: A Stephanie Plum Novel");
        mItems.add("Catching Fire (The Second Book of the Hunger Games)");
        mItems.add("Elder Scrolls V: Skyrim: Prima Official Game Guide");
        mItems.add("Death Comes to Pemberley");
		
		Collections.sort(mItems);
	}
}

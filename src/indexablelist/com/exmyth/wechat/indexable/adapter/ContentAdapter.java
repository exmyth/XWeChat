package com.exmyth.wechat.indexable.adapter;

import java.util.List;

import com.exmyth.wechat.indexable.util.StringMatcher;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.SectionIndexer;

public class ContentAdapter extends ArrayAdapter<String> implements SectionIndexer {

	public ContentAdapter(Context context, int textViewResourceId, List<String> objects) {
		super(context, textViewResourceId, objects);
	}

	private String mSection = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	@Override
	public Object[] getSections() {
		String[] sections = new String[mSection.length()];
		for(int i = 0; i < mSection.length(); i++){
			sections[i] = String.valueOf(mSection.charAt(i));
		}
		return sections;
	}

	@Override
	public int getPositionForSection(int section) {
		for(int i = section; i >=0; i++){
			for(int j = 0; j < getCount(); j++){
				if(i == 0){//查询数字
					for(int k = 0; k <10; k++){
						if(StringMatcher.match(String.valueOf(getItem(j).charAt(0)),String.valueOf(k))){
							return j;
						}
					}
				}
				else{//查询字母
					if(StringMatcher.match(String.valueOf(getItem(j).charAt(0)),
							String.valueOf(mSection.charAt(i)))){
						return j;
					}
				}
			}
		}
		return 0;
	}

	@Override
	public int getSectionForPosition(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

}

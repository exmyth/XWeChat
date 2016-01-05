package com.exmyth.wechat.previous.activity;

import com.exmyth.wechat.R;
import com.exmyth.wechat.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ChatMainTabFragment extends Fragment
{
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.previous_tab01, container, false);
	}
}

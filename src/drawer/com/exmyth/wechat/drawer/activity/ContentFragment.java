package com.exmyth.wechat.drawer.activity;

import com.exmyth.wechat.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by guest on 16/1/4.
 */
public class ContentFragment extends Fragment {


    private TextView textView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       View view =  inflater.inflate(R.layout.drawaer_activity_main_fragment, container, false);
        textView = (TextView) view.findViewById(R.id.textView);
        String arg = (String) getArguments().getString("text");
        textView.setText(arg);
        return view;


    }
}

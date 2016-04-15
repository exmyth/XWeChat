package com.exmyth.wechat.popup.activity;

import java.util.ArrayList;
import java.util.List;

import com.exmyth.wechat.R;

import android.app.Activity;
import android.graphics.drawable.PaintDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

public class MainActivity1 extends Activity {
	private PopupWindow popWin;
	private List<String> strs = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popup_activity_main1);
        
        //为ListView添加数据
        for(int i =0; i < 10; i++){
        	strs.add("item " + i);
        }
        
        popWin = new PopupWindow(this);
        //获取popWin要显示的布局
		View contentView = LayoutInflater.from(this).inflate(R.layout.pop_win_layout, null);
		ListView listView = (ListView) contentView.findViewById(R.id.pop_win_listview);
		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strs));
		//为popWin设置布局
		popWin.setContentView(contentView);
		//不指定popWin的大小就无法显示popWin
		popWin.setWidth(300);
		popWin.setHeight(500);
		//加上下面三句就可以在popWin外点击让popWin消失，同时popWin内也可以获取焦点
		popWin.setFocusable(true);
		popWin.setOutsideTouchable(true);
		popWin.setBackgroundDrawable(new PaintDrawable());
		//指定popWin显示和消失的动画
		popWin.setAnimationStyle(R.style.PopWinAnimation);
    }
    
    //点击按钮显示PopupWindow
    public void btnOnClick(View view){
    	//显示popWin，在按钮的正下方
    	popWin.showAsDropDown(view);
//    	popWin.showAtLocation(view, Gravity.CENTER, 0, 10);
    }

}

package com.exmyth.wechat.popup.fragment;  
  
import com.exmyth.wechat.R;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;  
  
public class ContentFragment extends Fragment  
{  
  
    private Button start;

	@Override  
    public View onCreateView(LayoutInflater inflater, ViewGroup container,  
            Bundle savedInstanceState)  
    {  
        return inflater.inflate(R.layout.fragment_content, container, false);  
    }
    
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
    	super.onViewCreated(view, savedInstanceState);
    	start = (Button) view.findViewById(R.id.start);
		start.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showPopwindow();
			}

		});
    }
    
    /**
	 * 显示popupWindow
	 */
	private void showPopwindow() {
		// 利用layoutInflater获得View
		LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.popwindowlayout, null);

		// 下面是两种方法得到宽度和高度 getWindow().getDecorView().getWidth()

		PopupWindow window = new PopupWindow(view,
				WindowManager.LayoutParams.MATCH_PARENT,
				WindowManager.LayoutParams.WRAP_CONTENT);

		// 设置popWindow弹出窗体可点击，这句话必须添加，并且是true
		window.setFocusable(true);
		window.setOutsideTouchable(true);

		// 必须要给调用这个方法，否则点击popWindow以外部分，popWindow不会消失
		// window.setBackgroundDrawable(new BitmapDrawable());

		// 实例化一个ColorDrawable颜色为半透明
		ColorDrawable dw = new ColorDrawable(0xb0000000);
		window.setBackgroundDrawable(dw);

		// 在参照的View控件下方显示
		// window.showAsDropDown(MainActivity.this.findViewById(R.id.start));

		// 设置popWindow的显示和消失动画
		window.setAnimationStyle(R.style.mypopwindow_anim_style);
		// 在底部显示
		window.showAsDropDown(start, 0, 0);
		
		//产生背景变暗效果
	    WindowManager.LayoutParams lp=getActivity().getWindow().getAttributes();
	    lp.alpha = 0.4f;
	    getActivity().getWindow().setAttributes(lp);

		// 这里检验popWindow里的button是否可以点击
		Button first = (Button) view.findViewById(R.id.first);
		first.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				System.out.println("第一个按钮被点击了");
			}
		});
		
		// popWindow消失监听方法
		window.setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss() {
				System.out.println("popWindow消失");
				WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
				lp.alpha = 1f;
				getActivity().getWindow().setAttributes(lp);
			}
		});

	}
  
} 
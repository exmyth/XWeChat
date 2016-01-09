package com.exmyth.wechat.base;

import com.exmyth.wechat.app.AppManager;
import com.exmyth.wechat.itf.BaseViewInterface;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import butterknife.ButterKnife;

public abstract class BaseActivity extends Activity implements BaseViewInterface {
	protected LayoutInflater mInflater;
//    protected ActionBar mActionBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AppManager.getAppManager().addActivity(this);
		onBeforeSetContentLayout();
		if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }
		mInflater = getLayoutInflater();
		/*
		if (hasActionBar()) {
			mActionBar = getSupportActionBar();
            initActionBar(mActionBar);
        }
		else{
			// supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
		}
		*/
		// 通过注解绑定控件
        init(savedInstanceState);
        ButterKnife.inject(this);
		initView();
        initData();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		ButterKnife.reset(this);
	}
	protected void onBeforeSetContentLayout(){
		
	}
	protected int getLayoutId() {
        return 0;
    }
	protected void init(Bundle savedInstanceState) {
	}
	/*
	protected boolean hasBackButton() {
        return false;
    }
	protected int getActionBarCustomView() {
        return 0;
    }
	protected boolean hasActionBar() {
		return true;
	}
	protected void initActionBar(ActionBar actionBar) {
		
	}
	*/
}

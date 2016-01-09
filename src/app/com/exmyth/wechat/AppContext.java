package com.exmyth.wechat;

import com.exmyth.wechat.base.BaseApplication;

/**
 * 全局应用程序类：用于保存和调用全局应用配置及访问网络数据
 * 
 * @author 火蚁 (http://my.oschina.net/LittleDY)
 * @version 1.0
 * @created 2014-04-22
 */
public class AppContext extends BaseApplication {
	/**
	 * 获得当前app运行的AppContext
	 * 
	 * @return
	 */
	private static AppContext instance;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		instance = this;
	}

	public static AppContext getInstance() {
		return instance;
	}
}

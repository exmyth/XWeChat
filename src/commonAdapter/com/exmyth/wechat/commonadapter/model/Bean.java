/*
 * @Title Bean.java
 * @Copyright Copyright 2010-2015 Yann Software Co,.Ltd All Rights Reserved.
 * @Description：
 * @author Yann
 * @date 2015-8-5 下午10:04:35
 * @version 1.0
 */
package com.exmyth.wechat.commonadapter.model;

/** 
 * 类注释
 * @author Yann
 * @date 2015-8-5 下午10:04:35
 */
public class Bean
{
	private String title;
	private String desc;
	private String time;
	private String phone;
	private boolean isChecked;
	
	public boolean isChecked()
	{
		return isChecked;
	}

	public void setChecked(boolean isChecked)
	{
		this.isChecked = isChecked;
	}
	
	/** 
	 *@param title
	 *@param desc
	 *@param time
	 *@param phone
	 */
	public Bean(String title, String desc, String time, String phone)
	{
		this.title = title;
		this.desc = desc;
		this.time = time;
		this.phone = phone;
	}
	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getDesc()
	{
		return desc;
	}
	public void setDesc(String desc)
	{
		this.desc = desc;
	}
	public String getTime()
	{
		return time;
	}
	public void setTime(String time)
	{
		this.time = time;
	}
	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	
	
}

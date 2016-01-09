package com.exmyth.wechat.indexable.util;

import com.exmyth.wechat.util.StringUtils;

public class StringMatcher {

	public static boolean match(String value, String keyword) {
		if(StringUtils.isEmpty(value) || StringUtils.isEmpty(keyword)){
			return false;
		}
		if(keyword.length() > value.length()){
			return false;
		}
		int i = 0;//value pointer
		int j = 0;//keyword pointer
		do {
			if(value.charAt(i) == keyword.charAt(j)){
				i++;
				j++;
			}
			else if(j > 0){
				break;
			}
			else{
				i++;
			}
		} while (i < value.length()&& j < keyword.length());
		return j == keyword.length();
	}
}

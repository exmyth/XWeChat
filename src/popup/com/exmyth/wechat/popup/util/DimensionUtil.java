package com.exmyth.wechat.popup.util;

import android.content.Context;
import android.util.TypedValue;

public class DimensionUtil {
	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变
	 * 
	 * @param context
	 * @param dipValue
	 * @return
	 */
	public static float dip2px(Context context, float dipValue) {
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dipValue, context.getResources().getDisplayMetrics());
		return px;
	}
	
	public static float sp2px(Context context, float dipValue) {
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
				dipValue, context.getResources().getDisplayMetrics());
		return px;
	}
}

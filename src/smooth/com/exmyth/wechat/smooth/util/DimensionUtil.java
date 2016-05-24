package com.exmyth.wechat.smooth.util;

import android.content.Context;
import android.util.TypedValue;

public class DimensionUtil {
	
	public static int width = 0;
	public static int height = 0;
	public static int densityDpi = 0;
	public static float density = 0F;

	private DimensionUtil(){
		
	}
	
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

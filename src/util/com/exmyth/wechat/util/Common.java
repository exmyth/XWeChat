package com.exmyth.wechat.util;
import java.io.File;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
public class Common {
	private static final String VERSION_NAME = "VERSION_NAME";
	private static final String VERSION_CODE = "VERSION_CODE";
	private static final String TAG = Common.class.getSimpleName();
	/**
	 * 拨打电话
	 * 
	 * @param context
	 * @param phoneNumber
	 */
	public static void call(Context context, String phoneNumber) {
		context.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNumber)));
	}
	/**
	 * 跳转至拨号界面
	 * 
	 * @param context
	 * @param phoneNumber
	 */
	public static void callDial(Context context, String phoneNumber) {
		context.startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNumber)));
	}
	/**
	 * 发送短信
	 * 
	 * @param context
	 * @param phoneNumber
	 * @param content
	 */
	public static void sendSms(Context context, String phoneNumber,String content) {
		Uri uri = Uri.parse("smsto:"+ (TextUtils.isEmpty(phoneNumber) ? "" : phoneNumber));
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		intent.putExtra("sms_body", TextUtils.isEmpty(content) ? "" : content);
		context.startActivity(intent);
	}
	/**
	 * 唤醒屏幕并解锁
	 * 
	 * @param context
	 */
	public static void wakeUpAndUnlock(Context context) {
		KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
		KeyguardManager.KeyguardLock kl = km.newKeyguardLock("unLock");
		// 解锁
		kl.disableKeyguard();
		// 获取电源管理器对象
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
		// 获取PowerManager.WakeLock对象,后面的参数|表示同时传入两个值,最后的是LogCat里用的Tag
		PowerManager.WakeLock wl = pm
				.newWakeLock(PowerManager.ACQUIRE_CAUSES_WAKEUP | PowerManager.SCREEN_DIM_WAKE_LOCK, "bright");
		// 点亮屏幕
		wl.acquire();
		// 释放
		wl.release();
	}
	/**
	 * 判断当前App处于前台还是后台状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isApplicationBackground(final Context context) {
		ActivityManager am = (ActivityManager) context
				.getSystemService(Context.ACTIVITY_SERVICE);
		@SuppressWarnings("deprecation")
		List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
		if (!tasks.isEmpty()) {
			ComponentName topActivity = tasks.get(0).topActivity;
			if (!topActivity.getPackageName().equals(context.getPackageName())) {
				return true;
			}
		}
		return false;
	}
	/**
	 * 判断当前手机是否处于锁屏(睡眠)状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isSleeping(Context context) {
		KeyguardManager kgMgr = (KeyguardManager) context
				.getSystemService(Context.KEYGUARD_SERVICE);
		boolean isSleeping = kgMgr.inKeyguardRestrictedInputMode();
		return isSleeping;
	}
	/**
	 * 判断当前是否有网络连接
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isOnline(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context
				.getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			return true;
		}
		return false;
	}
	/**
	 * 判断当前是否是WIFI连接状态
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isWifiConnected(Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo wifiNetworkInfo = connectivityManager
				.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (wifiNetworkInfo.isConnected()) {
			return true;
		}
		return false;
	}
	/**
	 * 安装APK
	 * 
	 * @param context
	 * @param file
	 */
	public static void installApk(Context context, File file) {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		intent.addCategory("android.intent.category.DEFAULT");
		intent.setType("application/vnd.android.package-archive");
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(intent);
	}
	/**
	 * 判断当前设备是否为手机
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isPhone(Context context) {
		TelephonyManager telephony = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (telephony.getPhoneType() == TelephonyManager.PHONE_TYPE_NONE) {
			return false;
		} else {
			return true;
		}
	}
	/**
	 * 获取当前设备宽高，单位px
	 * 
	 * @param context
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int getDeviceWidth(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		return manager.getDefaultDisplay().getWidth();
	}
	@SuppressWarnings("deprecation")
	public static int getDeviceHeight(Context context) {
		WindowManager manager = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		return manager.getDefaultDisplay().getHeight();
	}
	/**
	 * 获取当前设备的IMEI，需要与上面的isPhone()一起使用
	 * 
	 * @param context
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static String getDeviceIMEI(Context context) {
		String deviceId;
		if (isPhone(context)) {
			TelephonyManager telephony = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			deviceId = telephony.getDeviceId();
		} else {
			deviceId = Settings.Secure.getString(context.getContentResolver(),
					Settings.Secure.ANDROID_ID);
		}
		return deviceId;
	}
	/**
	 * 获取当前设备的MAC地址
	 * 
	 * @param context
	 * @return
	 */
	public static String getMacAddress(Context context) {
		String macAddress;
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifi.getConnectionInfo();
		macAddress = info.getMacAddress();
		if (null == macAddress) {
			return "";
		}
		macAddress = macAddress.replace(":", "");
		return macAddress;
	}
	/**
	 * 获取当前程序的版本号
	 * 
	 * @param context
	 * @return
	 */
	public static String getAppVersion(Context context) {
		String version = "0";
		try {
			version = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return version;
	}
	/**
	 * 收集设备信息，用于信息统计分析
	 * 
	 * @param context
	 * @return
	 */
	public static Properties collectDeviceInfo(Context context) {
		Properties mDeviceCrashInfo = new Properties();
		try {
			PackageManager pm = context.getPackageManager();
			PackageInfo pi = pm.getPackageInfo(context.getPackageName(),
					PackageManager.GET_ACTIVITIES);
			if (pi != null) {
				mDeviceCrashInfo.put(VERSION_NAME,
						pi.versionName == null ? "not set" : pi.versionName);
				mDeviceCrashInfo.put(VERSION_CODE, pi.versionCode);
			}
		} catch (PackageManager.NameNotFoundException e) {
			Log.e(TAG, "Error while collect package info", e);
		}
		Field[] fields = Build.class.getDeclaredFields();
		for (Field field : fields) {
			try {
				field.setAccessible(true);
				mDeviceCrashInfo.put(field.getName(), field.get(null));
			} catch (Exception e) {
				Log.e(TAG, "Error while collect crash info", e);
			}
		}
		return mDeviceCrashInfo;
	}
	public static String collectDeviceInfoStr(Context context) {
		Properties prop = collectDeviceInfo(context);
		Set deviceInfos = prop.keySet();
		StringBuilder deviceInfoStr = new StringBuilder("{\n");
		for (Iterator iter = deviceInfos.iterator(); iter.hasNext();) {
			Object item = iter.next();
			deviceInfoStr.append("\t\t\t" + item + ":" + prop.get(item)
					+ ", \n");
		}
		deviceInfoStr.append("}");
		return deviceInfoStr.toString();
	}
	/**
	 * 是否有SD卡
	 * 
	 * @return
	 */
	public static boolean haveSDCard() {
		return android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
	}
	/**
	 * 动态隐藏软键盘
	 * 
	 * @param activity
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static void hideSoftInput(Activity activity) {
		View view = activity.getWindow().peekDecorView();
		if (view != null) {
			InputMethodManager inputmanger = (InputMethodManager) activity
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			inputmanger.hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static void hideSoftInput(Context context, EditText edit) {
		edit.clearFocus();
		InputMethodManager inputmanger = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputmanger.hideSoftInputFromWindow(edit.getWindowToken(), 0);
	}
	/**
	 * 动态显示软键盘
	 * 
	 * @param context
	 * @param edit
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static void showSoftInput(Context context, EditText edit) {
		edit.setFocusable(true);
		edit.setFocusableInTouchMode(true);
		edit.requestFocus();
		InputMethodManager inputManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.showSoftInput(edit, 0);
	}
	/**
	 * 动态显示或者是隐藏软键盘
	 * 
	 * @param context
	 * @param edit
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static void toggleSoftInput(Context context, EditText edit) {
		edit.setFocusable(true);
		edit.setFocusableInTouchMode(true);
		edit.requestFocus();
		InputMethodManager inputManager = (InputMethodManager) context
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		inputManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
	}
	/**
	 * 主动回到Home，后台运行
	 * 
	 * @param context
	 */
	public static void goHome(Context context) {
		Intent mHomeIntent = new Intent(Intent.ACTION_MAIN);
		mHomeIntent.addCategory(Intent.CATEGORY_HOME);
		mHomeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				| Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
		context.startActivity(mHomeIntent);
	}
	/**
	 * 获取状态栏高度 注意，要在onWindowFocusChanged中调用，在onCreate中获取高度为0
	 * 
	 * @param activity
	 * @return
	 */
	@TargetApi(Build.VERSION_CODES.CUPCAKE)
	public static int getStatusBarHeight(Activity activity) {
		Rect frame = new Rect();
		activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
		return frame.top;
	}
	/**
	 * 获取状态栏高度＋标题栏(ActionBar)高度 (注意，如果没有ActionBar，那么获取的高度将和上面的是一样的，只有状态栏的高度)
	 * 
	 * @param activity
	 * @return
	 */
	public static int getTopBarHeight(Activity activity) {
		return activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT)
				.getTop();
	}
	/**
	 * 获取MCC+MNC代码 (SIM卡运营商国家代码和运营商网络代码) 仅当用户已在网络注册时有效, CDMA 可能会无效（中国移动：46000
	 * 46002, 中国联通：46001,中国电信：46003）
	 * 
	 * @param context
	 * @return
	 */
	public static String getNetworkOperator(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getNetworkOperator();
	}
	/**
	 * 返回移动网络运营商的名字 (例：中国联通、中国移动、中国电信) 仅当用户已在网络注册时有效, CDMA 可能会无效)
	 * 
	 * @param context
	 * @return
	 */
	public static String getNetworkOperatorName(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getNetworkOperatorName();
	}
	/**
	 * 返回移动终端类型 PHONE_TYPE_NONE :0 手机制式未知 PHONE_TYPE_GSM :1 手机制式为GSM，移动和联通
	 * PHONE_TYPE_CDMA :2 手机制式为CDMA，电信 PHONE_TYPE_SIP:3
	 * 
	 * @param context
	 * @return
	 */
	public static int getPhoneType(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		return telephonyManager.getPhoneType();
	}
	/**
	 * 判断手机连接的网络类型(2G,3G,4G)
	 * 联通的3G为UMTS或HSDPA，移动和联通的2G为GPRS或EGDE，电信的2G为CDMA，电信的3G为EVDO
	 *
	 */
	public class Constants {
		/**
		 * 
		 * Unknown network class
		 * 
		 */
		public static final int NETWORK_CLASS_UNKNOWN = 0;
		/**
		 * 
		 * wifi net work
		 * 
		 */
		public static final int NETWORK_WIFI = 1;
		/**
		 * 
		 * "2G" networks
		 * 
		 */
		public static final int NETWORK_CLASS_2_G = 2;
		/**
		 * 
		 * "3G" networks
		 * 
		 */
		public static final int NETWORK_CLASS_3_G = 3;
		/**
		 * 
		 * "4G" networks
		 * 
		 */
		public static final int NETWORK_CLASS_4_G = 4;
	}
	public static int getNetWorkClass(Context context) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		switch (telephonyManager.getNetworkType()) {
		case TelephonyManager.NETWORK_TYPE_GPRS:
		case TelephonyManager.NETWORK_TYPE_EDGE:
		case TelephonyManager.NETWORK_TYPE_CDMA:
		case TelephonyManager.NETWORK_TYPE_1xRTT:
		case TelephonyManager.NETWORK_TYPE_IDEN:
			return Constants.NETWORK_CLASS_2_G;
		case TelephonyManager.NETWORK_TYPE_UMTS:
		case TelephonyManager.NETWORK_TYPE_EVDO_0:
		case TelephonyManager.NETWORK_TYPE_EVDO_A:
		case TelephonyManager.NETWORK_TYPE_HSDPA:
		case TelephonyManager.NETWORK_TYPE_HSUPA:
		case TelephonyManager.NETWORK_TYPE_HSPA:
		case TelephonyManager.NETWORK_TYPE_EVDO_B:
		case TelephonyManager.NETWORK_TYPE_EHRPD:
		case TelephonyManager.NETWORK_TYPE_HSPAP:
			return Constants.NETWORK_CLASS_3_G;
		case TelephonyManager.NETWORK_TYPE_LTE:
			return Constants.NETWORK_CLASS_4_G;
		default:
			return Constants.NETWORK_CLASS_UNKNOWN;
		}
	}
	/**
	 * 判断当前手机的网络类型(WIFI还是2,3,4G) 需要用到上面的方法
	 * 
	 * @param context
	 * @return
	 */
	public static int getNetWorkStatus(Context context) {
		int netWorkType = Constants.NETWORK_CLASS_UNKNOWN;
		ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			int type = networkInfo.getType();
			if (type == ConnectivityManager.TYPE_WIFI) {
				netWorkType = Constants.NETWORK_WIFI;
			} else if (type == ConnectivityManager.TYPE_MOBILE) {
				netWorkType = getNetWorkClass(context);
			}
		}
		return netWorkType;
	}
	/**
	 * px-dp转换
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	/**
	 * px-sp转换
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
	// 把一个毫秒数转化成时间字符串
	// 格式为小时/分/秒/毫秒（如：24903600 –> 06小时55分03秒600毫秒）
	/**
	 * 
	 * @param millis
	 * 
	 *            要转化的毫秒数。
	 * 
	 * @param isWhole
	 * 
	 *            是否强制全部显示小时/分/秒/毫秒。
	 * 
	 * @param isFormat
	 * 
	 *            时间数字是否要格式化，如果true：少位数前面补全；如果false：少位数前面不补全。
	 * 
	 * @return 返回时间字符串：小时/分/秒/毫秒的格式（如：24903600 --> 06小时55分03秒600毫秒）。
	 * 
	 */
	public static String millisToString(long millis, boolean isWhole,
			boolean isFormat) {
		String h = "";
		String m = "";
		String s = "";
		String mi = "";
		if (isWhole) {
			h = isFormat ? "00小时" : "0小时";
			m = isFormat ? "00分" : "0分";
			s = isFormat ? "00秒" : "0秒";
			mi = isFormat ? "00毫秒" : "0毫秒";
		}
		long temp = millis;
		long hper = 60 * 60 * 1000;
		long mper = 60 * 1000;
		long sper = 1000;
		if (temp / hper > 0) {
			if (isFormat) {
				h = temp / hper < 10 ? "0" + temp / hper : temp / hper + "";
			} else {
				h = temp / hper + "";
			}
			h += "小时";
		}
		temp = temp % hper;
		if (temp / mper > 0) {
			if (isFormat) {
				m = temp / mper < 10 ? "0" + temp / mper : temp / mper + "";
			} else {
				m = temp / mper + "";
			}
			m += "分";
		}
		temp = temp % mper;
		if (temp / sper > 0) {
			if (isFormat) {
				s = temp / sper < 10 ? "0" + temp / sper : temp / sper + "";
			} else {
				s = temp / sper + "";
			}
			s += "秒";
		}
		temp = temp % sper;
		mi = temp + "";
		if (isFormat) {
			if (temp < 100 && temp >= 10) {
				mi = "0" + temp;
			}
			if (temp < 10) {
				mi = "00" + temp;
			}
		}
		mi += "毫秒";
		return h + m + s + mi;
	}
	// 格式为小时/分/秒/毫秒（如：24903600 –> 06小时55分03秒）。
	/**
	 *
	 * 
	 * 
	 * @param millis
	 * 
	 *            要转化的毫秒数。
	 * 
	 * @param isWhole
	 * 
	 *            是否强制全部显示小时/分/秒/毫秒。
	 * 
	 * @param isFormat
	 * 
	 *            时间数字是否要格式化，如果true：少位数前面补全；如果false：少位数前面不补全。
	 * 
	 * @return 返回时间字符串：小时/分/秒/毫秒的格式（如：24903600 --> 06小时55分03秒）。
	 * 
	 */
	public static String millisToStringMiddle(long millis, boolean isWhole,
			boolean isFormat) {
		return millisToStringMiddle(millis, isWhole, isFormat, "小时", "分钟", "秒");
	}
	public static String millisToStringMiddle(long millis, boolean isWhole,boolean isFormat, String hUnit, String mUnit, String sUnit) {
		String h = "";
        String m = "";
        String s = "";
        if (isWhole) {
            h = isFormat ? "00" + hUnit : "0" + hUnit;
            m = isFormat ? "00" + mUnit : "0" + mUnit;
            s = isFormat ? "00" + sUnit : "0" + sUnit;
        }

        long temp = millis;

        long hper = 60 * 60 * 1000;
        long mper = 60 * 1000;
        long sper = 1000;

        if (temp / hper > 0) {
            if (isFormat) {
                h = temp / hper < 10 ? "0" + temp / hper : temp / hper + "";
            } else {
                h = temp / hper + "";
            }
            h += hUnit;
        }
        temp = temp % hper;

        if (temp / mper > 0) {
            if (isFormat) {
                m = temp / mper < 10 ? "0" + temp / mper : temp / mper + "";
            } else {
                m = temp / mper + "";
            }
            m += mUnit;
        }
        temp = temp % mper;

        if (temp / sper > 0) {
            if (isFormat) {
                s = temp / sper < 10 ? "0" + temp / sper : temp / sper + "";
            } else {
                s = temp / sper + "";
            }
            s += sUnit;
        }
        return h + m + s;
    }
}
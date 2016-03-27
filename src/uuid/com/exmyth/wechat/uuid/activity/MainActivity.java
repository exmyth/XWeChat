package com.exmyth.wechat.uuid.activity;

import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import com.exmyth.wechat.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private Button btn;
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.uuid_activity_main);
		tv = (TextView) findViewById(R.id.uuid_id_tv);
//		tv.setText(getAndroidId(this));
		btn = (Button) findViewById(R.id.uuid_id_btn);
		initView();
	}
	private void initView() {
		btn.setOnClickListener(this);
	}
	/*
	获取DeviceId
	在获取deviceId之前先要要 AndroidManifest 中声明权限:
	<uses-permission android:name="android.permission.READ_PHONE_STATE" />
	在获取设备ID时应该注意：
	需要 READ_PHONE_STATE 权限
	在6.0或更高的设备上时因为权限要在运行时请求而 READ_PHONE_STATE 是一个 dangerous 权限用户极有可能不允许这个权限请求
	*/
	/*
	public static String getDeviceId(Context context){
        String  deviceId = null;
        if(Activity.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();
        }
        return deviceId;
    }
	*/
	/*
	获取AndroidId:
	获取AndroidId是不需要权限的但是AndroidId是可能变的，
	AndroidId是在用户第一次激活这个设备时产生的所以当用户重置手机时AndroidId会产生变化，
	理论上这个AndroidId是可以接受的毕竟重置手机这个事发生也不会太频繁。
	*/
	public static String getAndroidId(Context context) {
		return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
	}
	
	/*
	获取MAC地址

	可以使用WIFI的MAC地址来作为标识符，感觉现阶段这种方式比较可靠总结如下：
	Mac地址是唯一的
	直接产生在硬件上基本上不会变更
	不需要额外的权限
	向上或向下兼容
	*/
	public static String getMacAddress(Context context) {
        String macAddress = null;
        try{
            String wifiInterfaceName = "wlan0";
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            while (interfaces.hasMoreElements()) {
                NetworkInterface iF = interfaces.nextElement();
                if(iF.getName().equalsIgnoreCase(wifiInterfaceName)) {
                    byte[] addr = iF.getHardwareAddress();
                    if (addr == null || addr.length == 0) {
                        return null;
                    }

                    StringBuilder buf = new StringBuilder();
                    for (byte b : addr) {
                        buf.append(String.format("%02X:", b));
                    }
                    if (buf.length() > 0) {
                        buf.deleteCharAt(buf.length() - 1);
                    }
                    macAddress =  buf.toString();
                    break;
                }
            }
        }catch (SocketException se){
            macAddress = null;
        }

        if(TextUtils.isEmpty(macAddress)){
            android.net.wifi.WifiManager wifi = (android.net.wifi.WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            macAddress = wifi.getConnectionInfo().getMacAddress();
        }

        return macAddress;
    }
	@Override
	public void onClick(View v) {
		tv.setText(getMacAddress(this));
	}
}

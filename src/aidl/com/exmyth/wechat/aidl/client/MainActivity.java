package com.exmyth.wechat.aidl.client;

import com.exmyth.wechat.R;
import com.exmyth.wechat.aidl.IRemoteAIDL;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {

	private EditText mEdtNum1;
	private EditText mEdtNum2;
	private Button mBtnAdd;
	private TextView mTxtResult;
	private IRemoteAIDL aidl;
	private ServiceConnection conn = new ServiceConnection() {
		

		@Override
		public void onServiceDisconnected(ComponentName name) {
			aidl = null;
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			aidl = IRemoteAIDL.Stub.asInterface(service);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aidl_activity_main);
		initView();
		bindService();
	}

	private void initView() {
		mEdtNum1 = (EditText)findViewById(R.id.edt_num1);
		mEdtNum2 = (EditText)findViewById(R.id.edt_num2);
		mTxtResult = (TextView)findViewById(R.id.txt_result);
		
		mBtnAdd = (Button)findViewById(R.id.btn_add);
		mBtnAdd.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		try {
			int num1 = Integer.parseInt(mEdtNum1.getText().toString());
			int num2 = Integer.parseInt(mEdtNum2.getText().toString());
			int result = aidl.add(num1, num2);
			mTxtResult.setText(result+"");
		} catch (NumberFormatException | RemoteException e) {
			e.printStackTrace();
		}
	}

	private void bindService() {
		Intent intent = new Intent();
		intent.setComponent(new ComponentName("com.exmyth.demo", 
				"com.exmyth.wechat.aidl.service.IRemoteService"));
		bindService(intent, conn, Context.BIND_AUTO_CREATE);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		unbindService(conn);
	}
}

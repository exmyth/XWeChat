package com.exmyth.wechat.network.asynchttp.activity;


import com.exmyth.wechat.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.network_asynchttp_activity_main);
		doGet();
	}

	private void doGet() {
		AsyncHttpClient client = new AsyncHttpClient();

		client.get("http://www.baidu.com", new AsyncHttpResponseHandler() {

		    @Override
		    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
		    	Log.d(TAG, "-----onSuccess"+new String(responseBody));
		    }

		    @Override
		    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
		    	Log.d(TAG, "-----onFailure");
		    }

		    @Override
		    public void onStart() {
		        super.onStart();
		        Log.d(TAG, "-----onStart");
		    }

		    @Override
		    public void onFinish() {
		        super.onFinish();
		        Log.d(TAG, "-----onFinish");
		    }

		    @Override
		    public void onRetry(int retryNo) {
		        super.onRetry(retryNo);
		        Log.d(TAG, "-----onRetry");
		    }

		    @Override
		    public void onCancel() {
		        super.onCancel();
		        Log.d(TAG, "-----onCancel");
		    }
		    @Override
		    public void onProgress(long bytesWritten, long totalSize) {
		    	super.onProgress(bytesWritten, totalSize);
		    	Log.d(TAG, "-----onProgress"+bytesWritten+"/"+totalSize);
		    }
		});
	}
}

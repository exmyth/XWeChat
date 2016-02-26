package com.exmyth.wechat.volley.activity;

import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.exmyth.wechat.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends Activity {

	private Context context;
	private RequestQueue mQueue;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.volley_activity_main);
		context = this;
		initData();
		request();
	}
	
	private void initData() {
		mQueue = Volley.newRequestQueue(context);
	}

	private void request() {
		StringRequest stringRequest = new StringRequest(Method.POST,"http://www.baidu.com",  
	        new Response.Listener<String>() {  
	            @Override  
	            public void onResponse(String response) {  
	                Log.d("TAG", response);  
	            }  
	        }, new Response.ErrorListener() {  
	            @Override  
	            public void onErrorResponse(VolleyError error) {  
	                Log.e("TAG", error.getMessage(), error);  
	            }  
	        });
		mQueue.add(stringRequest);
	}
}

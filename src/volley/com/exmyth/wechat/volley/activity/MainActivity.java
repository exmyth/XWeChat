package com.exmyth.wechat.volley.activity;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
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
//		requestString();
//		requestJSON();
		requestJsonArray();
	}
	
	private void requestJsonArray() {
		new JsonArrayRequest(Method.POST, "http://www.baidu.com", null, 
		new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
			}
		}, new Response.ErrorListener(){

			@Override
			public void onErrorResponse(VolleyError error) {
			}
		});
	}

	private void requestJSON() {
		JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("http://m.weather.com.cn/data/101010100.html", null,
				new Response.Listener<JSONObject>() {
					@Override
					public void onResponse(JSONObject response) {
						Log.d("TAG", response.toString());
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(), error);
					}
				});
		mQueue.add(jsonObjectRequest);
	}

	private void initData() {
		mQueue = Volley.newRequestQueue(context);
	}

	private void requestString() {
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
	        }){
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("params1", "value1");
				map.put("params2", "value2");
				return map;
			}
		};
		mQueue.add(stringRequest);
	}
}

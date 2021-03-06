package com.exmyth.wechat.volley.activity;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.GsonRequest;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageListener;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.toolbox.XMLRequest;
import com.exmyth.wechat.R;
import com.exmyth.wechat.volley.cache.BitmapCache;
import com.exmyth.wechat.volley.model.Weather;
import com.exmyth.wechat.volley.model.WeatherInfo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MainActivity extends Activity {

	private Context context;
	private RequestQueue mQueue;
	private ImageView imageView;
	private NetworkImageView networkImageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.volley_activity_main);
		context = this;
		initView();
		initData();
//		requestString();
//		requestJSON();
//		requestJsonArray();
//		requestImage();
//		loadImage();
//		setNetworkImageView();
//		requestXML();
		requestGSON();
	}
	
	private void requestGSON() {
		GsonRequest<Weather> gsonRequest = new GsonRequest<Weather>(  
		        "http://www.weather.com.cn/data/sk/101010100.html", Weather.class,  
		        new Response.Listener<Weather>() {  
		            @Override  
		            public void onResponse(Weather weather) {  
		                WeatherInfo weatherInfo = weather.getWeatherinfo();  
		                Log.d("TAG", "city is " + weatherInfo.getCity());  
		                Log.d("TAG", "temp is " + weatherInfo.getTemp());  
		                Log.d("TAG", "time is " + weatherInfo.getTime());  
		            }  
		        }, new Response.ErrorListener() {  
		            @Override  
		            public void onErrorResponse(VolleyError error) {  
		                Log.e("TAG", error.getMessage(), error);  
		            }  
		        });  
		mQueue.add(gsonRequest); 
	}

	private void requestXML() {
		XMLRequest xmlRequest = new XMLRequest(  
		        "http://flash.weather.com.cn/wmaps/xml/china.xml",  
		        new Response.Listener<XmlPullParser>() {  
		            @Override  
		            public void onResponse(XmlPullParser response) {  
		                try {  
		                    int eventType = response.getEventType();  
		                    while (eventType != XmlPullParser.END_DOCUMENT) {  
		                        switch (eventType) {  
		                        case XmlPullParser.START_TAG:  
		                            String nodeName = response.getName();  
		                            if ("city".equals(nodeName)) {  
		                                String pName = response.getAttributeValue(0);  
		                                Log.d("TAG", "pName is " + pName);  
		                            }  
		                            break;  
		                        }  
		                        eventType = response.next();  
		                    }  
		                } catch (XmlPullParserException e) {  
		                    e.printStackTrace();  
		                } catch (IOException e) {  
		                    e.printStackTrace();  
		                }  
		            }  
		        }, new Response.ErrorListener() {  
		            @Override  
		            public void onErrorResponse(VolleyError error) {  
		                Log.e("myth", error.getMessage(), error);  
		            }  
		        });  
		xmlRequest.setShouldCache(false);
		mQueue.add(xmlRequest);
	}

	private void setNetworkImageView() {
		networkImageView = (NetworkImageView) findViewById(R.id.network_image_view);
		
		networkImageView.setDefaultImageResId(R.drawable.default_image);  
		networkImageView.setErrorImageResId(R.drawable.error_image);
		ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache()); 
		networkImageView.setImageUrl("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg",  
		                imageLoader);
	}

	private void initView() {
		imageView = (ImageView) findViewById(R.id.imgVolley);
	}

	private void loadImage() {
		ImageListener listener = ImageLoader.getImageListener(imageView,  
		        R.drawable.default_image, R.drawable.error_image);
		ImageLoader imageLoader = new ImageLoader(mQueue, new BitmapCache()); 
		imageLoader.get("http://img.my.csdn.net/uploads/201404/13/1397393290_5765.jpeg",  
                listener, 200, 200);  
	}

	private void requestImage() {
		ImageRequest imageRequest = new ImageRequest("http://developer.android.com/images/home/aw_dac.png", 
				new Response.Listener<Bitmap>() {  
		            @Override  
		            public void onResponse(Bitmap response) {  
						imageView.setImageBitmap(response);  
		            }  
		        }, 0, 0, ScaleType.CENTER_CROP, Config.RGB_565, 
				new Response.ErrorListener() {  
		            @Override  
		            public void onErrorResponse(VolleyError error) {  
		                imageView.setImageResource(R.drawable.ic_launcher);  
		            }
		        }
		 );
		
		mQueue.add(imageRequest);  
	}

	private void requestJsonArray() {
		JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Method.POST, "http://www.baidu.com", null, 
		new Response.Listener<JSONArray>() {

			@Override
			public void onResponse(JSONArray response) {
			}
		}, new Response.ErrorListener(){

			@Override
			public void onErrorResponse(VolleyError error) {
			}
		});
		
		mQueue.add(jsonArrayRequest);
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

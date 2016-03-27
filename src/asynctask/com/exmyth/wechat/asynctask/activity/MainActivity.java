package com.exmyth.wechat.asynctask.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.exmyth.wechat.R;
import com.exmyth.wechat.asynctask.adapter.NewsAdapter;
import com.exmyth.wechat.asynctask.model.Bean;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends Activity {
	private ListView listView;
	private static String URL="http://www.imooc.com/api/teacher?type=4&num=30";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.async_task_activity_main);
		listView=(ListView) findViewById(R.id.listview);
		new NewsAsynTask().execute(URL);//第一步NewsAsynTask将一个url传递进来，实现对url的异步访问
	}
	//获取json
	private List<Bean> getJsonData(String url){//第二步getJsonData将url转化为所需要的json对像
		List<Bean> beanList=new ArrayList<Bean>();
		String jsonString;
		try {
			jsonString = readStream(new java.net.URL(url).openStream());
			JSONObject jsonObject;
			Bean bean;
			jsonObject=new JSONObject(jsonString);
			JSONArray jsonArray=jsonObject.getJSONArray("data");
			for (int i = 0; i < jsonArray.length(); i++) {
				jsonObject=jsonArray.getJSONObject(i);//jsonArray中每一个都是jsonObject
				bean=new Bean();//初始化bean
				bean.newsIconUrl=jsonObject.getString("picSmall");
				bean.newsTitle=jsonObject.getString("name");
				bean.newsContent=jsonObject.getString("description");
				beanList.add(bean);
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return beanList;
	}
	//读取json的字符流
	private String readStream(InputStream in){//传进来一个字节流
		InputStreamReader inReader;
		String result="";
		try {
			String line="";
			inReader=new InputStreamReader(in,"utf-8");//通过InputStreamReader将字节流转化成字符流
			BufferedReader bf=new BufferedReader(inReader);//通过BufferedReader将字符流再读取出来
			while ((line=bf.readLine())!=null) {
				result+=line;
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
		
	}
	//实现网络的异步访问
	class NewsAsynTask extends AsyncTask<String, Void, List<Bean>>{//Void 表示不记录中间过程
		protected List<Bean> doInBackground(String... param) {
			return getJsonData(param[0]);
		}
		//第三步 onPostExecute将生成的数据传递给NewsAdapter 即将生成的bean设置给listview 
			protected void onPostExecute(List<Bean> bean) {
				super.onPostExecute(bean);
				NewsAdapter adapter=new NewsAdapter(MainActivity.this,bean,listView);
				listView.setAdapter(adapter);
			}
	}
}

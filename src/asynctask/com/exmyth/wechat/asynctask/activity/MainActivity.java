package com.exmyth.wechat.asynctask.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.exmyth.wechat.R;
import com.exmyth.wechat.asynctask.adapter.NewsAdapter;
import com.exmyth.wechat.asynctask.model.NewsBeam;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;


public class MainActivity extends Activity
{
	private static final String urlPath = "http://www.imooc.com/api/teacher?type=4&num=30";
	//private static final String urlPath = "http://14.117.17.161/json.php";
	private ListView mListView;


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.async_task_activity_main);

		mListView = (ListView) findViewById(R.id.lv_main);
		new NewAsyncTask().execute(urlPath);
	}


	/**
	 * 实现网络的异步访问
	 */
	class NewAsyncTask extends AsyncTask<String, Void, List<NewsBeam>>
	{
		@Override
		protected void onPostExecute(List<NewsBeam> newsBeams)
		{
			super.onPostExecute(newsBeams);
			NewsAdapter adapter = new NewsAdapter(MainActivity.this, newsBeams, mListView);
			mListView.setAdapter(adapter);
		}

		@Override
		protected List<NewsBeam> doInBackground(String... params)
		{
			return getJsonData(params[0]);
		}


		/**
		 * 从 URL 中获取数据
		 *
		 * @param url
		 * @return
		 */
		private List<NewsBeam> getJsonData(String url)
		{
			List<NewsBeam> newsBeamsList = new ArrayList<NewsBeam>();
			try
			{
				String jsonString = readStream(new URL(urlPath).openStream());

				JSONObject jsonObject;
				NewsBeam newsBeam;

				jsonObject = new JSONObject(jsonString);
				JSONArray jsonArray = jsonObject.getJSONArray("data");
				for(int i = 0; i < jsonArray.length(); i++)
				{
					jsonObject = jsonArray.getJSONObject(i);
					newsBeam = new NewsBeam();
					newsBeam.newsIconUrl = jsonObject.getString("picSmall");
					newsBeam.newsTitle = jsonObject.getString("name");
					newsBeam.newsContent = jsonObject.getString("description");
					newsBeamsList.add(newsBeam);
				}

			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			catch(JSONException e)
			{
				e.printStackTrace();
			}
			return newsBeamsList;
		}


		/**
		 * 从 inpustStream 获取的信息
		 *
		 * @param is
		 * @return
		 */
		private String readStream(InputStream is)
		{
			InputStreamReader isr;
			String result = "";
			try
			{
				isr = new InputStreamReader(is, "utf-8");
				BufferedReader br = new BufferedReader(isr);
				String line = "";
				while((line = br.readLine()) != null)
				{
					result += line;
				}

			}
			catch(UnsupportedEncodingException e)
			{
				e.printStackTrace();
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			return result;
		}
	}

}

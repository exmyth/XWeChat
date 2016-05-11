package com.exmyth.wechat.test.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONTest {
	public static void main(String[] args) throws JSONException {
		String str = "data:\"[1462934272000,1462934288000,1462934292000,1462934305000,1462934317000,1462934318000,1462934318000,1462946398000]\"";
		
		JSONObject json = new JSONObject(str);
		JSONArray jsonArray = json.getJSONArray("data");
		System.out.println(jsonArray);
	}
}

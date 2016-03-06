package com.android.volley.toolbox;

import java.io.UnsupportedEncodingException;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Request.Method;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.google.gson.Gson;

public class GsonRequest<T> extends Request<T> {  
  
    private final Listener<T> mListener;  
  
    private Gson mGson;  
  
    private Class<T> mClass;  
  
    public GsonRequest(int method, String url, Class<T> clazz, Listener<T> listener,  
            ErrorListener errorListener) {  
        super(method, url, errorListener);  
        mGson = new Gson();  
        mClass = clazz;  
        mListener = listener;  
    }  
  
    public GsonRequest(String url, Class<T> clazz, Listener<T> listener,  
            ErrorListener errorListener) {  
        this(Method.GET, url, clazz, listener, errorListener);  
    }  
  
    @Override  
    protected Response<T> parseNetworkResponse(NetworkResponse response) {  
        try {  
            String jsonString = new String(response.data,  
                    HttpHeaderParser.parseCharset(response.headers));  
            return Response.success(mGson.fromJson(jsonString, mClass),  
                    HttpHeaderParser.parseCacheHeaders(response));  
        } catch (UnsupportedEncodingException e) {  
            return Response.error(new ParseError(e));  
        }  
    }  
  
    @Override  
    protected void deliverResponse(T response) {  
        mListener.onResponse(response);  
    }  
  
}  
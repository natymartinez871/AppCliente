package com.ayalamart.app;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.app.Application;
import android.text.TextUtils;

public class Net_menu extends Application {

	public static final String TAG = Net_menu.class.getSimpleName(); 
	private RequestQueue mRequestQueue;
	private static Net_menu mInstance; 


	public void OnCreate(){
		super.onCreate();
		mInstance = this; 
	}

	public static synchronized Net_menu getInstance(){	return mInstance; 
	}
	public RequestQueue getRequestQueue(){
		if (mRequestQueue == null) {
			mRequestQueue = Volley.newRequestQueue(getApplicationContext()); 
		}
		return mRequestQueue; 
	}

	public <T> void addToRequestQueue(Request<T> req, String tag){
		req.setTag(TextUtils.isEmpty(tag) ? TAG : tag); 
		getRequestQueue().add(req); 
	}
	
	public <T> void addToRequestQueue(Request<T> req){
		req.setTag(TAG); 
		getRequestQueue().add(req); 
	}
	public void cancelPendingRequests(Object tag){
		if (mRequestQueue != null) {
			mRequestQueue.cancelAll(tag);
		}
	}

}

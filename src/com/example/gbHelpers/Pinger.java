package com.example.gbHelpers;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.util.Log;

public class Pinger extends AsyncTask<String, Void, Void> {
	
	private final HttpClient client = new DefaultHttpClient();
	private final String URL = "http://54.69.71.254:8080/timestamp/";
	
	private long startTime;
	private long timeStamp, userCount;
	private String error = null;
	
	private String ret = null;
	
	private PingThread pingerThread;
	
	public Pinger(PingThread me) {
			pingerThread = me;
	}
	@Override 
	protected void onPreExecute(){
		startTime = System.currentTimeMillis();
	}
	@Override
	protected Void doInBackground(String... urls) {
		try{
			
			HttpGet httpGet = new HttpGet(URL);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String content = client.execute(httpGet, responseHandler);
			
			timeStamp = -1;
			userCount = -1;
			
			Log.w("content : ", content);
			try {
				JSONObject obj = new JSONObject(content);
//				JSONObject obj = new JSONObject("{\"time\":1416595726805670161,\"count\":1}");
				timeStamp = obj.getLong("time");
				userCount = obj.getLong("count");
				Log.w("timestamp : ", timeStamp+ " "+ userCount);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}catch (ClientProtocolException e) {
            error = e.getMessage();
            cancel(true);
        } catch (IOException e) {
            error = e.getMessage();
            cancel(true);
        }
         
		return null;
	}
	
	@Override
	protected void onPostExecute(Void unused){
		if(error != null ){
			Log.w("Error? : ", error);
			ret = ("Output : "+ error);
		}else{
//			ret = ("Output : "+ content);
			if(timeStamp != -1 && userCount != -1){
				long delay = System.currentTimeMillis() - startTime;
				Log.w("delay :" , delay+" ");
				
				//convert to millis
				String s = Long.toString(timeStamp);
				s = s.substring(0,s.length() - 7);
				Log.w("timestamp :" , s+" ");
				
				pingerThread.setTimeStamp(Float.parseFloat(s));
				pingerThread.setUserCount(userCount);
				pingerThread.setDelay(delay);
				
//				pingerThread.setStamp(Float.parseFloat(content));
			}
		}
	}
	public String getRet(){
		return ret;
	}
}

/*
*
* Author: George Macrae 
*
* 2014
*
*/

package com.georgemacrae.gbHelpers;

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
		// ping the url
		try{
			HttpGet httpGet = new HttpGet(URL);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();
			String content = client.execute(httpGet, responseHandler);
			
			timeStamp = -1;
			userCount = -1;
			
//			Log.w("content : ", content);
			try {
				JSONObject obj = new JSONObject(content);
				timeStamp = obj.getLong("time");
				userCount = obj.getLong("count");
//				Log.w("timestamp : ", timeStamp+ " "+ userCount);
			} catch (JSONException e) {
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
			if(timeStamp != -1 && userCount != -1){
				
				//convert to millis from nanos
				String s = Long.toString(timeStamp);
				long l = Long.parseLong(s)/1000000;
				l = l%10000;
				
//				Log.w("timestamp pinger :" , l+" ");
				
				// set and notify pingerThread
				pingerThread.setTimeStamp(l);
				pingerThread.setUserCount(userCount);

				long delay = System.currentTimeMillis() - startTime;
//				Log.w("delay :" , delay+" ");
				pingerThread.setDelay(delay);
				
			}
		}
	}
	public String getRet(){
		return ret;
	}
}

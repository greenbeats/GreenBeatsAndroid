package com.example.gbHelpers;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import android.os.AsyncTask;
import android.util.Log;

public class Pinger extends AsyncTask<String, Void, Void> {
	
	private final HttpClient client = new DefaultHttpClient();
	private final String URL = "http://54.69.71.254:8080/timeStamp/";
	
	private long startTime;
	private String content;
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
			content = client.execute(httpGet, responseHandler);
			Log.w("content : ", content);
			
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
			ret = ("Output : "+ content);
			if(content != null){
				long delay = System.currentTimeMillis() - startTime;
				Log.w("delay :" , delay+" ");
				
				//convert to millis
				content = content.substring(0,content.length() - 7);
				Log.w("timestamp :" , content+" ");
				
				pingerThread.setDelay(delay);
				pingerThread.setTimeStamp(Float.parseFloat(content));
				
//				pingerThread.setStamp(Float.parseFloat(content));
			}
		}
	}
	public String getRet(){
		return ret;
	}
}

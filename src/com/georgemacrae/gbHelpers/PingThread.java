/*
*
* Author: George Macrae 
*
* 2014
*
*/

package com.georgemacrae.gbHelpers;

import android.util.Log;

import com.georgemacrae.greenbeatsv2.VideoPlayerActivity;

public class PingThread extends Thread{
	private final int PING_INTERVAL = 10000;
	private final int PING_REQUESTS = 3;
	private final int VIDEO_LENGTH = 10000;
	private VideoPlayerActivity player;
	private Boolean running;
	
	private long delay;
	private float timeStamp;
	private boolean lock;
	private long userCount;
	
	// constructor
	public PingThread(VideoPlayerActivity player){
		Log.w("new Thread","new thread");
		this.player = player;
		running = true;
	}
	
	// run
	// calls pinger then sleeps
	// pinger notifies pingerthread by setting the delay so loop can continue
	// pinger thread then notifies VideoPlayerActivity to update video
	@Override
	public void run(){
		
		while(running){
			
			delay = 0;
			for(int i = 0; i < PING_REQUESTS ; i++){
				lock = true;
				Pinger pinger = new Pinger(this);
				pinger.execute();
				while(lock){
					if(!running){
						break;
					}
				}
			}
			
			// average delay
			// divide by two for round trip
			delay = delay/(2*PING_REQUESTS);
			
//				Log.w("Average delay :" , delay+" ");
//				Log.w("Time stamp :" , timeStamp+" ");
			
			//add delay to startTime
			//mod startTime by length of video
			float startTime = timeStamp + delay;
			startTime = startTime % VIDEO_LENGTH;
			
			
//				Log.w("startTime + delay:" , startTime+" ");
			
			// notifiy video
			player.updateVideo((int) startTime, userCount);
			try {
				sleep(PING_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setDelay(long x){
		delay += x;
		lock = false;
	}
	public void setTimeStamp(float x){
		timeStamp = x;
	}
	public void setRunning(Boolean x)
	{
		running = x;
	}
	public void setUserCount(long u) {
		userCount = u;
	}

}

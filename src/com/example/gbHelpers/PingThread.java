package com.example.gbHelpers;

import android.util.Log;

import com.example.greenbeatsv2.VideoPlayerActivity;

public class PingThread extends Thread{
	private final int PING_INTERVAL = 30000;
	private final int PING_REQUESTS = 3;
	private final int VIDEO_LENGTH = 10000;
	private VideoPlayerActivity player;
	private Boolean running;
	
	private long delay;
	private float timeStamp;
	private boolean lock;
	private long userCount;
	
	public PingThread(VideoPlayerActivity player){
		this.player = player;
		running = true;
	}
	@Override
	public void run(){
		while(true){
			while(running){
				delay = 0;
				for(int i = 0; i < PING_REQUESTS ; i++){
					lock = true;
					Pinger pinger = new Pinger(this);
					pinger.execute();
					while(lock);
				}
				
				// average delay
				delay = delay/PING_REQUESTS;
				
				Log.w("Average delay :" , delay+" ");
				
				//mod startTime by length of video
				//add delay to startTime
				float startTime = timeStamp % VIDEO_LENGTH;
				startTime += delay;
				startTime = startTime % VIDEO_LENGTH;

				Log.w("startTime :" , startTime+" ");
				player.updateVideo((int) startTime, userCount);
				try {
					sleep(PING_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
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

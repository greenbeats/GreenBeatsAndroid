package com.example.gbHelpers;

import android.util.Log;

import com.example.greenbeatsv2.VideoPlayerActivity;

public class PingThread extends Thread{
	private final int PING_INTERVAL = 30000;
	
	private VideoPlayerActivity player;
	private Boolean running;
	
	private long delay;
	private float timeStamp;
	private boolean lock;
	
	public PingThread(VideoPlayerActivity player){
		this.player = player;
		running = true;
	}
	@Override
	public void run(){
		while(true){
			while(running){
				delay = 0;
				for(int i = 0; i < 10 ; i++){
					lock = true;
					Pinger pinger = new Pinger(this);
					pinger.execute();
					while(lock);
				}
				delay = delay/10;
				
				Log.w("Average delay :" , delay+" ");
				float startTime = timeStamp % 10000;
				startTime += delay;
				startTime = startTime % 10000;

				Log.w("startTime :" , startTime+" ");
				player.updateVideo((int) startTime);
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

}

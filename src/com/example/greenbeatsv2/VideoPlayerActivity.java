/*
*
* Author: George Macrae 
*
* 2014
*
*/

package com.example.greenbeatsv2;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.VideoView;
import com.example.gbHelpers.PingThread;



public class VideoPlayerActivity extends ActionBarActivity {

	private PingThread pingerThread;
	private TextView text;
	private float videoTime;
	private VideoView video;
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    this.setContentView(R.layout.activity_video_player);
		setContentView(R.layout.activity_video_player);
	   

		// initialize views
	    video = (VideoView)findViewById(R.id.videoView1);
		text = (TextView)findViewById(R.id.textView1);
		text.setRotation(90);
		text.setText("Connecting...");
	
		// create pinger thread
		pingerThread = new PingThread(this);
		pingerThread.setDaemon(true);
		pingerThread.start();
	}

	// updateVideo is called by pingerThread with the start time and user Count
	// updateVideo sets the video time to the passed time and the user count to the passed userCount
	public void updateVideo(int start, long userCount){
		final int s = start;
		final double u = userCount;
//		Log.w("Video startTime :" , s+" ");
		
		runOnUiThread(new Runnable(){
			@Override
			public void run(){
				text.setText("breathing with " + (int)u +" others");
				VideoView video = (VideoView)findViewById(R.id.videoView1);
				
				video.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
					@Override
					public void onPrepared(MediaPlayer mp) {
//						Log.w("Video startTime :" , s+" ");
						mp.seekTo(s);
						mp.setLooping(true);
					}
				});
				String path = "android.resource://"+getPackageName()+"/"+R.raw.pcm;
				video.setVideoURI(Uri.parse(path));
				video.start();
			}
		});
	}
	


    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
//		Log.w("RESUME : ","" );

        pingerThread.setRunning(true);
    }
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
//		Log.w("PAWZ : ","" );

        pingerThread.setRunning(false);

	    }
    @Override 
    public void onStop(){
    	super.onStop();
//		Log.w("STOP : ","" );

    	pingerThread.setRunning(false);
    }


}

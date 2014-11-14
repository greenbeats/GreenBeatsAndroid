package com.example.greenbeatsv2;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.VideoView;

import com.example.gbHelpers.PingThread;

public class VideoPlayerActivity extends ActionBarActivity {

	private PingThread pingerThread;
	private TextView text;
	private float videoTime;
	private VideoView video;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_video_player);
		
		video = (VideoView)findViewById(R.id.videoView1);
		text = (TextView)findViewById(R.id.textView1);
		
		pingerThread = new PingThread(this);
		pingerThread.start();
		
		

		
	}
	public void updateVideo(int start){
		final int s = start;
		Log.w("Video startTime :" , start+" ");
		
		runOnUiThread(new Runnable(){
			@Override
			public void run(){
				text.setText(s+"");
				VideoView video = (VideoView)findViewById(R.id.videoView1);

				video.setOnPreparedListener(new MediaPlayer.OnPreparedListener(){
					
					@Override
					public void onPrepared(MediaPlayer mp) {
						mp.seekTo(1000);
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
		Log.w("RESUME : ","" );

        pingerThread.setRunning(true);
    }
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first
		Log.w("PAWZ : ","" );

        pingerThread.setRunning(false);

	    }
    @Override 
    public void onStop(){
    	super.onStop();
		Log.w("STOP : ","" );

    	pingerThread.setRunning(false);
    }


}

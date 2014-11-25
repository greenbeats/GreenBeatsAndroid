/*
*
* Author: George Macrae 
*
* 2014
*
*/


package com.example.greenbeatsv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {
	private ImageView bg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Remove title bar
	    this.requestWindowFeature(Window.FEATURE_NO_TITLE);

	    //Remove notification bar
	    this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	    
	    this.setContentView(R.layout.activity_main);
		setContentView(R.layout.activity_main);

	    bg = (ImageView)findViewById(R.id.imageView1);
		
		backGroundListener();
	}
	
	// on touch switch to video player activity
	private void backGroundListener() {
		bg.setOnTouchListener(new OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
            	switchToVideoActivity();
                return true;
            }
       });
	}
	private void switchToVideoActivity(){
		startActivity(new Intent(MainActivity.this,VideoPlayerActivity.class));
	}

}

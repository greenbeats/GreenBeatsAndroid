package com.example.greenbeatsv2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class MainActivity extends ActionBarActivity {
	private ImageView bg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		bg = (ImageView)findViewById(R.id.imageView1);
		
		backGroundListener();
	}
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
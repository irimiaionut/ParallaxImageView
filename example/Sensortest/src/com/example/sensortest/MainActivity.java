package com.example.sensortest;

import com.android.irimiaionut.parallaxImageView.ParallaxImageView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity  {
	
    
    TextView welcome;
	
	
    ParallaxImageView backgr;
	LinearLayout logo_holder;
	
	 Animation anim_logo;
	 Animation anim_welcome;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		backgr = (ParallaxImageView) findViewById(R.id.backgr);
		backgr.setMargins(800, 500);
		backgr.setMultipliers(2.5f, 2.7f);

		welcome = (TextView) findViewById(R.id.welcome);
		logo_holder = (LinearLayout) findViewById(R.id.logo_holder);
	    welcome.setVisibility(View.INVISIBLE);
	    logo_holder.setVisibility(View.INVISIBLE);
	    
	    anim_logo = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_info_popup);
	    anim_welcome = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.anim_info_popup);
	}  
	
	protected void onStart(){
		super.onStart();
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings){
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	
	protected void onPause() {
	    super.onPause();
	    backgr.onPause();
	    welcome.setVisibility(View.INVISIBLE);
	    logo_holder.setVisibility(View.INVISIBLE);
	}
	
	protected void onResume() {
	    super.onResume();
	    backgr.onResume();
	    welcome.startAnimation(anim_welcome);
	    
	    Handler handler = new Handler();
		Runnable startMain = new Runnable() {

			@Override
			public void run() {
				if (!isFinishing()){
					logo_holder.startAnimation(anim_logo);
				}
			}
		};
		
		handler.postDelayed(startMain, 200);
	    
	}
}

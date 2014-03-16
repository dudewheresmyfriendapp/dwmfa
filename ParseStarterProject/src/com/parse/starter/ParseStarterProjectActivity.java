package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.parse.ParseAnalytics;

public class ParseStarterProjectActivity extends Activity {
	
	public static String MYTAG = "MYTAG";
	
	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		ParseAnalytics.trackAppOpened(getIntent());
		
		Log.d(MYTAG, "inside ParseStarterProjectActivity");
		
		startActivity(new Intent(this, SignUpOrLogInActivity.class));
		
	}
}

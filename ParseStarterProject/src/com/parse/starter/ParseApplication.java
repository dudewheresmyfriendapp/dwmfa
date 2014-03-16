package com.parse.starter;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseUser;

public class ParseApplication extends Application {
	
	public static String MYTAG = "MYTAG";

	@Override
	public void onCreate() {
		super.onCreate();

		// Add your initialization code here
		Parse.initialize(this, "COcz6qKshMaNYndoDgTKMQA6Rav7iU16XUyMJGdZ", "icmO4R1cq4JnZWkArIJJM8DlcP5xXf7lcJIeqi6N");


		ParseUser.enableAutomaticUser();
		ParseACL defaultACL = new ParseACL();
	    
		// If you would like all objects to be private by default, remove this line.
		defaultACL.setPublicReadAccess(true);
		
		ParseACL.setDefaultACL(defaultACL, true);
		
		Log.d(MYTAG, "done with app setup");
	}

}

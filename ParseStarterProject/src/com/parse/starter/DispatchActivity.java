package com.parse.starter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.parse.ParseUser;

/**
 * Activity which starts an intent for either the logged in (MainActivity) or logged out
 * (SignUpOrLoginActivity) activity.
 */
public class DispatchActivity extends Activity {

	public static String MYTAG = "MYTAG";
	
  public DispatchActivity() {
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    Log.d(MYTAG, "inside DispatchActivity");
    
    // Check if there is current user info
    if (ParseUser.getCurrentUser() != null) {
      // Start an intent for the logged in activity
      startActivity(new Intent(this, HomeScreenActivity.class));
    } else {
      // Start and intent for the logged out activity
      startActivity(new Intent(this, SignUpOrLogInActivity.class));
    }
  }

}

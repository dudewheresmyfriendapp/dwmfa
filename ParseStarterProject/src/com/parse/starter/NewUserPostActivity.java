package com.parse.starter;

import android.app.Activity;
import android.content.IntentSender;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient;
import com.google.android.gms.location.LocationClient;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class NewUserPostActivity extends Activity implements
GooglePlayServicesClient.ConnectionCallbacks,
GooglePlayServicesClient.OnConnectionFailedListener{

	private Button saveButton;
	private Button cancelButton;
	private TextView postContent;
	private TextView postDescription;
	private LocationClient locClient;
	public static double group_num;
	public static String MYTAG = "MYTAG";
	private final static int
    CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user_post);
		
		
		// get group_num
		Bundle extras;
		if (savedInstanceState == null) {
			extras = getIntent().getExtras();
		    if(extras == null) {
		        ;
		    } else {
		    	group_num = extras.getDouble("group_num");
		    	Log.d(MYTAG, "group_num from bundle is: " + group_num);
		    }
		} else {
			group_num = (Double) savedInstanceState.getSerializable("group_num");
		}
		
		Log.d(MYTAG, "group_num in NewUserPost is: "+ group_num);
		
		
		
		postContent = ((EditText) findViewById(R.id.blog_post_content));
		postDescription = ((EditText) findViewById(R.id.blog_post_description));
		locClient = new LocationClient(this, this, this);
		locClient.connect();

		saveButton = ((Button) findViewById(R.id.save_button));
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				Location lastLoc = locClient.getLastLocation();
				final double lastLat = lastLoc.getLatitude();
				final double lastLong = lastLoc.getLongitude();
				
				// When the user clicks "Save," upload the post to Parse
				// Create the Post object
				ParseObject post = new ParseObject("Post");
				post.put("textContent", postContent.getText().toString());
				post.put("textDescription", postDescription.getText().toString());
				post.put("latitude", lastLat);
				post.put("longitude", lastLong);
				post.put("user", ParseUser.getCurrentUser().getUsername());
				

				// Create an author relationship with the current user
				post.put("author", ParseUser.getCurrentUser());
				// possibly assign facebook account to this user number
				
				// Create a group relationship with the post
				
				Log.d(MYTAG, "group_num is: " + group_num);
				post.put("group", group_num);
				post.put("author", ParseUser.getCurrentUser());
				
				locClient.disconnect();
				
				// Save the post and return
				post.saveInBackground(new SaveCallback () {

					@Override
					public void done(ParseException e) {
						if (e == null) {
							setResult(RESULT_OK);
							finish();
						} else {
							Toast.makeText(getApplicationContext(), 
									"Error saving: " + e.getMessage(), Toast.LENGTH_SHORT).show();
						}
					}

					
				});

			}
		});

		cancelButton = ((Button) findViewById(R.id.cancel_button));
		cancelButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				locClient.disconnect();
				setResult(RESULT_CANCELED);
				finish();
			}
		});
	}
		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_group, menu);
		return true;
	}
	
	/*
     * Called by Location Services when the request to connect the
     * client finishes successfully. At this point, you can
     * request the current location or start periodic updates
     */
    @Override
    public void onConnected(Bundle dataBundle) {
        // Display the connection status
        Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();

    }
    
    /*
     * Called by Location Services if the connection to the
     * location client drops because of an error.
     */
    @Override
    public void onDisconnected() {
        // Display the connection status
        Toast.makeText(this, "Disconnected. Please re-connect.",
                Toast.LENGTH_SHORT).show();
    }
    
    /*
     * Called by Location Services if the attempt to
     * Location Services fails.
     */
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects.
         * If the error has a resolution, try sending an Intent to
         * start a Google Play services activity that can resolve
         * error.
         */
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(
                        this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            /*
             * If no resolution is available, display a dialog to the
             * user with the error.
             */
            //showErrorDialog(connectionResult.getErrorCode());
        }
    }

}

package com.parse.starter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class NewGroupActivity extends Activity {

	private Button saveButton;
	private Button cancelButton;
	private TextView postContent;
	public static double groupNum;
	public static String MYTAG = "MYTAG";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_group);
		
		
		postContent = ((EditText) findViewById(R.id.blog_post_content));

		saveButton = ((Button) findViewById(R.id.save_button));
		saveButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// When the user clicks "Save," upload the post to Parse
				// Create the Post object
				ParseObject group = new ParseObject("Group");
				group.put("textContent", postContent.getText().toString());

				// Create an author relationship with the current user
				group.put("author", ParseUser.getCurrentUser());
				// possibly assign facebook account to this user number
				
				// Create a group relationship with the post
				
				// create unique group
				groupNum = (double) (1 + Math.random()*484393482727123.0);
				//groupNum = (double) 3.0;
				Log.d(MYTAG, "groupNum is: " + groupNum);
				group.put("group", groupNum);
				group.put("author", ParseUser.getCurrentUser());
				
				// Save the post and return
				group.saveInBackground(new SaveCallback () {

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

}






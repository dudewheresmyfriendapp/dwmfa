package com.parse.starter;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class HomeScreenActivity extends ListActivity {

	public static String MYTAG = "MYTAG";
	private ArrayList<String> groups;
	public static ArrayList<ParseObject> global_postList;
	public static ArrayAdapter<String> adapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_screen);
		
		
		Log.d(MYTAG, "inside HomeScreenActivity");
		
		
		ParseAnalytics.trackAppOpened(getIntent());
		groups = new ArrayList<String>();
		updatePostList();
		Log.d(MYTAG, "groups instantiated");
		
		global_postList = new ArrayList<ParseObject>();
		Log.d(MYTAG, "global_postList instantiated");
		
		adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, groups);
		setListAdapter(adapter);
		adapter.notifyDataSetChanged();
		updatePostList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_screen, menu);
		return true;
	}

	
	/*
	 * Creating posts and refreshing the list will be controlled from the Action
	 * Bar.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

		case R.id.action_create_group: {
			newGroup();
			break;
		}

		case R.id.action_refresh: {
			updatePostList();
			break;
		}

		case R.id.logout_button: {
			// Call the Parse log out method
		    ParseUser.logOut();
		    // Start and intent for the dispatch activity
		    Intent intent = new Intent(HomeScreenActivity.this, DispatchActivity.class);
		    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
		    intent.putExtra("logged_out", true);
		    startActivity(intent);
			break;
		}
		}
		return super.onOptionsItemSelected(item);
	}
	
	private void updatePostList() {
		// Create query for objects of type "Post"
		ParseQuery<ParseObject> query = ParseQuery.getQuery("Group");
		groups.clear();

		// Restrict to cases where the author is the current user.
		// Note that you should pass in a ParseUser and not the
		// String reperesentation of that user
		//query.whereEqualTo("author", ParseUser.getCurrentUser(), "com.parse.ParseUser@41e69fc0");
		//query.whereEqualTo("author", "com.parse.ParseUser@41e69fc0");
		
		Log.d(MYTAG, "current user is: " + ParseUser.getCurrentUser());
		query.whereEqualTo("author", ParseUser.getCurrentUser());
		
		// Run the query
		query.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> postList, ParseException e) {
				if (e == null) {
					// If there are results, update the list of posts
					// and notify the adapter
					groups.clear();
					Log.d(MYTAG, "groups.clear");
					global_postList.clear();
					Log.d(MYTAG, "global_postList.clear");
					
					for (ParseObject post : postList) {
						groups.add(post.getString("textContent"));
						//Log.d(MYTAG, "groups.add");
						global_postList.add(post);
						//Log.d(MYTAG, "global_postList.add");
					}
					((ArrayAdapter<String>) getListAdapter())
							.notifyDataSetChanged();
				} else {
					Log.d("Post retrieval", "Error: " + e.getMessage());
				}

			}

		});

	}
	
	@Override
	protected void onListItemClick (ListView l, View v, int position, long id) {
	    //Toast.makeText(this, "Clicked row " + position, Toast.LENGTH_SHORT).show();
	    
		Log.d(MYTAG, "inside onListItemClick");
		
	    // get group id with position
	    ParseObject selected_group= new ParseObject("Group");
	    Log.d(MYTAG, "selected_group instantiated");
	    
	    selected_group = global_postList.get(position);
	    Log.d(MYTAG, "selected group = global_postlist");
	    
	    double group_num = selected_group.getDouble("group");
	    
	    //String group_number;
	    //Log.d(MYTAG, "double assigned");
	    
	    //group_number = (String) selected_group.get("group");
	    //Log.d(MYTAG, "double group_number assigned");
	    
	    Log.d(MYTAG, "group ID: " + selected_group.get("group"));
	    Toast.makeText(this, "Clicked row " + position + " group num: " + selected_group.get("group"), Toast.LENGTH_SHORT).show();
	    Log.d(MYTAG, "finished Toast");
	    Log.d(MYTAG, "group_num variable: " + group_num);
	    
	    
	    /*
	     * ADD ADD_USER NEW ACTIVITY INTENT HERE
	     * 
	     * attach "group_number" to intent w/ putExtra
	     */
	    Intent i = new Intent(HomeScreenActivity.this, PostListActivity.class);
	    Log.d(MYTAG, "created intent");
	    i.putExtra("group_num", group_num);
	    Log.d(MYTAG, "put extra");
	    startActivityForResult(i,2);
	    
	}

	private void newGroup() {
		
		Intent i = new Intent(HomeScreenActivity.this, NewGroupActivity.class);
		startActivityForResult(i, 1);
		
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK && requestCode == 1) {
			// If a new post has been added, update
			// the list of posts
			Log.d(MYTAG, "HomeScreen onActivityResult code 1");
			adapter.notifyDataSetChanged();
			updatePostList();
		}
		else if (resultCode == Activity.RESULT_OK && requestCode == 2){
			// returning from PostListActivity
			Log.d(MYTAG, "HomeScreen onActivityResult code 1");
			adapter.notifyDataSetChanged();
			updatePostList();
		}
	}
	
	
	
	public void logOut(){
		// Call the Parse log out method
	    ParseUser.logOut();
	    // Start and intent for the dispatch activity
	    Intent intent = new Intent(HomeScreenActivity.this, DispatchActivity.class);
	    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
	    startActivity(intent);
	}

	
}






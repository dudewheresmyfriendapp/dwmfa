package com.parse.starter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

public class PostListActivity extends ListActivity {

	public static String MYTAG = "MYTAG";
	public Context context;
	public static double group_num;
	
	
	private ArrayList<String> posts;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		
		Log.d(MYTAG, "in PostListActivity");
		Bundle fake = new Bundle();
		
		super.onCreate(savedInstanceState);
		//super.onCreate(fake);
		
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
		
		//updatePostList_PLA();
		
		ParseAnalytics.trackAppOpened(getIntent());
		posts = new ArrayList<String>();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, posts);
		setListAdapter(adapter);
		Log.d(MYTAG, "calling updatePostList...");
		updatePostList_PLA();
		
	
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.post_list, menu);
		return true;
	}
	

	/*
	 * Creating posts and refreshing the list will be controlled from the Action
	 * Bar.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {

			case R.id.action_refresh: {
				updatePostList_PLA();
				break;
			}
			
	
			case R.id.action_add_user: {
				newPost();
				break;
			}
			
			case R.id.action_return_to_groups: {
				//group_num = (Double) null;
				setResult(RESULT_OK);
				finish();
			}
		
		}
		return super.onOptionsItemSelected(item);
	}
	
	protected void onListItemClick (ListView l, View v, int position, long id) {
		
		Intent i = new Intent(PostListActivity.this, ShowMapActivity.class);
	    Log.d(MYTAG, "created intent");
	    startActivityForResult(i,3);
		
	}
	
	

	private void updatePostList_PLA() {
		// Create query for objects of type "Post"
		Log.d(MYTAG, "inside upDatePostList() in PLA");
		ParseQuery<ParseObject> query_posts = ParseQuery.getQuery("Post");
		//posts.clear();

		// Restrict to cases where the author is the current user.
		// Note that you should pass in a ParseUser and not the
		// String reperesentation of that user
		//query.whereEqualTo("author", ParseUser.getCurrentUser(), "com.parse.ParseUser@41e69fc0");
		//query.whereEqualTo("author", "com.parse.ParseUser@41e69fc0");
		
		Log.d(MYTAG, "group_num query is: " + group_num);
		query_posts.whereEqualTo("group", group_num);
		
		//Log.d(MYTAG, "author is: " + ParseUser.getCurrentUser());
		// Run the query
		query_posts.findInBackground(new FindCallback<ParseObject>() {

			@Override
			public void done(List<ParseObject> postList, ParseException e) {
				if (e == null) {
					// If there are results, update the list of posts
					// and notify the adapter
					posts.clear();
					for (ParseObject post : postList) {
						posts.add(post.getString("textContent"));
					}
					((ArrayAdapter<String>) getListAdapter())
							.notifyDataSetChanged();
				} else {
					Log.d("Post retrieval", "Error: " + e.getMessage());
				}

			}

		});

	}

	private void newPost() {
		Intent i = new Intent(PostListActivity.this, NewUserPostActivity.class);
		i.putExtra("group_num", group_num);
		startActivityForResult(i, 0);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == Activity.RESULT_OK) {
			// If a new post has been added, update
			// the list of posts
			updatePostList_PLA();
		}
	}

}
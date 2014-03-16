package com.parse.starter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.parse.ParseUser;

public class NFCGroupActivity extends Activity {
	
	//public static ArrayList<int> active_groups;
	List<Float> active_groups = new ArrayList<Float>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nfcgroup);
		
		
	}
	
	
	public void add_group_num(float group_num){
		ParseUser current_user = ParseUser.getCurrentUser();
		
		// copy from server
		active_groups = (List<Float>) current_user.get("active_groups");
		// add new group to local array of groups
		active_groups.add(group_num);
		// update array on parse server
		current_user.put("active_groups", active_groups);
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.nfcgroup, menu);
		return true;
	}

}

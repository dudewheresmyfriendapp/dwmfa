package com.parse.starter;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ShowMapActivity extends Activity {
  private LatLng LOCATION;
  private GoogleMap map;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show_map);
    
    Bundle extras;
	extras = getIntent().getExtras();
	LOCATION = new LatLng(extras.getDouble("latitude"), extras.getDouble("longitude"));
    
    
    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
        .getMap();
    Marker locMarker = map.addMarker(new MarkerOptions()
        .position(LOCATION)
        .title(extras.getString("content") + " - User: " + extras.getString("user"))
        .snippet(extras.getString("description")));

    map.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION, 15));

    //map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.show_map, menu);
    return true;
  }

} 

package com.parse.starter;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class ShowMapActivity extends Activity {
  static final LatLng ISLAVISTA = new LatLng(34.4347, 119.8606);
  static final LatLng NEWBURYPARK = new LatLng(34.1842, 118.9107);
  private GoogleMap map;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_show_map);
    map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
        .getMap();
    Marker islaVista = map.addMarker(new MarkerOptions().position(ISLAVISTA)
        .title("Isla Vista"));
    Marker newburyPark = map.addMarker(new MarkerOptions()
        .position(NEWBURYPARK)
        .title("Home")
        .snippet("Home is where the heart is.")
        .icon(BitmapDescriptorFactory
            .fromResource(R.drawable.ic_launcher)));

    // Move the camera instantly to hamburg with a zoom of 15.
    map.moveCamera(CameraUpdateFactory.newLatLngZoom(ISLAVISTA, 15));

    // Zoom in, animating the camera.
    map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.show_map, menu);
    return true;
  }

} 

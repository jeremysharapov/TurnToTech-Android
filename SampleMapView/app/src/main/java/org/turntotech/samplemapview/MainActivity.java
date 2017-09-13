/*
 * This example aims to give knowledge about how to implements newer Google Maps
 * into our applications.
 * But here we need to include 
 *  1. Google Maps API key.
 * 	2. we need to use Google Play Services project as a library to use project.
 * 	3. Google maps needs some permissions and features which we can see in manifest file.
 */


package org.turntotech.samplemapview;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("TurnToTech", "Project Name - SampleMapView");
        // Google Map
        GoogleMap map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
        // Create a LatLng object
        LatLng latlong = new LatLng(40.7413351,-73.9898172);
        map.setMyLocationEnabled(true);
        
        // focus map to particular place 
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(latlong, 17));
        // Markers identify locations on the map.
        map.addMarker(new MarkerOptions()
                .title("TurnToTech")
                .snippet("Best place to learn")
                .position(latlong));
        
    }

}

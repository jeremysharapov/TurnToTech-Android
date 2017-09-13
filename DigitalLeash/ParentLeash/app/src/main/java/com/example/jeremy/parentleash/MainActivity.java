package com.example.jeremy.parentleash;

import android.*;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;


import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    static EditText username, latitude, longitude, radius;
    static String json_url;
    static String Username, Latitude, Longitude, Radius;
    double lat;
    double lon;
    private GoogleApiClient googleApiClient;
    private static final int MY_PERMISSION_ACCESS_COURSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.Username);
        latitude = (EditText) findViewById(R.id.Latitude);
        longitude = (EditText) findViewById(R.id.Longitude);
        radius = (EditText) findViewById(R.id.Radius);

        googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();
        googleApiClient.connect();

        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { android.Manifest.permission.ACCESS_COARSE_LOCATION },
                    MY_PERMISSION_ACCESS_COURSE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_ACCESS_COURSE_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "All good", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Need your location!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(MainActivity.class.getSimpleName(), "Connected to Google Play Services!");

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Location lastLocation = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);

            lat = lastLocation.getLatitude();
            lon = lastLocation.getLongitude();
        }
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.i(MainActivity.class.getSimpleName(), "Can't connect to Google Play Services!");
    }


    public void createProfile(View view) {
        Username = username.getText().toString();
        Latitude = latitude.getText().toString();
        Longitude = longitude.getText().toString();
        Radius = radius.getText().toString();
        json_url = "https://turntotech.firebaseio.com/digitalleash/" + Username + ".json";
        new GetData().execute(json_url);
    }

    public class GetData extends AsyncTask<String, String, Void> {

        HttpURLConnection urlConnection;

        @Override
        protected Void doInBackground(String... args) {


            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username", MainActivity.Username);
                jsonObject.put("latitude", MainActivity.Latitude);
                jsonObject.put("longitude", MainActivity.Longitude);
                jsonObject.put("radius", MainActivity.Radius);
                String json = jsonObject.toString();
                URL url = new URL(args[0]);

                HttpURLConnection urlConnectionCheck = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnectionCheck.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = reader.readLine();
                //JSONObject json2 = new JSONObject(line);
                if (!(line.equalsIgnoreCase("null"))) {
                    urlConnectionCheck.disconnect();
                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.this, "Error: Username already exists, use update to update information", Toast.LENGTH_LONG).show();
                        }
                    });
                    return null;
                }
                urlConnectionCheck.disconnect();

                HttpURLConnection httpCon = (HttpURLConnection) url.openConnection();
                httpCon.setRequestMethod("PUT");
                httpCon.setDoOutput(true);
                httpCon.setRequestProperty("Content-Type", "application/json");
                httpCon.setRequestProperty("Accept", "application/json");
                httpCon.setRequestProperty( "charset", "utf-8");
                httpCon.setRequestProperty( "Content-Length", Integer.toString( json.length() ));
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpCon.getOutputStream());
                outputStreamWriter.write(json);
                outputStreamWriter.flush();
                outputStreamWriter.close();
                Log.e("ConnectionStatus", String.valueOf(httpCon.getResponseCode()));
                httpCon.disconnect();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivity.this, "Information Registered!", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
//                runOnUiThread(new Runnable() {
//                    public void run() {
//                        Toast.makeText(MainActivity.this, "Error: Username already exists, use update to update information", Toast.LENGTH_LONG).show();
//                    }
//                });
                e.printStackTrace();
            }
            return null;
        }
    }

    public void Update(View view){
        Username = username.getText().toString();
        Latitude = latitude.getText().toString();
        Longitude = longitude.getText().toString();
        Radius = radius.getText().toString();
        json_url = "https://turntotech.firebaseio.com/digitalleash/" + Username + ".json";
        new GetUpdate().execute(json_url);
    }

    public void UpdateGPS(View view){
        Username = username.getText().toString();
        Latitude = String.valueOf(lat);
        latitude.setText(Latitude);
        Longitude = String.valueOf(lon);
        longitude.setText(Longitude);
        Radius = radius.getText().toString();
        json_url = "https://turntotech.firebaseio.com/digitalleash/" + Username + ".json";
        new GetUpdate().execute(json_url);
    }

    public class GetUpdate extends AsyncTask<String, String, Void> {

        HttpURLConnection urlConnection, urlConnectionUpdate;

        @Override
        protected Void doInBackground(String... args) {
            try {
                URL url = new URL(args[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = reader.readLine();
                JSONObject json2 = new JSONObject(line);
                json2.getString("username");
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("username", MainActivity.Username);
                jsonObject.put("latitude", MainActivity.Latitude);
                jsonObject.put("longitude", MainActivity.Longitude);
                jsonObject.put("radius", MainActivity.Radius);
                String json = jsonObject.toString();

                urlConnectionUpdate = (HttpURLConnection) url.openConnection();
                urlConnectionUpdate.setRequestMethod("PATCH");
                urlConnectionUpdate.setDoOutput(true);
                urlConnectionUpdate.setRequestProperty("Content-Type", "application/json");
                urlConnectionUpdate.setRequestProperty("Accept", "application/json");
                urlConnectionUpdate.setRequestProperty( "charset", "utf-8");
                urlConnectionUpdate.setRequestProperty( "Content-Length", Integer.toString( json.length() ));
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(urlConnectionUpdate.getOutputStream());
                outputStreamWriter.write(json);
                outputStreamWriter.flush();
                outputStreamWriter.close();
                Log.e("ConnectionStatus", String.valueOf(urlConnectionUpdate.getResponseCode()));
                urlConnectionUpdate.disconnect();
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivity.this, "Information Updated!", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivity.this, "Error: Username was not created yet!", Toast.LENGTH_LONG).show();
                    }
                });
                e.printStackTrace();
            }
            finally {
                urlConnection.disconnect();
            }
            return null;
        }

    }

    public void childCheck(View view){
        Username = username.getText().toString();
        json_url = "https://turntotech.firebaseio.com/digitalleash/" + Username + ".json";
        new GetChild().execute(json_url);
    }


    public class GetChild extends AsyncTask<String, String, Void> {
        HttpURLConnection urlConnection;

        @Override
        protected Void doInBackground(String... args) {
            try {
                URL url = new URL(args[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = reader.readLine();
                JSONObject json = new JSONObject(line);
                double childLat = Double.parseDouble(json.getString("childLatitude"));
                double childLong = Double.parseDouble(json.getString("childLongitude"));
                Location parent = new Location("");
                double parLat = Double.parseDouble(json.getString("latitude"));
                double cparLong = Double.parseDouble(json.getString("longitude"));
                parent.setLatitude(parLat);
                parent.setLongitude(cparLong);
                Location child = new Location("");
                child.setLatitude(childLat);
                child.setLongitude(childLong);
                if (parent.distanceTo(child) > Integer.parseInt(json.getString("radius"))) {
                    startActivity(new Intent(MainActivity.this, OutOfRange.class));
                } else {
                    startActivity(new Intent(MainActivity.this, InRange.class));
                }
            } catch (Exception e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivity.this, "Error: Child didn't report location!", Toast.LENGTH_LONG).show();
                    }
                });
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return null;
        }
    }
}

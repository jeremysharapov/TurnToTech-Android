package com.example.jeremy.childleash;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    static String json_url;
    static EditText parent;
    static String Parent;
    double lat;
    double lon;
    private GoogleApiClient googleApiClient;
    private static final int MY_PERMISSION_ACCESS_COURSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parent = (EditText) findViewById(R.id.editText);

        googleApiClient = new GoogleApiClient.Builder(this, this, this).addApi(LocationServices.API).build();
        googleApiClient.connect();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] { Manifest.permission.ACCESS_COARSE_LOCATION },
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

    public void Report(View view) {
        Parent = parent.getText().toString();
        json_url = "https://turntotech.firebaseio.com/digitalleash/" + Parent + ".json";
        new GetData().execute(json_url);
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


    public class GetData extends AsyncTask<String, String, Void> {
        HttpURLConnection urlConnection,urlConnectionUpdate;

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
                jsonObject.put("childLatitude", String.valueOf(lat));
                jsonObject.put("childLongitude", String.valueOf(lon));
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
                        Toast.makeText(MainActivity.this, "Location Sent!", Toast.LENGTH_LONG).show();
                    }
                });
            } catch (Exception e) {
                runOnUiThread(new Runnable() {
                    public void run() {
                        Toast.makeText(MainActivity.this, "Error: Parent Not Found!", Toast.LENGTH_LONG).show();
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
}

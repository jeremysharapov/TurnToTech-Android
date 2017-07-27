package com.example.jeremy.parentleash;

import android.content.Intent;
import android.location.Location;
import android.os.AsyncTask;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    static EditText username, latitude, longitude, radius;
    static String json_url;
    static String Username, Latitude, Longitude, Radius;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = (EditText) findViewById(R.id.Username);
        latitude = (EditText) findViewById(R.id.Latitude);
        longitude = (EditText) findViewById(R.id.Longitude);
        radius = (EditText) findViewById(R.id.Radius);
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
                int childLat = Integer.parseInt(json.getString("childLatitude"));
                int childLong = Integer.parseInt(json.getString("childLongitude"));
                Location parent = new Location("");
                int parLat = Integer.parseInt(json.getString("latitude"));
                int cparLong = Integer.parseInt(json.getString("longitude"));
                parent.setLatitude(parLat);
                parent.setLongitude(cparLong);
                Location child = new Location("");
                child.setLatitude(childLat);
                child.setLongitude(childLong);
                if (parent.distanceTo(child) > Integer.parseInt(json.getString("childLongitude"))) {
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

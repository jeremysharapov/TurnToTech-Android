package com.example.jeremy.childleash;

import android.location.Location;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    static String json_url;
    static EditText parent;
    static String Parent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        parent = (EditText) findViewById(R.id.editText);
    }

    public void Report(View view) {
        Parent = parent.getText().toString();
        json_url = "https://turntotech.firebaseio.com/digitalleash/" + Parent + ".json";
        new GetData().execute(json_url);
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
                jsonObject.put("childLatitude", "40");
                jsonObject.put("childLongitude", "40");
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

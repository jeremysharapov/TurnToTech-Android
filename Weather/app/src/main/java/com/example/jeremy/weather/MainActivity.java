package com.example.jeremy.weather;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.TestLooperManager;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.jeremy.weather.R.attr.background;

public class MainActivity extends AppCompatActivity {

    TextView Low, High, Temp;
    EditText Zip;
    Button Refresh;
    public static final String prefs_name = "MyPreferencesFile";
    ConstraintLayout lay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Low = (TextView)findViewById(R.id.Low);
        High = (TextView)findViewById(R.id.High);
        Temp = (TextView)findViewById(R.id.curTemp);
        Zip = (EditText)findViewById(R.id.ZipCode);
        Refresh = (Button)findViewById(R.id.Refresh);
        lay = (ConstraintLayout)findViewById(R.id.lay1);
    }

    public void click(View view) {
        SharedPreferences settings = getSharedPreferences(prefs_name, 0);
        String temp = settings.getString("zip", "00000");
        if (Zip.getText().toString().equals(temp)) {
            //String tempResult = "{\"coord\":{\"lon\":-73.83,\"lat\":40.71},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10d\"},{\"id\":701,\"main\":\"Mist\",\"description\":\"mist\",\"icon\":\"50d\"}],\"base\":\"stations\",\"main\":{\"temp\":292.32,\"pressure\":1017,\"humidity\":88,\"temp_min\":291.15,\"temp_max\":294.15},\"visibility\":6437,\"wind\":{\"speed\":4.81,\"deg\":176.001},\"clouds\":{\"all\":90},\"dt\":1502134500,\"sys\":{\"type\":1,\"id\":1969,\"message\":0.0045,\"country\":\"US\",\"sunrise\":1502099930,\"sunset\":1502150543},\"id\":0,\"name\":\"Kew Gardens\",\"cod\":200}";
            String tempResult = settings.getString("tempstring", "");
            updateTemps(tempResult);
        } else {
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("zip", Zip.getText().toString());
            editor.commit();
            sendVolleyRequest(this);
        }
    }

    public void sendVolleyRequest(Context context){
        // http://samples.openweathermap.org/data/2.5/weather?zip=94040,us&appid=b1b15e88fa797225412429c1c50c122a1
        String URL = "http://api.openweathermap.org/data/2.5/weather?zip=" + Zip.getText().toString() + ",us&appid=" + "397976b6bba3fd5c99b576d05a0e45be";
        StringRequest stringRequest = new StringRequest(URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        updateTemps(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("V ERR","ERROR");
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void updateTemps(String result) {
        try {
            SharedPreferences settings = getSharedPreferences(prefs_name, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("tempstring", result);
            editor.commit();

            JSONObject json = new JSONObject(result);
            JSONObject json2 = json.getJSONObject("main");
            int temp = (int) Math.round(json2.getDouble("temp") * 9/5 - 459.67);
            int min = (int) Math.round(json2.getDouble("temp_min") * 9/5 - 459.67);
            int max = (int) Math.round(json2.getDouble("temp_max") * 9/5 - 459.67);
            Low.setText("Low:" + min + "°F");
            High.setText("High:" + max + "°F");
            Temp.setText(temp + "°F");

            JSONArray json3 = json.getJSONArray("weather");
            JSONObject json4 = json3.getJSONObject(0);
            switch (json4.getString("main")) {
                case "Clear": lay.setBackground(ContextCompat.getDrawable(this, R.drawable.clear));
                    break;
                case "Clouds": lay.setBackground(ContextCompat.getDrawable(this, R.drawable.clouds));
                    break;
                case "Rain": lay.setBackground(ContextCompat.getDrawable(this, R.drawable.rain));
                    break;
                case "Drizzle": lay.setBackground(ContextCompat.getDrawable(this, R.drawable.drizzle));
                    break;
                case "Snow": lay.setBackground(ContextCompat.getDrawable(this, R.drawable.snow));
                    break;
                case "Thunderstorm": lay.setBackground(ContextCompat.getDrawable(this, R.drawable.thunderstorm));
                    break;
                case "Atmosphere": lay.setBackground(ContextCompat.getDrawable(this, R.drawable.atmosphere));
                    break;
                case "Extreme": lay.setBackground(ContextCompat.getDrawable(this, R.drawable.extreme));
                    break;
                default: lay.setBackground(ContextCompat.getDrawable(this, R.drawable.clear));
                    break;
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }
}

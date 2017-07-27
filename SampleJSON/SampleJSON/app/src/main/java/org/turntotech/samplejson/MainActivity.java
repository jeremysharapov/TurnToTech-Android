/*
 * JSON is very light weight, structured, easy to parse and much human readable.
 * JSON is best alternative to XML when your android app needs to interchange data
 * with your server.
 * In this example we are going to learn how to parse JSON in android.
 */

package org.turntotech.samplejson;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

	private static String json_url = "http://api.themoviedb.org/3/movie/now_playing?api_key=b1e885f7a1a0602d435d8c52dc0de5f0";
	private ArrayAdapter<String> adapter;
	private List<String> movie_titles;
	List<String> movie_overviews;
	List<Double> movie_popularities;
	ListView listView;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listView = (ListView)findViewById(R.id.listView);
		movie_titles = new ArrayList<>();
        movie_overviews = new ArrayList<>();
        movie_popularities = new ArrayList<>();
		adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, movie_titles);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(this);
	}


	public void buttonClicked(View view) {
		movie_titles.clear();
		adapter.notifyDataSetChanged();
		if(view.getId()==R.id.buttonURL)new GetData().execute(json_url);
		if(view.getId()==R.id.buttonVolley)sendVolleyRequest();
	}




	public class GetData extends AsyncTask<String, String, String> {

		HttpURLConnection urlConnection;

		@Override
		protected String doInBackground(String... args) {

			StringBuilder result = new StringBuilder();

			try {
				URL url = new URL(args[0]);
				urlConnection = (HttpURLConnection) url.openConnection();

				InputStream in = new BufferedInputStream(urlConnection.getInputStream());

				BufferedReader reader = new BufferedReader(new InputStreamReader(in));

				String line;
				while ((line = reader.readLine()) != null) {
					result.append(line);
				}

			}catch( Exception e) {
				e.printStackTrace();
			}
			finally {
				urlConnection.disconnect();

		}

			return result.toString();
		}

		@Override
		protected void onPostExecute(String result) {

			//Do something with the JSON string
			populateList(result);
		}

	}


	private void sendVolleyRequest(){

		StringRequest stringRequest = new StringRequest(json_url,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						populateList(response);
					}
				},
				new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_LONG).show();
					}
				});

		RequestQueue requestQueue = Volley.newRequestQueue(this);
		requestQueue.add(stringRequest);
	}

	private void populateList(String result){

		try {
			JSONObject json = new JSONObject(result);
			JSONArray movies = json.getJSONArray("results");
			for (int i=0; i<movies.length(); i++) {
				JSONObject actor = movies.getJSONObject(i);
				String title = actor.getString("title");
				movie_titles.add(title);
				String overview = actor.getString("overview");
				movie_overviews.add(overview);
				Double popularity = actor.getDouble("popularity");
				movie_popularities.add(popularity);
			}
			adapter.notifyDataSetChanged();

		}catch (Exception e) { e.printStackTrace();}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(parent.getContext(), Info.class);

		// Add extended data to the intent.
		intent.putExtra("TITLE", movie_titles.get(position));
		intent.putExtra("OVERVIEW", movie_overviews.get(position));
		intent.putExtra("POPULARITY", movie_popularities.get(position));

		/*
		 * Launch a new activity. You will not receive any information about when
		 * the activity exits. This implementation overrides the base version,
		 * providing information about the activity performing the launch.
		 */
		startActivity(intent);
	}
}

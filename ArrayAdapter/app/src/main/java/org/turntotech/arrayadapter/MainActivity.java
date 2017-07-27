

package org.turntotech.arrayadapter;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends ListActivity{

	//ListView listView;
    String[] fruits;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("TurnToTech", "Project Name - Array Adapter");
		// 1. Create an array with the data we want to show in a table. Each element is 1 row.

		fruits = new String[]{"Dog", "Cat", "Bird", "Fish", "Bear", "Horse", "Cow", "Pig", "Monkey", "Mouse"};

		// 2. Define a new ArrayAdapter - which helps map an array to a list on the screen

		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
				R.layout.activity_main, fruits);


		final ListView listView = getListView();
		listView.setAdapter(arrayAdapter);


		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
									int position, long id) {
				//String s =  fruits[position];
                //android.util.Log.e("MSG",s);
				//Toast.makeText(getBaseContext(),s,Toast.LENGTH_LONG).show();
				Toast.makeText(getBaseContext(), String.valueOf(position), Toast.LENGTH_LONG).show();
			}});
	}


	}


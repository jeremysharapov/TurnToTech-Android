package org.turntotech.navigatesample;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;


public class ChildActivity extends ListActivity implements AdapterView.OnItemClickListener {
	//Intent intent = getIntent();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String[][] data = {
				{ "Galaxy Tab", "Galaxy Smart Phones", "Galaxy Gear" },
				{ "iPhone", "iPad", "iPod" } };
		int[][] icons = {
				{ R.drawable.gala, R.drawable.duos, R.drawable.star },
				{ R.drawable.a, R.drawable.b, R.drawable.c }, };
		Intent intent = getIntent();
		int position = intent.getIntExtra("POSITION1", 0);
		
		// Provide the cursor for the list view. 
		setListAdapter(new CustomListAdapter(this, data[position], icons[position]));
		getListView().setOnItemClickListener(this);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent intent = new Intent(parent.getContext(), DetailActivity.class);

		intent.putExtra("POSITION2", position);

		startActivity(intent);
	}

}

package org.turntotech.sqlitesample;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class MainActivity extends Activity {

	private SQLiteDatabase db;
	private ArrayList<HashMap<String, String>> data;
	private SimpleAdapter adapter;

	private EditText fieldName;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        Log.i("TurnToTech", "Project Name - SQLiteSample");

		fieldName = (EditText) findViewById(R.id.editText);

		ListView listView = (ListView) findViewById(R.id.listView);
		registerForContextMenu(listView);

		db = openOrCreateDatabase("androidsqlite.db", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS Student ( stud_id INTEGER PRIMARY KEY, stud_name TEXT)");

		data = new ArrayList<HashMap<String, String>>();
		adapter = new SimpleAdapter(this, data, R.layout.row_layout, new String[] { "stud_id", "stud_name" }, new int[] {R.id.stud_id, R.id.stud_name });
		listView.setAdapter(adapter);

		populateList();
	}

	private void populateList() {
		Cursor cursor = db.rawQuery("SELECT stud_id,stud_name FROM Student", null);
		data.clear();
		while (cursor.moveToNext()) {
			HashMap<String, String> map = new HashMap<String, String>();
			map.put("stud_id", cursor.getString(0));
			map.put("stud_name", cursor.getString(1));
			data.add(map);
		}
		cursor.close();
		adapter.notifyDataSetChanged();
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
		menu.add("Delete " + data.get(info.position).get("stud_name"));
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
		db.execSQL("delete from Student where stud_id=?", new String[] { data.get(info.position).get("stud_id") });
		populateList();
		return true;
	}

	public void addClicked(View view) {
		String name = fieldName.getText().toString();
		db.execSQL("insert into Student(stud_id,stud_name) values(?,?)", new String[] { String.valueOf(new Date().getTime()), name });
		fieldName.setText("");
		populateList();
	}

	@Override
	protected void onDestroy() {
		db.close();
		super.onDestroy();
	}

}

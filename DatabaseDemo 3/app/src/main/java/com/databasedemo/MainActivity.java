/*
 * Example of Simple SQLite project.
 * SQLite is an open-source SQL database that stores data to a text file on a device. 
 * Android comes in with built in SQLite database implementation. 
 * SQLite supports all the relational database features.
 */


package com.databasedemo;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {
	Button save,load;
	EditText name,email;
	DataHandler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        Log.i("TurnToTech", "Project Name - Database Demo");
		name = (EditText) findViewById(R.id.name);
		email = (EditText) findViewById(R.id.email);
		save=(Button) findViewById(R.id.save);
		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handler =new DataHandler(getBaseContext());
				handler.open();
				handler.insertData(name.getText().toString(), email.getText().toString());
				Toast.makeText(getBaseContext(), "DATA INSERTED", Toast.LENGTH_LONG).show();
				handler.close();
			}
		});
		
		load = (Button) findViewById(R.id.load);
		load.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				handler =new DataHandler(getBaseContext());
				handler.open();
				Cursor C = handler.returnData();
				if(C.moveToLast())
				{
					Toast.makeText(getBaseContext(), "NAME :"+C.getString(0)+" and Email : "+C.getString(1), Toast.LENGTH_LONG).show();
				}
				handler.close();
			}
		});

		// TODO: Call your new function that allows you at change only the name.
	}

}

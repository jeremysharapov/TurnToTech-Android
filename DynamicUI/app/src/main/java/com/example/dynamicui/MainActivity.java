/*
 * An alternative to writing XML resource files or using the Graphical Layout tool is 
 * to write Java code to directly create, configure and manipulate the view objects 
 * that comprise the user interface of an Android activity.
 * It is the example of how to create ui dynamically (without xml file).
 */

package com.example.dynamicui;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Button;
import android.app.AlertDialog;

public class MainActivity extends Activity 
{

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
        Log.i("TurnToTech", "Project Name - Dynamic UI");
		
		// At first we create layout
		android.widget.LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		LinearLayout layout = new LinearLayout(this);
		layout .setOrientation(LinearLayout.VERTICAL);
		
		// Creating text view programmatically  
		TextView txt = new TextView(this);
		txt.setText("Demo text view");
		txt.setLayoutParams(params);
		layout.addView(txt);
		
		final AlertDialog alertDialog;
		alertDialog = new AlertDialog.Builder(this).create();

		//Create button
		Button b1 = new Button(this);
		b1.setText("Demo Button 1");
		b1.setLayoutParams(params);
		layout.addView(b1);
		
		//If we click this button 
		b1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) 
			{
				alertDialog.setMessage("Button 1 clicked by user");
				alertDialog.show();
			}
		});
		
		Button b2 = new Button(this);
		b2.setText("Demo Button 2");
		b2.setLayoutParams(params);
		layout.addView(b2);
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				alertDialog.setMessage("Button 2 clicked by user");
				alertDialog.show();
			}
		});

		Button b3 = new Button(this);
		b3.setText("Demo Button 3");
		b3.setLayoutParams(params);
		layout.addView(b3);
		b3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				alertDialog.setMessage("This is a new button.");
				alertDialog.show();
			}
		});

		LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		this.addContentView(layout,layoutparams);
	}

}


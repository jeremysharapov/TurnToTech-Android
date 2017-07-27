/*
 * Android provides many ways of storing the data of an application. 
 * One of these ways is by using the Shared Preferences class.
 * Shared Preferences allow you to save and retrieve data in the form of key,value pair.
 * This a simple example of Shared Preferences which allows us to save changes
 * to the background color of the app.
 */

package org.turntotech.sharedpreference;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends Activity {

	private RadioGroup radioColorGroup;
	private RadioButton radioColorButton;
	public static final String prefs_name = "MyPreferencesFile";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        Log.i("TurnToTech", "Project Name - SharedPreference");

		radioColorGroup = (RadioGroup) findViewById(R.id.radioColor);
		radioColorGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			// Called when the checked state of a compound button has changed.
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				int selectedId = radioColorGroup.getCheckedRadioButtonId();
			    radioColorButton = (RadioButton) findViewById(selectedId);    

			    /*
			     * getSharedPreferences() - Retrieve and hold the contents of the 
			     * preferences file 'name', returning a SharedPreferences through 
			     * which you can retrieve and modify its values. 
			     */
				SharedPreferences settings = getSharedPreferences(prefs_name,0);
				
				// SharedPreferences.Editor - Interface used for modifying values in a SharedPreferences object. 
				SharedPreferences.Editor editor = settings.edit();
				
				// Set a String value in the preferences editor, to be written back once commit() or apply() are called.
				editor.putString("col",radioColorButton.getText().toString());
				
				editor.commit();
				setBackground();
				
			}
		});
		setBackground();
	}
	
	/*
	 * Change the background.
	 */
	private void setBackground(){
		    
			SharedPreferences settings = getSharedPreferences(prefs_name, 0);
			
			// Retrieve a String value from the preferences. default "Green"
		    String color_pref = settings.getString("col", "Green");
		    
		    // After retrieving change background color accordingly. 
		    if(color_pref.equals("Green")){
		    	radioColorGroup.setBackgroundColor(Color.parseColor("#99FFCC"));
		    	radioColorGroup.check(R.id.radioGreen);
		    }
		    if(color_pref.equals("Red")){
		    	radioColorGroup.setBackgroundColor(Color.parseColor("#FF6666"));
		    	radioColorGroup.check(R.id.radioRed);
		    }
		    if(color_pref.equals("Blue")){
		    	radioColorGroup.setBackgroundColor(Color.parseColor("#66CCFF"));
		    	radioColorGroup.check(R.id.radioBlue);
		    }
	}


	
}

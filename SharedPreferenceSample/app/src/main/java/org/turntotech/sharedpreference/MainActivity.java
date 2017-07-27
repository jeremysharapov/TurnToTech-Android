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
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SeekBar;

public class MainActivity extends Activity {

	LinearLayout mainLayout;
	private RadioGroup radioColorGroup;
	private RadioButton radioColorButton;
	public static final String prefs_name = "MyPreferencesFile";
	public static final String colors_name = "MyColorsFile";
	public static final String number_name = "MyNumberFile";
	public String color_pref;
	int color;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        Log.i("TurnToTech", "Project Name - SharedPreference");

		mainLayout = (LinearLayout)findViewById(R.id.linearLayout);
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
				SharedPreferences settings = getSharedPreferences(prefs_name, 0);

				// SharedPreferences.Editor - Interface used for modifying values in a SharedPreferences object. 
				SharedPreferences.Editor editor = settings.edit();

				// Set a String value in the preferences editor, to be written back once commit() or apply() are called.
				editor.putString("col", radioColorButton.getText().toString());

				editor.commit();
				setBackground();

			}
		});
		setBackground();
		SharedPreferences colors = getSharedPreferences(colors_name, 0);
		int coloration = colors.getInt("colors", 0);
		radioColorGroup.setBackgroundColor(coloration);
		mainLayout.setBackgroundColor(coloration);


		SharedPreferences numbers = getSharedPreferences(number_name, 0);
		int numero = numbers.getInt("number", 0);
		SeekBar seekBar = (SeekBar) findViewById(R.id.color_seekbar);
		seekBar.setMax(510);
		seekBar.setProgress(numero);

		seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
				int c = i % 255;

				SharedPreferences numbers = getSharedPreferences(number_name, 0);
				SharedPreferences.Editor editorio = numbers.edit();
				editorio.putInt("number", i);
				editorio.commit();


				SharedPreferences settings = getSharedPreferences(prefs_name, 0);
				color_pref = settings.getString("col", "Green");

				SharedPreferences colors = getSharedPreferences(colors_name, 0);

				if(color_pref.equals("Green")){
					if (i == 255){
						color = Color.rgb(0, 255, 0);
					}
					else if (i == 510){
						color = Color.rgb(0, 0, 0);
					}
					else if (i <= 255) {
						color = Color.rgb(255 - c, 255, 255 - c);
					}
					else if (i > 255){
						color = Color.rgb(0, 255 - c, 0);
					}
				}
				if(color_pref.equals("Red")){
					if (i == 255){
						color = Color.rgb(255, 0, 0);
					}
					else if (i == 510){
						color = Color.rgb(0, 0, 0);
					}
					else if (i <= 255) {
						color = Color.rgb(255, 255 - c, 255 - c);
					}
					else if (i > 255){
						color = Color.rgb(255 - c, 0, 0);
					}
				}
				if(color_pref.equals("Blue")){
					if (i == 255){
						color = Color.rgb(0, 0, 255);
					}
					else if (i == 510){
						color = Color.rgb(0, 0, 0);
					}
					else if (i <= 255) {
						color = Color.rgb(255 - c, 255 - c, 255);
					}
					else if (i > 255){
						color = Color.rgb(0, 0, 255 - c);
					}
				}
				radioColorGroup.setBackgroundColor(color);
				mainLayout.setBackgroundColor(color);
				SharedPreferences.Editor editor = colors.edit();
				editor.putInt("colors", color);
				editor.commit();
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {

			}

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {

			}
		});
	}
	
	/*
	 * Change the background.
	 */
	private void setBackground(){
		    
			SharedPreferences settings = getSharedPreferences(prefs_name, 0);
			
			// Retrieve a String value from the preferences. default "Green"
		    color_pref = settings.getString("col", "Green");
		    
		    // After retrieving change background color accordingly. 
		    if(color_pref.equals("Green")){
		    	radioColorGroup.setBackgroundColor(Color.parseColor("#00FF00"));
		    	radioColorGroup.check(R.id.radioGreen);
				mainLayout.setBackgroundColor(Color.parseColor("#00FF00"));
		    }
		    if(color_pref.equals("Red")){
		    	radioColorGroup.setBackgroundColor(Color.parseColor("#FF0000"));
		    	radioColorGroup.check(R.id.radioRed);
                mainLayout.setBackgroundColor(Color.parseColor("#FF0000"));

		    }
		    if(color_pref.equals("Blue")){
		    	radioColorGroup.setBackgroundColor(Color.parseColor("#0000FF"));
		    	radioColorGroup.check(R.id.radioBlue);
                mainLayout.setBackgroundColor(Color.parseColor("#0000FF"));
		    }
	}

}

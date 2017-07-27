/* 
 * This is a very simple example of an Activity. 
 * It will simply print "hello world" to the screen of your device
 */

package org.turntotech.simpleactivity;

import android.os.Bundle;
import android.app.Activity;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set the view to main xml file
		setContentView(R.layout.activity_main);
        Log.i("TurnToTech", "Project Name - Simple Activity");
	}


}

/* 
 * It is a very basic example of Navigation. Just click the button and go to the another screen. 
 */

package org.turntotech.simplenavigate;


import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// First view 
		setContentView(R.layout.activity_main);
        Log.i("TurnToTech", "Project Name - Simple Navigate");
	}
	
	public void buttonClicked(View view){
		// Second view when click the button of the main view
		startActivity(new Intent(this, OtherActivity.class));
	}
    public void gotoLast(View view){
        // Second view when click the button of the main view
        startActivity(new Intent(this, LastActivity.class));
    }

}

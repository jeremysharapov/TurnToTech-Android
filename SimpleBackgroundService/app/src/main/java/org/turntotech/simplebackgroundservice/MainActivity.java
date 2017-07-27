/*
 * In this example a message is displayed every 5 seconds telling us 
 * how long the app has been running.
 */


package org.turntotech.simplebackgroundservice;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

	private BroadcastReceiver broadcastReceiver;
	Intent msgIntent;
	EditText min;
	static int mins;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		min = (EditText) findViewById(R.id.Min);
		final MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.applause);

		// Base class for code that will receive intents sent by sendBroadcast().
		broadcastReceiver = new BroadcastReceiver(){
			@Override
			/*
			 * This method is called when the BroadcastReceiver is receiving an Intent broadcast.
			 * During this time you can use the other methods on BroadcastReceiver to view/modify
			 * the current result values.
			 */
			public void onReceive(Context arg0, Intent intent) {
				//String text = intent.getStringExtra(SimpleIntentService.PARAM_OUT_MSG);
				int s = intent.getIntExtra(SimpleIntentService.PARAM_OUT_MSG,1);
				android.util.Log.e("val",String.valueOf(s));


				if(s == 0){
					Toast.makeText(getApplicationContext(), "Done! Now Play Audio...", Toast.LENGTH_LONG).show();

					unregisterReceiver(broadcastReceiver);
					stopService(msgIntent);
				}
				else {
					Toast.makeText(getApplicationContext(), String.valueOf(s), Toast.LENGTH_SHORT).show();
				}
			}
		};

		Log.i("TurnToTech", "Project Name - SimpleBackgroundService");
	}

	public void StartNewService(View view){
		mins = Integer.valueOf(min.getText().toString());
	    msgIntent = new Intent(this, SimpleIntentService.class);

	    // Add extended data to the intent.
	    msgIntent.putExtra(SimpleIntentService.PARAM_IN_MSG, "Hello World: ");

	    /*
	     * Request that a given application service be started.
	     * The Intent should contain either the complete class
	     * name of a specific service implementation to start or a
	     * specific package name to target.
	     */
	    startService(msgIntent);


	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	/*
         * Called after onPause(), for your activity to start interacting with the user.
         */
	public void onResume() {
	    super.onResume();
		
		IntentFilter filter = new IntentFilter(SimpleIntentService.ACTION_RESP);
	    filter.addCategory(Intent.CATEGORY_DEFAULT);

		registerReceiver(broadcastReceiver,filter);
	}

	/*
	 * Called as part of the activity lifecycle when an activity is going into the background,
	 * but has not (yet) been killed. 
	 * (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	  public void onPause() {
		  super.onPause();
	      //unregisterReceiver(broadcastReceiver);
	  }

	@Override
	protected void onStop() {
		unregisterReceiver(broadcastReceiver);
		stopService(msgIntent);
		super.onStop();
	}
}

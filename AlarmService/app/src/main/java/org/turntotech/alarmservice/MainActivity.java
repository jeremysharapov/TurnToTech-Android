/*
 * This is an example of how the AlarmManager class is used.
 * The AlarmManager class provides access to the system alarm services. 
 * These allow you to schedule methods to be run at some point in the future. 
 * 
 * When an alarm goes off, the Intent that had been registered for it is broadcasted
 * by the system.  If the target application is not already running it will get
 * automatically started.
 */

package org.turntotech.alarmservice;

import android.os.Bundle;
import android.os.SystemClock;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;

public class MainActivity extends Activity {

	boolean red;
	LinearLayout lay;
	EditText delaySec;
	final static private long ONE_SECOND = 1000;
	//final static private long DELAY = ONE_SECOND * 5;// Delay set to 5 second
	long delay;
	private final static String ALARM_KEY = "org.turntotech.alarm";

	PendingIntent pi;
	BroadcastReceiver br;
	AlarmManager am;

	PendingIntent pi2;
	BroadcastReceiver br2;
	AlarmManager am2;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        Log.i("TurnToTech", "Project Name - AlarmService");
		// broadcast receiver that will act as the callback for our alarm.

		lay = (LinearLayout) findViewById(R.id.lay1);
		delaySec = (EditText)findViewById(R.id.editText);

		// 1. Create a class that will receive an event.
		br = new BroadcastReceiver() {
			@Override
			public void onReceive(Context c, Intent i) {
//				Toast.makeText(c, "Welcome To TurnToTech", Toast.LENGTH_LONG).show();
				lay.setBackgroundColor(RED);
				red = true;
			}
		};


		// 2. Register the class as a receiver of a specific type of event
		// idenified by a String, in our case, ALARM_KEY defined above


		// 3. Set up the notification that needs to be fired. We don't fire
		// the event yet.

		pi = PendingIntent.getBroadcast(this, 0, new Intent(ALARM_KEY), 0);

		// 4. Get reference to the alarm service
		am = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));

		// 5. Connect the button from the UI to the onClick(..) method below
		findViewById(R.id.the_button).setOnClickListener(new OnClickListener() {

			@Override
			// In the on click listener, we set the alarm.
			public void onClick(View v) {
				// set() method of the AlarmManager is used to register an
				// Intent to be sent.
				// Set the alarm for particular time

				// 6. When button is tapped, we start the alarm to go off at a
				// specific time and also pass it the pending intent reference
				// so it knows which alarm to trigger
				registerReceiver(br, new IntentFilter(ALARM_KEY));
				delay = 0;
				delay = Long.parseLong(delaySec.getText().toString()) * ONE_SECOND;
				am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + delay, pi);

			}

		});
	}

	public void Snooze(View view) {
		if (red) {
			lay.setBackgroundColor(WHITE);
			//registerReceiver(br, new IntentFilter(ALARM_KEY));
			pi = PendingIntent.getBroadcast(this, 0, new Intent(ALARM_KEY), 0);
			am = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));
			am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, 10 * ONE_SECOND, pi);
		}
	}

	/*
	 * 7. We need to remove the pending intent. onDestroy() is called when the activity is destroyed 
	 * 
	 */
	public void kill(View view){
		red = false;
		am.cancel(pi);
		lay.setBackgroundColor(WHITE);
		unregisterReceiver(br);
	}


    @Override
	protected void onDestroy() {
		am.cancel(pi);
		unregisterReceiver(br);
		super.onDestroy();
	}
}
/*
 * Notifications are used to alert users on some event that requires their attention.
 * Notifications alert the users through various forms:
 *  	1.Display a status bar icon
 *  	2.Flash the LED
 *  	3.Vibrate
 *  	4.Ringtones.
 *  Status bar notifications indicate some ongoing background services such as 
 *  downloading, playing music and displaying an alert/information.
 *  To see the notification the user needs to open the notification drawer. 
 *  Notifications are handled by Notification Manger in Android. 
 *  Notification Manager is a system service used to manage notifications.
 *  
 *  Here we create a simple example of notification manager.
 */

package org.turntotech.notification;

import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.DateFormat;
import android.view.View;

public class MainActivity extends Activity {

	private int NOTIFICATION_ID = 1001;
	
	private Notification.Builder mBuilder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        Log.i("TurnToTech", "Project Name - Notification");

		// 1. Cancel all existing notifications for the current application
		((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).cancelAll();
		
		// 2. Create a notification
		mBuilder = new Notification.Builder(this);
		
		// 3. Set the text (first row) of the notification
		mBuilder.setContentTitle("TurnToTech Test Notification");
		
		
		// 4. Set icon for notification.
		mBuilder.setSmallIcon(R.drawable.ic_launcher);

		//5. We also want our application to open when the notification is clicked. For that we create a PendingIntent ( like we did for Alarm example)
		Intent resultIntent = new Intent(this, Notific.class);

		PendingIntent pendingIntentToOpenApp = PendingIntent.getActivity(this,0,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
		
		// Supply the PendingIntent to send when the notification is clicked.
		mBuilder.setContentIntent(pendingIntentToOpenApp);

		// 6. Set the vibration pattern to use.
		mBuilder.setVibrate(new long[] { 0, 500, 500, 500, 500, 500, 500 });

		//7. Set a notification sound for the notification
		Uri alarmSound = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		mBuilder.setSound(alarmSound);

	}

	
	//8. This method will be called when the Notify button is clicked. ( Configured via activity_main.xml)
	public void notifyClicked(View view) {

		//The NotificationManager is a system Service used to manage Notification. Get a reference to it using the getSystemService() method.
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		
		// Set the text (second row) of the notification
		mBuilder.setContentText("The current time is: " + DateFormat.format("MM/dd/yy h:mm:ss aa", System.currentTimeMillis()) + ", tap to open application.");

		
		//9. Trigger the notification
		mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
	}

}

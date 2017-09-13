/*
 * A widget is a small gadget or control of your android application 
 * placed on the home screen. Widgets can be very handy as they allow you 
 * to put your favourite applications on your homescreen in order to quickly
 *  access them. You have probably seen some common widgets , 
 *  such as music widget , weather widget , clock widget e.t.c
 *  This example will show how to build android widget
 */

package org.turntotech.samplewidget;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MainActivity extends AppWidgetProvider {

	@Override
	// This is called when the last instance of AppWidgetProvider is deleted
	public void onDisabled(Context context) {
		Toast.makeText(context, "Time Widget Disabled", Toast.LENGTH_LONG)
				.show();
		Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
		PendingIntent sender = PendingIntent
				.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
		super.onDisabled(context);
	}

	@Override
	// This is called when an instance of AppWidgetProvider is created.
	public void onEnabled(Context context) {
        Log.i("TurnToTech", "Project - Widget");
		super.onEnabled(context);
		Toast.makeText(context, "Time Widget Enabled", Toast.LENGTH_LONG)
				.show();
		AlarmManager am = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
		am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),
				1000, pi);
	}

}
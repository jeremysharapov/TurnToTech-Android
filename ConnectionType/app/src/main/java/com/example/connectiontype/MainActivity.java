/*
 * This is a sample application that checks the type of network connection a device is using.
 * ConnectivityManager is a class that answers queries about the state of network connectivity. 
 * It also notifies applications when network connectivity changes. 
 * */
package com.example.connectiontype;



import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        Log.i("TurnToTech", "Project Name - ConnectionType");
	}

	// We call this method when we want to check the type of connection
	public void buttonClicked(View view) {

		// 1. Create an instance of ConnectivityManager
		ConnectivityManager cmanager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);

		// 2. The connectivityManager object's getActiveNetworkInfo() method
		// gives us info about the network we are on
		NetworkInfo networkInfoObj = cmanager.getActiveNetworkInfo();

		String networkType = null;

		// 3. If connected, we can get the type of the network
		if (networkInfoObj != null && networkInfoObj.isConnected()) {

			// 4. networkInfoObj.getType() gives us either TYPE_WIFI or
			// TYPE_MOBILE

			// 5. Populate the string with the type of network

			if (networkInfoObj.getType() == ConnectivityManager.TYPE_WIFI)
				networkType = "WI-FI will be used for Internet";
			else if (networkInfoObj.getType() == ConnectivityManager.TYPE_MOBILE)
				networkType = "Cell will be used for Internet";
			else
				networkType = "Not cell or WI-FI";

		} else
			networkType = "Not Connected";

		// 6. Show an alert with the type of network we're on

		Toast.makeText(MainActivity.this, networkType, Toast.LENGTH_LONG)
				.show();
	}

}

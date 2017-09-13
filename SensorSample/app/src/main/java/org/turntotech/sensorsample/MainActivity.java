/*
 * This is an example of sensor usage in Android.
 * The Android platform provides several sensors that let you monitor the motion of a device.
 * Motion sensors are useful for monitoring device movement, such as tilt, shake, rotation, or swing. 
 */

package org.turntotech.sensorsample;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

// SensorEventListener - Used for receiving notifications from the SensorManager when sensor values have changed. 
public class MainActivity extends Activity implements SensorEventListener {

	  private SensorManager mSensorManager;
	  Sensor accelerometer;
	  Sensor magnetometer;
	  private TextView textView;
	  float[] mGravity = null;
	  float[] mGeomagnetic = null;
	  float orientation[] = null;

	 
	  protected void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.activity_main);
        Log.i("TurnToTech", "Project Name - SensorSample");
	    textView = (TextView) findViewById(R.id.textView);
	    
	    /*
	     * SensorManager lets you access the device's sensors. 
	     * Get an instance of this class by calling Context.getSystemService() with the argument SENSOR_SERVICE. 
	     */
	    mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
	    
	    //getDefaultSensor() Use this method to get the default sensor for a given type. 
	    accelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
	    magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
	  }
	 
	  
	  /*
		 * Called after onPause(), for your activity to start interacting with the user.
	  */
	  protected void onResume() {
	    super.onResume();
	    
	    // Registers a SensorEventListener for the given sensor. 
	    mSensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI);
	    mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI);
	  }
	 
	  
	  /*
		 * Called as part of the activity lifecycle when an activity is going into the background,
		 * but has not (yet) been killed. 
		 * (non-Javadoc)
		 * @see android.app.Activity#onPause()
		*/
	  protected void onPause() {
	    super.onPause();
	    
	    // Unregisters a listener for all sensors.
	    mSensorManager.unregisterListener(this);
	  }
	 
	  public void onAccuracyChanged(Sensor sensor, int accuracy) {  }
	 
	  
	  /*
	   * Called when sensor values have changed. 
	   * The length and contents of the values array vary depending on 
	   * which sensor is being monitored.
	   */
	  public void onSensorChanged(SensorEvent event) {
		  
	    
		 String details = "Sensor Details:";
		  
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
			mGravity = event.values;
		}
	      ;
	    if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
	      mGeomagnetic = event.values;
	    }
	    
	    if (mGravity != null && mGeomagnetic != null) {
	      
	      float R[] = new float[9];
	      float I[] = new float[9];
	      boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
	      if (success) {
	    	  orientation = new float[3];
	    	  SensorManager.getOrientation(R, orientation);
	      }
	    }
	    if (mGravity != null) {
			details +="\n\nX       : " + mGravity[0];
			details +="\n\nY       : " + mGravity[1];
			details +="\n\nZ       : " + mGravity[2];
	    }
	    
	    if(orientation!=null){
			details +="\n\nAzimuth : " + orientation[0];
			details +="\n\nPitch   : " + orientation[1];
			details +="\n\nRoll    : " + orientation[2];
	    }
	    
	    
		textView.setText(details);
		
		
		
	    
	  }
	}


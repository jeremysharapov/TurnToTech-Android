/*
 * This is a simple example of gesture in Android.
 * Android provides special types of touch screen events such as pinch , double tap,
 * scrolls , long presses and flinch. These are all known as gestures.
 * Android provides the GestureDetector class to receive motion events and tell us whether
 * these events correspond to gestures or not.
 */


package org.turntotech.example.samplegesture;

import android.app.Activity;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ImageView img;
	private TextView txt;
	private Matrix matrix = new Matrix();
	private float scale = 1f;
	/*
	 * Android provides the ScaleGestureDetector class to handle gestures like pinch e.t.c.
	 * In order to use it , you need to instantiate an object of this class.
	 */
	private ScaleGestureDetector scaleGD;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        Log.i("TurnToTech", "Project Name - SampleGesture");
		img = (ImageView) findViewById(R.id.imageView1);
		txt = (TextView) findViewById(R.id.textView1);
		scaleGD = new ScaleGestureDetector(this, new ScaleListener());
	}

	@Override
	/*
	 * We have to define the event listener 
	 * and override the function OnTouchEvent to make it work.
	 */
	public boolean onTouchEvent(MotionEvent ev) {
		return scaleGD.onTouchEvent(ev);
	}

	private class ScaleListener extends
	
			/* ScaleGestureDetector.SimpleOnScaleGestureListener -> A convenience class to
			 * extend when you only want to listen for a subset of scaling-related events. 
			 */
			ScaleGestureDetector.SimpleOnScaleGestureListener {
		@Override
		/* onScale() Responds to scaling events for a gesture in progress.
		 *  Reported by pointer motion.		
		 */
		public boolean onScale(ScaleGestureDetector detector) {
			scale *= detector.getScaleFactor();
			if(scale<0.5f)scale=0.5f;
			if(scale>6.0f)scale=6.0f;
			matrix.setScale(scale, scale);
			img.setImageMatrix(matrix);
			txt.setText("Scale: " + scale);
			return true;
		}
	}

}
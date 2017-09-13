/*
 * The Android System provides Drag and Drop framework, 
 * which facilitates users especially to move data from one View to another 
 * within the current layout. It is good to mention that many apps take advantage 
 * of the graphical drag and drop gesture that the system procures.
 * This example shows a simple drag and drop functionality, 
 * in which an item can be moved from one View to another,
 */


package org.turntotech.example.sampledragdrop;

import android.os.Bundle;
import android.app.Activity;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.View.DragShadowBuilder;
import android.view.ViewGroup;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements OnTouchListener,
		OnDragListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        Log.i("TurnToTech", "Project Name - SampleDragDrop");

		findViewById(R.id.textView1).setOnTouchListener(this);
		
		// Here we create two linear layout in our main xml page one is coloured with pink
		// another colored with yellow
		findViewById(R.id.pinkLayout).setOnDragListener(this);
		findViewById(R.id.yellowLayout).setOnDragListener(this);
	}

	@Override
	/* When the user presses the View to be dragged the methods like 
	 * onTouch() will be invoked by the Android runtime. 
	 * In onTouch() method, the application intimates about start of the drag. 
	 * This is done while calling the startDrag() method. 
	 * This method includes some set of arguments like as follows.
    		# Data to be dragged(It is null here).
    		# Shadow Buider object created by DragShadowBuilder.
    		# Local State (Drag Location).
    		# Flags

	*/
	public boolean onTouch(View view, MotionEvent motionEvent) {

		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
			view.startDrag(null, shadowBuilder, view, 0);
			view.setVisibility(View.INVISIBLE);
		}
		return true;
	}

	@Override
	/*
	 * After the information about the start of the drag is intimated to the 
	 * Android runtime, immediately it will allow the DragEventListener to 
	 * handle the drag event using the onDrag() method.
	 * The DragEvent represents an event that is sent out by the system at 
	 * various times during a drag and drop operation. 
	 * This class provides few Constants and important methods which 
	 * we use during Drag/Drop process.
	 */
	public boolean onDrag(View layoutview, DragEvent dragevent) {
		/* ACTION_DROP: 
		 * Signals to a View that the user has released the drag shadow, 
		 * and the drag point is within the bounding box of the View.
		*/
		if (dragevent.getAction() == DragEvent.ACTION_DROP) {
			View view = (View) dragevent.getLocalState();
			ViewGroup owner = (ViewGroup) view.getParent();
			owner.removeView(view);
			LinearLayout container = (LinearLayout) layoutview;
			container.addView(view);
			view.setVisibility(View.VISIBLE);
		}
		return true;
	}
}

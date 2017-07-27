/*
 *  A fragment is an independent component which can be used by an activity. 
 *  A fragment encapsulates functionality so that it is easier to reuse within activities and layouts.
 *  A fragment runs in the context of an activity, but has its own life cycle and typically its own user interface.
 *  It is also possible to define fragments without an user interface, i.e., headless fragments.
 *  Fragments can be dynamically or statically added to an activity. 
 *  In this example, we show the two buttons on the screen and 
 *  when the appropriate button is pressed, the respective fragment is displayed. 
 */

package org.turntotech.samplefragment;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);

      setContentView(R.layout.activity_main);
        Log.i("TurnToTech", "Project name - SampleFragment");
       
      //FragmentTransaction is used to start a series of edit operations 
      // on the Fragments associated with this FragmentManager
      FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();	
      fragmentTransaction.add(R.id.fragment_container, new FragmentOne()).commit();

    }

//	TODO: Modify this function to include your button and replace the container with your new fragment.
	public void selectFrag(View view) {
		
	    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();	
		if(view == findViewById(R.id.button1)) {
	    	fragmentTransaction.replace(R.id.fragment_container, new FragmentOne()).commit();
	    }else if (view == findViewById(R.id.button2)){
	    	fragmentTransaction.replace(R.id.fragment_container, new FragmentTwo()).commit();
	    }
	    else{
			fragmentTransaction.replace(R.id.fragment_container, new FragmentThree()).commit();
		}
	}
   
}

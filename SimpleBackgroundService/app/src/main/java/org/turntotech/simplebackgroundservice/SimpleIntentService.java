package org.turntotech.simplebackgroundservice;

import android.app.IntentService;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.widget.EditText;
import android.widget.Toast;

public class SimpleIntentService extends IntentService {
    public static final String PARAM_IN_MSG = "in_msg";
    public static final String PARAM_OUT_MSG = "out_msg";
    
    public static final String ACTION_RESP = "org.turntotech.intent.action.MESSAGE_PROCESSED";
    
    int secs = MainActivity.mins ;
 
    public SimpleIntentService() {
        super("SimpleIntentService");
    }

    //EditText min = (EditText) MainActivity.findViewById(R.id.Min);
 
    @Override
    protected void onHandleIntent(Intent intent) {
 
        System.out.println("SimpleIntentService Called");
    	String msg = intent.getStringExtra(PARAM_IN_MSG);
        while(true){
    	    SystemClock.sleep(1000); // 5 seconds
    	    secs -= 1;

            if(secs == 0){
                try {

//
                    MediaPlayer.create(getApplicationContext(), R.raw.applause).start();

                }catch(Exception e){e.printStackTrace();}
            }

            String resultTxt = msg + "" + secs + " seconds left";
            Intent broadcastIntent = new Intent();
        
            // Set the general action to be performed.
            broadcastIntent.setAction(ACTION_RESP);
        
            // Add a new category to the intent. Categories provide additional detail about the action the intent performs.
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        
            // Add extended data to the intent.
            broadcastIntent.putExtra(PARAM_OUT_MSG, secs);
        
            /*
             * Broadcast the given intent to all interested BroadcastReceivers.
             * This call is asynchronous; it returns immediately, and
            * you will continue executing while the receivers are run.
            */
            sendBroadcast(broadcastIntent);
        }
    }
}
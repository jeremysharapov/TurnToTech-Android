package backgroundservice.turntotech.org.simplebackgroudservice;

/**
 * Created by RAJAT on 6/25/2015.
 */
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import java.util.Calendar;
import java.util.logging.LogRecord;

public class MyService extends Service {

//    int time;
//    int timeStart;
    String tag="TestService";
    public MyService() {
        Log.e(tag,"Under TurnToTech Service");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return  null;
    }

    @Override
    public void onCreate() {


        //1.1 Service created.
        Toast.makeText(this, "The TurnToTech Service was Created.", Toast.LENGTH_LONG).show();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        // 1.2 called from Main Activity - startNewService
        // We want this service to continue running until it is explicitly
        // stopped, so return sticky.

        // For time consuming an long tasks you can launch a new thread here...
        Toast.makeText(this, "TurnToTech Service Started.", Toast.LENGTH_LONG).show();
//        timeStart = (int)System.currentTimeMillis();
        return START_STICKY;
    }





    @Override
    public void onDestroy() {

        /*
        2.2
        onDestroy Called by the system to notify a Service that it is no longer used and is being removed.
        when stopNewService called from Main Activity,  it calls stopService. So no service is running
        at that moment and so onDestroy event is invoked.
        */

        // Service destroyed.
//        time = (int)System.currentTimeMillis() - timeStart;
        Toast.makeText(this, "Service Destroyed.", Toast.LENGTH_LONG).show();

    }
}
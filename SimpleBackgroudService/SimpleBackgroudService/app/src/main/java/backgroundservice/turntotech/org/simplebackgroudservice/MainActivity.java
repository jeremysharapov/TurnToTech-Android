package backgroundservice.turntotech.org.simplebackgroudservice;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView Timer;
    int Time;
    int TimeStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Timer = (TextView) findViewById(R.id.Timer);
        setContentView(R.layout.activity_main);
    }


    /*
        1.
        startNewService method is called by clicking the 'Start Service' button.
        It is defined in activity_main.xml onClick event.
    */
    public void startNewService(View view) {

        // Start the  service
        // Service MyService needs to be added in AndroidManifest.xml
        startService(new Intent(this, MyService.class));
        TimeStart = (int)System.currentTimeMillis();
    }



    /*
        2.
        stopNewService method is called by clicking the 'Stop Service' button.
        It is defined in activity_main.xml onClick event.
    */
    public void stopNewService(View view) {

        // Stop the  service
        stopService(new Intent(this, MyService.class));
    }

}

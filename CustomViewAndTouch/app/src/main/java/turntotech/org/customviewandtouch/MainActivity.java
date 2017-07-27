package turntotech.org.customviewandtouch;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Log.i("TurnToTech", "Project name - CustomViewAndTouch");

        //1. See below for the declaration of MyExtendedView
        MyExtendedView extendedView = new MyExtendedView(this, null);


        //2. Usually we pass a layout to setContentView e.g. setContentView(R.layout.activity_main) but here we will pass a reference to the extended view we created above

        setContentView(extendedView);
    }


}

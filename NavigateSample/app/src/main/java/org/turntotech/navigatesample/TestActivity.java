package org.turntotech.navigatesample;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.*;


public class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Intent intent = getIntent();
        int position2 = intent.getIntExtra("POSITION2", 0);
        android.util.Log.e("TTT",String.valueOf(position2));
    }
}

package org.turntotech.samplejson;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Info extends Activity {

    TextView Title;
    TextView Overview;
    TextView Popularity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        Title = (TextView) findViewById(R.id.Title);
        Overview = (TextView) findViewById(R.id.Overview);
        Popularity = (TextView) findViewById(R.id.Popularity);
        Intent intent = getIntent();
        Title.setText(intent.getStringExtra("TITLE"));
        Overview.setText(intent.getStringExtra("OVERVIEW"));
        Popularity.setText(String.valueOf(intent.getDoubleExtra("POPULARITY", 0)));

    }
}

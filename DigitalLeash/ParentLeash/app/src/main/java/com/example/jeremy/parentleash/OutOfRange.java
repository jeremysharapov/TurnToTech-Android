package com.example.jeremy.parentleash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class OutOfRange extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_out_of_range);
    }

    public void onClickOut(View view){
        startActivity(new Intent(this, MainActivity.class));
    }
}

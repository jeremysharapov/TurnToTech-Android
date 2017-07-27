package com.example.jeremy.parentleash;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class InRange extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_range);
    }

    public void onClickIn(View view){
        startActivity(new Intent(this, MainActivity.class));
    }
}

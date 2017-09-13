package io.rajat.turntotech.ormlitesample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button addStudent,displayAll;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addStudent = (Button)findViewById(R.id.button);
        displayAll = (Button)findViewById(R.id.button2);

    }

    void onClickButton(View v){
        if(v.getId()==addStudent.getId())
        {
            Intent intent = new Intent(MainActivity.this,AddActivity.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(MainActivity.this,DisplayActivity.class);
            startActivity(intent);
        }
    }
}

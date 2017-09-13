package com.example.jeremy.cloudcamera;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.VectorEnabledTintResources;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(this, ChooseActivity.class));
        }
    }

    public void logInMain(View view){
        startActivity(new Intent(this, LogInActivity.class));
    }

    public void signUpMain(View view){
        startActivity(new Intent(this, SignUpActivity.class));
    }
}

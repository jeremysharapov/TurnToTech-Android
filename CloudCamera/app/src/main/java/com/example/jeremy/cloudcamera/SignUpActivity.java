package com.example.jeremy.cloudcamera;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.ContentValues.TAG;

public class SignUpActivity extends AppCompatActivity {
    EditText Username, Password;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        Username = (EditText) findViewById(R.id.usernamesign);
        Password = (EditText) findViewById(R.id.passwordsign);
        mAuth = FirebaseAuth.getInstance();
    }

    public void createAccount(View view) {
        final String email = Username.getText().toString();
        final String password = Password.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignUpActivity.this, "Signup Failed", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(SignUpActivity.this, "Signup Successful; welcome " + email + "!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, ChooseActivity.class));
                        }

                        // ...
                    }
                });
    }
}

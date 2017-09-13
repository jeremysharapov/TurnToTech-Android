package com.example.jeremy.cloudcamera;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ImageActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;
    ImageButton LikeButton;
    TextView NumLikes;
    TextView Description;
    ImageView image;
    Button LogOut;
    Uri uri;
    String FileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        image = (ImageView) findViewById(R.id.imageView2);
        LikeButton = (ImageButton)findViewById(R.id.LikeButton);
        NumLikes = (TextView)findViewById(R.id.Likes);
        Description = (TextView)findViewById(R.id.Description);
        Intent intent = getIntent();
        uri = Uri.parse(intent.getStringExtra("URI"));
        FileName = intent.getStringExtra("Name");
        Glide.with(this).load(uri).into(image);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final DatabaseReference myRef2 = mDatabase.child("Uri").child(FileName).child("Description");
        Query phoneQuery2 = myRef2.orderByKey();
        phoneQuery2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String s = dataSnapshot.getValue(String.class);
                Description.setText(s);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("CloudCamera Problem", "onCancelled", databaseError.toException());
            }
        });

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.toolbar);
        LogOut = (Button)findViewById(R.id.LogOut);

        LogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mAuthListener != null) {
                    mAuth.removeAuthStateListener(mAuthListener);
                }
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(ImageActivity.this, MainActivity.class));
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
            }
        };
        final DatabaseReference myRef = mDatabase.child("Uri").child(FileName).child("Likes");
        Query phoneQuery = myRef.orderByKey();
        phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int temp = (int) dataSnapshot.getChildrenCount();
                NumLikes.setText(Integer.toString(temp));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("CloudCamera Problem", "onCancelled", databaseError.toException());
            }
        });
    }

    public void Like(View view){
        final DatabaseReference myRef = mDatabase.child("Uri").child(FileName).child("Likes");
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Query phoneQuery = myRef.orderByKey();
        phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    String string = singleSnapshot.getValue(String.class);
                    if (string.equals(user.getEmail())){
                        return;
                    }
                }
                myRef.child(user.getUid()).setValue(user.getEmail());
                int temp = Integer.parseInt(NumLikes.getText().toString());
                NumLikes.setText(Integer.toString(temp+1));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("CloudCamera Problem", "onCancelled", databaseError.toException());
            }
        });
    }
}

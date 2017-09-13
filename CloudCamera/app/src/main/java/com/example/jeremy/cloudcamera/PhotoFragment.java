package com.example.jeremy.cloudcamera;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import static android.app.Activity.RESULT_OK;


public class PhotoFragment extends Fragment {

    private static final int CAMERA_REQUEST = 1888;
    private int PICK_IMAGE_REQUEST = 1;
    ImageView mimageView;
    Button Camera, Photos;
    private StorageReference mStorageRef;
    private DatabaseReference mDatabase;

    public interface UploadDone {
        public void sendNotification();
    }


    UploadDone iCallback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the container activity has implemented
        // the callback interface. If not, it throws an exception
        try {
            iCallback = (UploadDone) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement TextClicked");
        }
    }

    @Override
    public void onDetach() {
        iCallback = null; // => avoid leaking, thanks @Deepscorn
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        Camera = (Button) view.findViewById(R.id.Camera);
        Photos = (Button) view.findViewById(R.id.Photos);
        mimageView = (ImageView) view.findViewById(R.id.imageView4);
        mimageView.setVisibility(View.INVISIBLE);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });

        Photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
            }
        });

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mimageView.setVisibility(View.VISIBLE);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            final Bitmap mphoto = (Bitmap) data.getExtras().get("data");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View view = inflater.inflate(R.layout.descriptiondialogue, null);
            builder.setView(view)
                    .setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            final Long tsLong = System.currentTimeMillis() / 1000;
                            final String ts = "images/" + tsLong.toString() + ".jpg";
                            StorageReference riversRef = mStorageRef.child(ts);
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            mphoto.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                            byte[] mdata = baos.toByteArray();

                            UploadTask uploadTask = riversRef.putBytes(mdata);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception exception) {
                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    Toast.makeText(getContext(), "Upload successful!", Toast.LENGTH_LONG).show();
                                    Task<Uri> tempUrl = mStorageRef.child(ts).getDownloadUrl();
                                    tempUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri downloadUrl) {
                                            Glide.with(PhotoFragment.this).load(downloadUrl).into(mimageView);
                                            DatabaseReference myRef = mDatabase.child("Uri").child(tsLong.toString());
                                            myRef.child("URI").setValue(downloadUrl.toString());
                                            myRef.child("Likes").setValue(new ArrayList<String>());
                                            myRef.child("Comments").setValue(new ArrayList<String>());
                                            TextView text = view.findViewById(R.id.EnteredDescription);
                                            myRef.child("Description").setValue(text.getText().toString());
                                            iCallback.sendNotification();

                                        }
                                    });
                                }
                            })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            Toast.makeText(getContext(), "Upload Failed", Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mimageView.setVisibility(View.INVISIBLE);
                        }
                    });
            builder.create();
            builder.show();
        } else if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data.getData() != null) {
            final Uri uri = data.getData();
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View view = inflater.inflate(R.layout.descriptiondialogue, null);
            builder.setView(view)
                    .setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            final Long tsLong = System.currentTimeMillis() / 1000;
                            final String ts = "images/" + tsLong.toString() + ".jpg";
                            StorageReference riversRef = mStorageRef.child(ts);
                            riversRef.putFile(uri)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                            Toast.makeText(getContext(), "Upload successful!", Toast.LENGTH_LONG).show();
                                            Task<Uri> tempUrl = mStorageRef.child(ts).getDownloadUrl();
                                            tempUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri downloadUrl) {
                                                    Glide.with(PhotoFragment.this).load(downloadUrl).into(mimageView);
                                                    DatabaseReference myRef = mDatabase.child("Uri").child(tsLong.toString());
                                                    myRef.child("URI").setValue(downloadUrl.toString());
                                                    myRef.child("Likes").setValue(new ArrayList<String>());
                                                    myRef.child("Comments").setValue(new ArrayList<String>());
                                                    TextView text = view.findViewById(R.id.EnteredDescription);
                                                    myRef.child("Description").setValue(text.getText().toString());
                                                    iCallback.sendNotification();
                                                }
                                            });
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception exception) {
                                            Toast.makeText(getContext(), "Upload Failed", Toast.LENGTH_LONG).show();
                                        }
                                    });
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            mimageView.setVisibility(View.INVISIBLE);
                        }
                    });
            builder.create();
            builder.show();
        }
    }
}
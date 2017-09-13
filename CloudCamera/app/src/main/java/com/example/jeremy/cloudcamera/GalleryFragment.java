package com.example.jeremy.cloudcamera;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;


public class GalleryFragment extends Fragment implements  ICallBack{
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private RecyclerView recycler_view;
    private  GridAdapter recyclerAdapter;
    ArrayList<Uri> UriList;
    ArrayList<String> ImageNameList;
    DatabaseReference myRef;

    public void update(){
        recyclerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onStart() {
        super.onStart();
        Query phoneQuery = myRef.orderByKey();
        phoneQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                UriList.clear();
                for(DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
                    String string = singleSnapshot.child("URI").getValue(String.class);
                    Uri uri = Uri.parse(string);
                    UriList.add(uri);
                    String name = singleSnapshot.getKey();
                    ImageNameList.add(name);
                }
                recyclerAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("CloudCamera Problem", "onCancelled", databaseError.toException());
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        UriList = new ArrayList<Uri>();
        ImageNameList = new ArrayList<String>();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        myRef = mDatabaseRef.child("Uri");

        recycler_view= (RecyclerView) view.findViewById(R.id.Recycler);
        recyclerAdapter=new GridAdapter(UriList);
        GridLayoutManager layoutmanager = new GridLayoutManager(getContext(), 2, GridLayoutManager.VERTICAL, false);
        recycler_view.setLayoutManager(layoutmanager);
        recycler_view.setAdapter(recyclerAdapter);

        recycler_view.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recycler_view ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Intent intent = new Intent(getContext(), ImageActivity.class);
                        intent.putExtra("URI", UriList.get(position).toString());
                        intent.putExtra("Name", ImageNameList.get(position));
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {
                    }
                }));




        return view;
    }

    @Override
    public void DoStuff() {
        recyclerAdapter.notifyDataSetChanged();
    }

    public class GridAdapter extends RecyclerView.Adapter<GridAdapter.MyViewHolder> {

        private List<Uri> gridList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public ImageView imView;

            public MyViewHolder(View view) {
                super(view);
                imView = (ImageView) view.findViewById(R.id.imageView);
            }
        }


        public GridAdapter(List<Uri> gridList) {
            this.gridList = gridList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {
            Glide.with(GalleryFragment.this).load(gridList.get(position)).into(holder.imView);
        }

        @Override
        public int getItemCount() {
            if(gridList == null)
                return 0;
            else
                return gridList.size();
        }
    }

}

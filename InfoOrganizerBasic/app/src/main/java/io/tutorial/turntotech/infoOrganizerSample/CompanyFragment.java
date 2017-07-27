package io.tutorial.turntotech.infoOrganizerSample;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyFragment extends Fragment {

    private RecyclerView recycler_view;
    private ArrayList<String> listOfComany;
    private  VerticalAdapter recyclerAdapter;
    ImageButton addButton;
    ImageButton backButton;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.activity_main, container, false);



        recycler_view= (RecyclerView) view.findViewById(R.id.vertical_recycler_view);


        listOfComany=new ArrayList<String>();
        listOfComany.add("Apple");
        listOfComany.add("Samsung");
        listOfComany.add("Motorola");
        listOfComany.add("Microsoft");

        recyclerAdapter=new VerticalAdapter(listOfComany);


        LinearLayoutManager layoutmanager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler_view.setLayoutManager(layoutmanager);



        recycler_view.setAdapter(recyclerAdapter);

        recycler_view.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recycler_view ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        Toast.makeText(getContext(),listOfComany.get(position),Toast.LENGTH_SHORT).show();


                        ((StartActivity) getActivity()).setCurrentCompanyNo(position);

                        // Go to Child not Found Screen
                        Fragment productFragment = new ProductFragment();
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.mainLayout, productFragment);
                        fragmentTransaction.addToBackStack(null);

                        // Commit the transaction
                        fragmentTransaction.commit();

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        // do what you want
                    }
                })
        );

        // Action Bar Setup
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.toolbar);
        backButton = (ImageButton)activity.findViewById(R.id.imageButton);
        addButton = (ImageButton)activity.findViewById(R.id.imageButton2);
        backButton.setVisibility(View.INVISIBLE);



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Add",Toast.LENGTH_LONG).show();
            }
        });




        return view;
    }

    public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.MyViewHolder> {

        private List<String> verticalList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView txtView;

            public MyViewHolder(View view) {
                super(view);
                txtView = (TextView) view.findViewById(R.id.txtView);

            }
        }


        public VerticalAdapter(List<String> verticalList) {
            this.verticalList = verticalList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.vertical_item_view, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.txtView.setText(verticalList.get(position));
            // If you want to access separate part of it

            holder.txtView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(getContext(),holder.txtView.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return verticalList.size();
        }
    }
}

package io.tutorial.turntotech.infoOrganizerSample;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyFragment extends Fragment implements ICallBack {

    private RecyclerView recycler_view;
    private  VerticalAdapter recyclerAdapter;
    ImageButton addButton;
    ImageButton backButton;
    CheckBox Edit;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_main, container, false);

        DAO.getHelper(getContext());
        DAO.getInstance();
        DAO.getInstance().setCallBack(this);
        DAO.getStockURL();
        DAO.getInstance().sendVolleyRequest(getContext());

        recycler_view= (RecyclerView) view.findViewById(R.id.vertical_recycler_view);







        recyclerAdapter=new VerticalAdapter(DAO.getcompanyList());


        LinearLayoutManager layoutmanager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycler_view.setLayoutManager(layoutmanager);



        recycler_view.setAdapter(recyclerAdapter);

        recycler_view.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), recycler_view ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, int position) {
                        DAO.setCompanyNo(position);

                        if(Edit.isChecked()){
                            DAO.setEdit(true);
                            Toast.makeText(getContext(),"Edit " + DAO.getcompanyList().get(position).getName(),Toast.LENGTH_LONG).show();
                            Fragment editCompanyFragment = new EditCompanyFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.mainLayout, editCompanyFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }

                        else {
                            Toast.makeText(getContext(), DAO.getcompanyList().get(position).getName(), Toast.LENGTH_SHORT).show();
                            Fragment productFragment = new ProductFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.mainLayout, productFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }

                    }

                    @Override public void onLongItemClick(View view, int position) {
                        final int pos = position;
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        int temp = Integer.parseInt("" + DAO.getcompanyList().get(pos).getId());
                                        try {
                                            DAO.helper.getmCompanyDao().delete(DAO.getcompanyList().get(pos));
                                            DAO.getcompanyList().remove(pos);
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        recyclerAdapter=new VerticalAdapter(DAO.getcompanyList());
                                        LinearLayoutManager layoutmanager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                        recycler_view.setLayoutManager(layoutmanager2);
                                        recycler_view.setAdapter(recyclerAdapter);
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //Do your No progress
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
                        ab.setMessage("Are you sure you want to delete " + DAO.getcompanyList().get(position).getName() + "?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();
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
        Edit = (CheckBox)activity.findViewById(R.id.EditInfo);
        backButton.setVisibility(View.INVISIBLE);



        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAO.setEdit(false);
                Toast.makeText(getContext(),"Add",Toast.LENGTH_LONG).show();
                Fragment editCompanyFragment = new EditCompanyFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainLayout, editCompanyFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });



        return view;
    }

    @Override
    public void updateStockPrice() {
        recyclerAdapter.notifyDataSetChanged();
    }

    public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.MyViewHolder> {

        private List<Company> verticalList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView txtView, stock;
            public ImageView imView;

            public MyViewHolder(View view) {
                super(view);
                txtView = (TextView) view.findViewById(R.id.txtView);
                imView = (ImageView) view.findViewById(R.id.imageView);
                stock = (TextView) view.findViewById(R.id.textView2);
            }
        }


        public VerticalAdapter(List<Company> verticalList) {
            this.verticalList = verticalList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_item_view, parent, false);

            return new MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.txtView.setText(verticalList.get(position).getName());
            holder.stock.setText(verticalList.get(position).getStock_ticker() + " - " + verticalList.get(position).getStock_price());
            // If you want to access separate part of it
            Picasso.with(getContext()).load(verticalList.get(position).getLogoURL()).placeholder(R.mipmap.ic_launcher).into(holder.imView);

        }

        @Override
        public int getItemCount() {
            return verticalList.size();
        }
    }
}

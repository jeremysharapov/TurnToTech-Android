package io.tutorial.turntotech.infoOrganizerSample;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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
import java.util.ArrayList;
import java.util.List;

import static io.tutorial.turntotech.infoOrganizerSample.DAO.getCompanyNo;

public class ProductFragment extends Fragment {

    private RecyclerView product_recycler_view;
    private ProductFragment.VerticalProductAdapter recyclerProductAdapter;
    ImageButton addButton;
    ImageButton backButton;
    CheckBox Edit;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_main2, container, false);

        product_recycler_view= (RecyclerView) view.findViewById(R.id.vertical_recycler_view);

        // Get the Company to display correct Products
        final int companyNo = getCompanyNo();


        DAO.getInstance().getFromDB();
        final ArrayList<Product> tempArrayList = new ArrayList<Product>(DAO.getcompanyList().get(companyNo).getProducts());
        recyclerProductAdapter = new VerticalProductAdapter(tempArrayList);


        LinearLayoutManager layoutmanager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        product_recycler_view.setLayoutManager(layoutmanager);


        product_recycler_view.setAdapter(recyclerProductAdapter);
        product_recycler_view.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(), product_recycler_view ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        DAO.setProductNo(position);

                        if(Edit.isChecked()){
                            DAO.setEdit(true);
                            Toast.makeText(getContext(),"Edit " + tempArrayList.get(position).getName(), Toast.LENGTH_LONG).show();
                            Fragment editProductFragment = new EditProductFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.mainLayout, editProductFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }

                        else {
                            Toast.makeText(getContext(), tempArrayList.get(position).getName(), Toast.LENGTH_SHORT).show();
                            Fragment webFragment = new WebFragment();
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.mainLayout, webFragment);
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }


                    }

                    @Override
                    public void onLongItemClick(View view, int position){
                    final int pos = position;
                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        int temp = Integer.parseInt("" + tempArrayList.get(pos).getId());
                                        try {
                                            DAO.helper.getmProductDao().delete(tempArrayList.get(pos));
                                            tempArrayList.remove(pos);
                                            DAO.getInstance().getFromDB();
                                            //DAO.getcompanyList().get(DAO.getCompanyNo()).getProducts().remove(pos);
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        recyclerProductAdapter=new ProductFragment.VerticalProductAdapter(tempArrayList);
                                        LinearLayoutManager layoutmanager2 = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                                        product_recycler_view.setLayoutManager(layoutmanager2);
                                        product_recycler_view.setAdapter(recyclerProductAdapter);
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //Do your No progress
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder ab = new AlertDialog.Builder(getContext());
                        ab.setMessage("Are you sure you want to delete " + tempArrayList.get(position).getName() + "?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();


                    }
                }));

        // ActionBar SetUp
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.toolbar);
        backButton = (ImageButton)activity.findViewById(R.id.imageButton);
        addButton = (ImageButton)activity.findViewById(R.id.imageButton2);
        Edit = (CheckBox)activity.findViewById(R.id.EditInfo);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Back",Toast.LENGTH_LONG).show();
                Fragment companyFragment = new CompanyFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainLayout, companyFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DAO.setEdit(false);
                Toast.makeText(getContext(),"Add",Toast.LENGTH_LONG).show();
                Fragment editProductFragment = new EditProductFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainLayout, editProductFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });




        return view;
    }

    public class VerticalProductAdapter extends RecyclerView.Adapter<ProductFragment.VerticalProductAdapter.MyViewHolder> {

        private List<Product> verticalList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView textProductName;
            public ImageView imView;

            public MyViewHolder(View view) {
                super(view);
                textProductName = (TextView) view.findViewById(R.id.textProductName);
                imView = (ImageView) view.findViewById(R.id.imageView);
            }
        }


        public VerticalProductAdapter(List<Product> verticalList) {
            this.verticalList = verticalList;
        }

        @Override
        public ProductFragment.VerticalProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_product_item, parent, false);

            return new ProductFragment.VerticalProductAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.textProductName.setText(verticalList.get(position).getName());
            Picasso.with(getContext()).load(verticalList.get(position).getLogoURL()).placeholder(R.mipmap.ic_launcher).into(holder.imView);
        }

        @Override
        public int getItemCount() {
            return verticalList.size();
        }
    }
}
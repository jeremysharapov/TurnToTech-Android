package io.tutorial.turntotech.infoOrganizerSample;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
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

public class ProductFragment extends Fragment {

    private RecyclerView product_recycler_view;
    private ArrayList<String> appleProducts,samsungProducts,motorolaProducts,microsoftProducts;
    private ProductFragment.VerticalProductAdapter recyclerProductAdapter;
    List<List<String>> listOfAllProducts;
    ImageButton addButton;
    ImageButton backButton;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(
                R.layout.activity_main2, container, false);

        product_recycler_view= (RecyclerView) view.findViewById(R.id.vertical_recycler_view);

        // Get the Company to display correct Products
        int companyNo = ((StartActivity) getActivity()).getCurrentCompanyNo();

        listOfAllProducts = new ArrayList<List<String>>();

        // apple Products
        appleProducts=new ArrayList<String>();
        appleProducts.add("Iphone");
        appleProducts.add("IPad");
        appleProducts.add("IPod");

        // Samsung Products
        samsungProducts =new ArrayList<String>();
        samsungProducts.add("Galaxy s");
        samsungProducts.add("Galaxy Note");
        samsungProducts.add("J7");

        // Moto Products
        motorolaProducts=new ArrayList<String>();
        motorolaProducts.add("Moto E");
        motorolaProducts.add("Moto G");
        motorolaProducts.add("Moto X");

        // Microsoft Products
        microsoftProducts=new ArrayList<String>();
        microsoftProducts.add("Lumia 540");
        microsoftProducts.add("Lumia 640");
        microsoftProducts.add("Lumia 925");

        // add all products to the main list
        listOfAllProducts.add(appleProducts);
        listOfAllProducts.add(samsungProducts);
        listOfAllProducts.add(motorolaProducts);
        listOfAllProducts.add(microsoftProducts);

        recyclerProductAdapter = new VerticalProductAdapter(listOfAllProducts.get(companyNo));


        LinearLayoutManager layoutmanager
                = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        product_recycler_view.setLayoutManager(layoutmanager);


        product_recycler_view.setAdapter(recyclerProductAdapter);


        // ActionBar SetUp
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.toolbar);
        backButton = (ImageButton)activity.findViewById(R.id.imageButton);
        addButton = (ImageButton)activity.findViewById(R.id.imageButton2);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Back",Toast.LENGTH_LONG).show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"Add",Toast.LENGTH_LONG).show();
            }
        });




        return view;
    }

    public class VerticalProductAdapter extends RecyclerView.Adapter<ProductFragment.VerticalProductAdapter.MyViewHolder> {

        private List<String> verticalList;

        public class MyViewHolder extends RecyclerView.ViewHolder {
            public TextView textProductName;

            public MyViewHolder(View view) {
                super(view);
                textProductName = (TextView) view.findViewById(R.id.textProductName);

            }
        }


        public VerticalProductAdapter(List<String> verticalList) {
            this.verticalList = verticalList;
        }

        @Override
        public ProductFragment.VerticalProductAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.vertical_product_item, parent, false);

            return new ProductFragment.VerticalProductAdapter.MyViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            holder.textProductName.setText(verticalList.get(position));
            holder.textProductName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   Toast.makeText(getContext(),holder.textProductName.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public int getItemCount() {
            return verticalList.size();
        }
    }
}
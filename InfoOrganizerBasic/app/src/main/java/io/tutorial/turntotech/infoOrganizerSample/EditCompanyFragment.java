package io.tutorial.turntotech.infoOrganizerSample;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.sql.SQLException;

public class EditCompanyFragment extends Fragment{
    EditText Name, LogoURL, StockTicker;
    Button submitButton;
    ImageButton backButton,addButton;
    CheckBox Edit;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_edit_company, container, false);
        Name = (EditText)view.findViewById(R.id.CompanyName);
        LogoURL = (EditText)view.findViewById(R.id.CompLogoURL);
        StockTicker = (EditText)view.findViewById(R.id.StockTicker);
        submitButton = (Button)view.findViewById(R.id.Finish);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.toolbar);
        backButton = (ImageButton)activity.findViewById(R.id.imageButton);
        addButton = (ImageButton)activity.findViewById(R.id.imageButton2);
        Edit = (CheckBox)activity.findViewById(R.id.EditInfo);
        Edit.setVisibility(View.INVISIBLE);
        addButton.setVisibility(View.INVISIBLE);

        if(DAO.getEdit()){
            Name.setText(DAO.getcompanyList().get(DAO.getCompanyNo()).getName());
            LogoURL.setText(DAO.getcompanyList().get(DAO.getCompanyNo()).getLogoURL());
            StockTicker.setText(DAO.getcompanyList().get(DAO.getCompanyNo()).getStock_ticker());
        }

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

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DAO.getEdit()){
                    if (LogoURL.getText().toString().equals("") && StockTicker.getText().toString().equals("")){
                        DAO.getcompanyList().get(DAO.getCompanyNo()).setName(Name.getText().toString());
                        DAO.getcompanyList().get(DAO.getCompanyNo()).setLogoURL("R.mipmap.ic_launcher");
                        DAO.getcompanyList().get(DAO.getCompanyNo()).setStock_ticker("_");
                    }
                    else if (LogoURL.getText().toString().equals("")){
                        DAO.getcompanyList().get(DAO.getCompanyNo()).setName(Name.getText().toString());
                        DAO.getcompanyList().get(DAO.getCompanyNo()).setLogoURL("R.mipmap.ic_launcher");
                        DAO.getcompanyList().get(DAO.getCompanyNo()).setStock_ticker(StockTicker.getText().toString());
                    }
                    else if (StockTicker.getText().toString().equals("")){
                        DAO.getcompanyList().get(DAO.getCompanyNo()).setName(Name.getText().toString());
                        DAO.getcompanyList().get(DAO.getCompanyNo()).setLogoURL(LogoURL.getText().toString());
                        DAO.getcompanyList().get(DAO.getCompanyNo()).setStock_ticker("_");
                    }
                    else {
                        DAO.getcompanyList().get(DAO.getCompanyNo()).setName(Name.getText().toString());
                        DAO.getcompanyList().get(DAO.getCompanyNo()).setLogoURL(LogoURL.getText().toString());
                        DAO.getcompanyList().get(DAO.getCompanyNo()).setStock_ticker(StockTicker.getText().toString());
                    }
                    try {
                        DAO.helper.getmCompanyDao().update(DAO.getcompanyList().get(DAO.getCompanyNo()));
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Company comp;
                    if (LogoURL.getText().toString().equals("") && StockTicker.getText().toString().equals("")) {
                        comp = new Company(Name.getText().toString(), "R.mipmap.ic_launcher", "_");
                    } else if (LogoURL.getText().toString().equals("")) {
                        comp = new Company(Name.getText().toString(), "R.mipmap.ic_launcher", StockTicker.getText().toString());
                    } else if (StockTicker.getText().toString().equals("")) {
                        comp = new Company(Name.getText().toString(), LogoURL.getText().toString(), "_");
                    } else {
                        comp = new Company(Name.getText().toString(), LogoURL.getText().toString(), StockTicker.getText().toString());
                    }
                    DAO.getcompanyList().add(comp);
                    try {
                        DAO.helper.getmCompanyDao().create(comp);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }

                Fragment CompanyFragment = new CompanyFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.mainLayout, CompanyFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        return view;
    }

}

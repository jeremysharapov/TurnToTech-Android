package io.tutorial.turntotech.infoOrganizerSample;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;



public class StartActivity extends AppCompatActivity {

    static int companyNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        companyNo = 0;

        FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.mainLayout, new CompanyFragment()).commit();
    }

    public int getCurrentCompanyNo()
    {
        return companyNo;
    }

    public void setCurrentCompanyNo(int pos)
    {
        companyNo = pos;
    }
}

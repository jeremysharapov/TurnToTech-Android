package org.turntotech.navigatesample;


import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;

public class DetailActivity extends ListActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String[][][] data = {
                {{ "Galaxy Tab", "Tablet"}, {"Galaxy S", "Smartphone"}, {"Gear", "SmartWatch"} },
                { {"iPhone", "Smartphone"}, {"iPad", "Tablet"}, {"iPod", "MP3 Player"} } };

        int[][] icons = {
                { R.drawable.gala, R.drawable.duos, R.drawable.star },
                { R.drawable.a, R.drawable.b, R.drawable.c }, };

        Intent intent = getIntent();
        int position1 = intent.getIntExtra("POSITION1", 0);
        int position2 = intent.getIntExtra("POSITION2", 1);
        setListAdapter(new CustomListAdapter(this, data[position1][position2], icons[position1]));
    }
}

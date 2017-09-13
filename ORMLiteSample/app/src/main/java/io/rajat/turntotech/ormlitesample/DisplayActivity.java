package io.rajat.turntotech.ormlitesample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DisplayActivity extends AppCompatActivity {

    ListView studentListView;
    ArrayList<Student> studentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        studentListView = (ListView)findViewById(R.id.studentList);


        // Fetch data from database
        try {
            // This is how, a reference of DAO object can be done
            // Need to find out list of TeacherDetails from database, so initialize DAO for TeacherDetails first
            final Dao<Student, Integer> studDAO = DBHelper.getInstance(DisplayActivity.this).getmStudentDAO();

            // Query the database. We need all the records so, used queryForAll()
            studentList = (ArrayList<Student>) studDAO.queryForAll();


        } catch (SQLException e) {
            e.printStackTrace();
        }




        // create another arraylist of String that convert StudentList to arraylist
        ArrayList<String> totalListinText = new ArrayList<>();
        for (Student student : studentList){
            totalListinText.add(student.toString());
        }


        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                totalListinText );

        studentListView.setAdapter(arrayAdapter);
    }
}

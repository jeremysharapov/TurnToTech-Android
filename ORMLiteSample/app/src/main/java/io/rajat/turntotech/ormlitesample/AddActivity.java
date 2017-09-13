package io.rajat.turntotech.ormlitesample;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

public class AddActivity extends AppCompatActivity {

    EditText name,address;
    Button addNew;

    void addStudentToDB(Student student) throws SQLException {
        // When you create a Student Also Create ompany in DB
        DBHelper.getInstance(AddActivity.this).getmStudentDAO().create(student);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        name = (EditText) findViewById(R.id.studentName);
        address = (EditText) findViewById(R.id.studentAddress);
        addNew = (Button) findViewById(R.id.addNewButton);



        addNew.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Student newStudent = null;
                        newStudent = new Student(name.getText().toString(),address.getText().toString());

                        // Add new Student
                        try {
                            addStudentToDB(newStudent);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }

                        name.setText("");
                        address.setText("");
                        Intent mainIntent = new Intent(AddActivity.this,MainActivity.class);
                        startActivity(mainIntent);
                    }
                }
        );
    }


}

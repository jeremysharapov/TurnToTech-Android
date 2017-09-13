package io.rajat.turntotech.ormlitesample;

import android.content.Context;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.SQLException;

/**
 * Created by Rajat on 4/18/17.
 */


@DatabaseTable(tableName = "Student")
public class Student implements Serializable {
    @DatabaseField(generatedId = true)
    int id;

    @DatabaseField
    String name;

    @DatabaseField
    String address;


    Student(String studentName, String studentAddress)  {
        this.name = studentName;
        this.address = studentAddress;
    }

    public Student()
    {

    }

    public int getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student [Name=" + name + ", Address=" + address
                + "]";
    }
}

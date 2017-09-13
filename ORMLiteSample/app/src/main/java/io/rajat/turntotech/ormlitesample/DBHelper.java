package io.rajat.turntotech.ormlitesample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Rajat on 4/19/17.
 */

public class DBHelper extends OrmLiteSqliteOpenHelper {


    private static final String DATABASE_NAME    = "studentDB.db";
    private static final int    DATABASE_VERSION = 5;

    private static Dao<Student, Integer> mStudentDAO = null;
    private static DBHelper sharedInstance;


    public static DBHelper getInstance(Context context) throws SQLException {
        if(sharedInstance == null) {
            sharedInstance = new DBHelper(context);
        }
        return sharedInstance;
    }

    protected DBHelper(Context context) throws SQLException {
        super(context, DATABASE_NAME , null, DATABASE_VERSION);
        mStudentDAO = getDao(Student.class);
    }

    /* Company */
    public  Dao<Student, Integer> getmStudentDAO() throws SQLException {

        return mStudentDAO;
    }





    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Student.class);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Student.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package io.tutorial.turntotech.infoOrganizerSample;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by Jeremy on 8/2/2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "ormLite.db";
    private static final int DATABASE_VERSION = 5;

    private Dao<Company, Integer> mCompanyDao = null;
    private Dao<Product, Integer> mProductDao = null;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try{
            TableUtils.createTable(connectionSource, Company.class);
            TableUtils.createTable(connectionSource, Product.class);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try{
            TableUtils.dropTable(connectionSource, Company.class, true);
            TableUtils.dropTable(connectionSource, Product.class, true);
            onCreate(database, connectionSource);
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public Dao<Company, Integer> getmCompanyDao() throws SQLException{
        if (mCompanyDao == null){
            mCompanyDao = getDao(Company.class);
        }
        return mCompanyDao;
    }

    public Dao<Product, Integer> getmProductDao() throws SQLException{
        if (mProductDao == null){
            mProductDao = getDao(Product.class);
        }
        return mProductDao;
    }

    @Override
    public void close(){
        mCompanyDao = null;
        mProductDao = null;
        super.close();
    }
}

package com.databasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHandler {
	public static final String NAME = "name";
	public static final String EMAIL = "email";
	public static final String TABLE_NAME = "mytable";
	public static final String DATA_BASE_NAME = "mydatabase";
	public static final int DATABASE_VERSION = 1;
	public static final String TABLE_CREATE = "create table mytable (name text not null,email text not null );";

	DataBaseHelper dbhelper;
	Context ctx;
	SQLiteDatabase db;

	public DataHandler(Context ctx) {
		this.ctx = ctx;
		dbhelper = new DataBaseHelper(ctx);
	}

	//A SQLiteOpenHelper class to manage database creation and version management. 
	private static class DataBaseHelper extends SQLiteOpenHelper {

		public DataBaseHelper(Context ctx) {
			super(ctx, DATA_BASE_NAME, null, DATABASE_VERSION);
		}

		@Override
		/* Called when the database is created for the first time.
		 * This is where the creation of tables and the initial
		 * population of the tables should happen.
		 */
		public void onCreate(SQLiteDatabase db) {

			try {
				/* execSQL - method not only insert data , but also used to
				 * update or modify already existing data in database
				 * using bind arguments
				*/
				db.execSQL(TABLE_CREATE);
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

		/* This method is only called when your DATABASE_VERSION variable changes, you do not
		 * have to call it yourself. It simply tells the database to delete your table(s) if they
		 * exist, and then tells it to create it again.
		 */
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS mytable ");
			onCreate(db);
		}

	}

	public DataHandler open() {
		//Create and/or open a database that will be used for reading and writing.
		db = dbhelper.getWritableDatabase();
		return this;
	}

    // Close the helper class
	public void close() {
		dbhelper.close();
	}

	// Handle inserting a name and email into our database
	public long insertData(String name, String email) {
		ContentValues content = new ContentValues();
		content.put(NAME, name);
		content.put(EMAIL, email);
		return db.insertOrThrow(TABLE_NAME, null, content);
	}

    // TODO: Add a feature so that you can edit the first name and leave the last name the same.
    // Hint: Use the SQL UPDATE statement.


	// Retrieve the names and emails from our database.
	public Cursor returnData() {
        return db.query(TABLE_NAME, new String[] { NAME, EMAIL }, null, null,
				null, null, null);
    }



}

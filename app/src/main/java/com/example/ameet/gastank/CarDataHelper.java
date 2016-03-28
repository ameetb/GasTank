package com.example.ameet.gastank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Ameet on 2/4/2016.
 */
public class CarDataHelper {

    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "cardata1.db";
    //Table
    private static final String TABLE_NAME = "cargasdata";
    //Table Column Variable
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String MPG = "mpg";
    public static final String GASREMAINING = "gasremaining";
    public static final String TANKSIZE = "tanksize";


    private DataHelper openHelper;
    private SQLiteDatabase sqlDb;

    //Database creation sql statement
    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " +
            TABLE_NAME + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            NAME + " TEXT," +
            MPG + " TEXT,"+
            TANKSIZE + " TEXT,"
            + GASREMAINING + " TEXT);";

    public CarDataHelper(Context context) {
        openHelper = new DataHelper(context);
        sqlDb = openHelper.getWritableDatabase();
    }

    //to insert data into database
    public void saveRecords(String name, String mpg,String gasTankSize, String gasRemaining) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,name);
        contentValues.put(MPG, mpg);
        contentValues.put(GASREMAINING, gasRemaining);
        contentValues.put(TANKSIZE, gasTankSize);
        sqlDb.insert(TABLE_NAME, null, contentValues);
    }
    public void updateRecords(int position, double newGasRemaining){

        Log.e("update records", String.valueOf(newGasRemaining));
        ContentValues cv = new ContentValues();
        cv.put(GASREMAINING,newGasRemaining);
        sqlDb.update(TABLE_NAME, cv, null, null);
        Cursor c=getAllRecords();
        c.moveToFirst();
        while (c.getPosition()!=position){
            c.moveToNext();
        }
        String s= c.getString(c.getColumnIndex(GASREMAINING));
        Log.e("cursorval",s);



    }

    //get fetch the all data
    public Cursor getAllRecords() {
        return sqlDb.rawQuery("select * from " + TABLE_NAME, null);
    }


    // inner class
    private class DataHelper extends SQLiteOpenHelper {

        public DataHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            // TODO Auto-generated constructor stub

        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // TODO Auto-generated method stub
            System.out.println("create statement"+SQL_CREATE_ENTRIES);
            try
            {
                db.execSQL(SQL_CREATE_ENTRIES);

            }catch(SQLiteException sql)
            {
                sql.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // TODO Auto-generated method stub
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}
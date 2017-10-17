package com.example.jaishreeupreti.notify;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class preferenceDB extends SQLiteOpenHelper {

    public String path = "/data/data/com.example.jaishreeupreti.notify/databases/preferencedbmanager.db";
    String table_name = "table1";

    public preferenceDB(Context context) {
        super(context, "preferencedbmanager.db", null, 1);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE table1 (preference text primary key,subcategories text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + "table1");

        // Create tables again
        onCreate(db);
    }

    public long addRecord(String preference, String subcategories) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("preference", preference);
        values.put("subcategories", subcategories);
        long b = db.insert(table_name, null, values);
        db.close();
        return b;
    }


    public int deleteRecord(String preference) {
        SQLiteDatabase db = this.getWritableDatabase();


        int ret = db.delete(table_name, "preference=?", new String[]{preference});

        db.close();
        return ret;

    }

    public boolean recordExists(String preference, String subcategories) {
        boolean exists = false;
        String result = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select preference from table1 where preference=?", new String[]{preference});
        if (cursor.moveToNext()) {
            result = cursor.getString(0);
        }
        if (result != null)
            exists = true;
        cursor.close();
        db.close();

        return exists;
    }

    public ArrayList<String[]> getArrayList() {
        ArrayList<String[]> tracks = new ArrayList<String[]>();
        int i = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("databaseee", "yup");
        Cursor c = db.rawQuery("select * from table1", null);
        try {
            if (c != null) {
                c.moveToFirst();


                while (!c.isAfterLast()) {

                    String[] record = {c.getString(0), c.getString(1)};
                    tracks.add(record);
                    c.moveToNext();

                }

            }
        } catch (SQLiteException e) {

            Log.e("Retrieve Data", "Unable to get Data " + e);
            e.printStackTrace();
        }
        c.close();
        db.close();

        return tracks;

    }

    public String[] getPreferences(int code) {
        String[] tracks = new String[100];
        int i = 0;
        Cursor c;
        SQLiteDatabase db = this.getReadableDatabase();
        Log.d("databaseee", "yup");
        if (code == 1)
            c = db.rawQuery("select preference from table1", null);
        else
            c = db.rawQuery("select subcategories from table1", null);
        try {
            if (c != null) {
                c.moveToFirst();


                while (!c.isAfterLast()) {

                    tracks[i++] = c.getString(0);
                    Log.d("databaseeeeee", c.getString(0) + "    " + i);
                    c.moveToNext();

                }

            }
        } catch (SQLiteException e) {

            Log.e("Retrieve Data", "Unable to get Data " + e);
            e.printStackTrace();
        }
        c.close();
        db.close();

        return tracks;

    }


}

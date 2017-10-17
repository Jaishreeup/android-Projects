package com.example.notifier;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class NotificationDB extends SQLiteOpenHelper 
{
    public String path = "/data/data/com.example.notifier/databases/notificationdbmanager.db";
    String table_name = "table1";
    public NotificationDB(Context context) 
    {
        super(context, "notificationdbmanager.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) 
    {
        String sql = "CREATE TABLE table1 (Organiser text, EventName text, Date text, Info text, Issuer text, Id text,preference text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
    {
        // TODO Auto-generated method stub
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS table1");
        onCreate(db);
    }

    public long addRecord(String organiser, String event_name, String date, String info, String issuer, String id, String preference) 
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Organiser", organiser);
        values.put("EventName", event_name);
        values.put("Date", date);
        values.put("Info", info);
        values.put("Issuer", issuer);
        values.put("Id", id);
        values.put("preference", preference);
        long b = db.insert(table_name, null, values);
        db.close();
        return b;
    }

    public int deleteRecord(String notificatonId) 
    {
        SQLiteDatabase db = this.getWritableDatabase();
        int ret = db.delete(table_name, "Id=?", new String[]{notificatonId});
        db.close();
        return ret;
    }

    public boolean recordExists(String notificationId) 
    {
        boolean exists = false;
        String result = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Id from table1 where Id=?", new String[]{notificationId});
        if (cursor.moveToNext()) 
        {
            result = cursor.getString(0);
        }
        if (result != null)
            exists = true;
        cursor.close();
        db.close();
        return exists;
    }

    public ArrayList<String[]> getArrayList(String preference) 
    {
        ArrayList<String[]> tracks = new ArrayList<String[]>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from table1 where preference=?", new String[]{preference});
        try {
            if (c != null) {
                c.moveToFirst();


                while (!c.isAfterLast()) {

                    String[] record = {c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)};
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


}

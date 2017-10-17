package com.example.jaishreeupreti.notice;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.math.BigInteger;
import java.util.ArrayList;

public class NotificationDB extends SQLiteOpenHelper {
    public String path = "/data/data/com.example.notifier/databases/notificationdbmanager.db";
    String table_name = "table1";
    SharedPreferences sp;

    public NotificationDB(Context context) {
        super(context, "notificationdbmanager.db", null, 1);
        sp = context.getSharedPreferences("notify", context.MODE_PRIVATE);
        sp = context.getSharedPreferences("notify", context.MODE_PRIVATE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE table1 ( EventName text, Date text, Info text, Issuer text, Id text,preference text,file text,saved int)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS table1");
        onCreate(db);
    }

    public long addRecord(String event_name, String date, String info, String issuer, String id, String preference, String file, int saved) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("EventName", event_name);
        values.put("Date", date);
        values.put("Info", info);
        values.put("Issuer", issuer);
        values.put("Id", id);
        values.put("preference", preference);
        values.put("file", file);
        values.put("saved", saved);
        long b = db.insert(table_name, null, values);
        db.close();
        return b;
    }

    public int deleteRecord(String notificatonId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int ret = db.delete(table_name, "Id=?", new String[]{notificatonId});
        db.close();
        return ret;
    }
    public int deletePreference(String preference) {
        SQLiteDatabase db = this.getWritableDatabase();
        int ret = db.delete(table_name, "preference=? and saved=0", new String[]{preference});
        db.close();
        return ret;
    }

    public boolean recordExists(String notificationId) {
        boolean exists = false;
        String result = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select Id from table1 where Id=?", new String[]{notificationId});
        if (cursor.moveToNext()) {
            result = cursor.getString(0);
        }
        if (result != null)
            exists = true;
        cursor.close();
        db.close();
        return exists;
    }
  public int setSaved(String notificatonId)
  {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues cv = new ContentValues();
      cv.put("saved", 1); //These Fields should be your String values of actual column names
      int ret = db.update(table_name,cv, "Id=?", new String[]{notificatonId});
      db.close();
      return ret;

  }


    public ArrayList<String[]> getArrayList(String preference) {
        ArrayList<String[]> tracks = new ArrayList<String[]>();
        SQLiteDatabase db = this.getReadableDatabase();
        System.out.println("preffff " + preference);
        Cursor c = db.rawQuery("select * from table1 where preference=? and saved=0 order by Id desc", new String[]{preference});
        try {
            if (c != null) {
                c.moveToFirst();


                while (!c.isAfterLast()) {
                    System.out.println("aaaaaaaaaaa");
                    String[] record = {c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)};
                    System.out.println(c.getString(0) + c.getString(1) + c.getString(2) + c.getString(3) + c.getString(4) + c.getString(5) + "hello" + c.getString(6));
                    System.out.println(c.getString(c.getColumnIndex("preference")));
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
    public ArrayList<String[]> getSaved() {
        ArrayList<String[]> tracks = new ArrayList<String[]>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("select * from table1 where saved=1 order by Id desc",null);
        try {
            if (c != null) {
                c.moveToFirst();


                while (!c.isAfterLast()) {
                    System.out.println("aaaaaaaaaaa");
                    String[] record = {c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)};
                    System.out.println(c.getString(0) + c.getString(1) + c.getString(2) + c.getString(3) + c.getString(4) + c.getString(5) + "hello" + c.getString(6));
          //          System.out.println(c.getString(c.getColumnIndex("preference")));
                    tracks.add(record);
                    c.moveToNext();

                }

            }
        } catch (SQLiteException e) {

            Log.e("Retrieve Data", "Unable to get Data " + e);
            Log.e("Retrieve Data", "Unable to get Data " + e);
            e.printStackTrace();
        }
        c.close();
        db.close();

        return tracks;

    }

    public int newNotificationCount(String oldId) {
        SQLiteDatabase db = this.getReadableDatabase();
        int count = 0;
        String pref, idcheck;
        Cursor c = db.rawQuery("select preference,id from table1 where id>?", new String[]{oldId});
        try {
            if (c != null) {

                c.moveToFirst();

                while (!c.isAfterLast()) {
                    pref = c.getString(0);
                    idcheck = c.getString(1);
                    if (sp.getBoolean(pref, false) && new BigInteger(idcheck).compareTo(new BigInteger(oldId)) > 0) {
                        sp.edit().putBoolean("newNot" + pref, true).commit();
                        count++;
                    }
                    c.moveToNext();
                }

            }
        } catch (SQLiteException e) {

            e.printStackTrace();
        }
        c.close();
        db.close();
        return count;
    }
}

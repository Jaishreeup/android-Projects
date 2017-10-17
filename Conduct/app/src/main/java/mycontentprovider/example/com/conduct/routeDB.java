package mycontentprovider.example.com.conduct;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

@SuppressLint("SdCardPath")
public class routeDB extends SQLiteOpenHelper {
    public String path = "/data/data/com.example.conductormodule/databases/routedbmanager.db";
    String table_name = "table1";

    public routeDB(Context context) {
        super(context, "routedbmanager.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE table1 (bid text primary key,num_stops text,route text)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS table1");
        onCreate(db);
    }

    public long addRecord(String bid, String num_stops, String route) {
        deleteRecord(bid);
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("bid", bid);
        values.put("num_stops", num_stops);
        values.put("route", route);
        long b = db.insert(table_name, null, values);
        db.close();
        return b;
    }


    public int deleteRecord(String bid) {
        SQLiteDatabase db = this.getWritableDatabase();
        int ret = db.delete(table_name, "bid=?", new String[]{bid});
        db.close();
        return ret;
    }

    public boolean recordExists(String bid) {
        boolean exists = false;
        String result = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select preference from table1 where bid=?", new String[]{bid});
        if (cursor.moveToNext()) {
            result = cursor.getString(0);
        }
        if (result != null)
            exists = true;
        cursor.close();
        db.close();
        return exists;
    }

    public String getRoute(String bid) {
        int i = 0;
        Cursor c;
        String result = "";
        SQLiteDatabase db = this.getReadableDatabase();
        c = db.rawQuery("select route from table1 where  bid=?", new String[]{bid});
        try {
            if (c != null) {
                c.moveToFirst();
                result = c.getString(0);
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        c.close();
        db.close();
        return result;
    }

    public String getStops(String bid) {
        int i = 0;
        Cursor c;
        String result = "";
        SQLiteDatabase db = this.getReadableDatabase();
        c = db.rawQuery("select num_stops from table1 where  bid=?", new String[]{bid});

        try {
            if (c != null) {
                System.out.println("lo yahan hu" + bid);
                c.moveToFirst();
                result = c.getString(0);
                System.out.println("lo yahan hu" + c);
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        c.close();
        db.close();
        return result;
    }

}

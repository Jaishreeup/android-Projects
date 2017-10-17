package mycontentprovider.example.com.mymoney;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;
import java.util.Map;

@SuppressLint("SdCardPath")
public class moneyDB extends SQLiteOpenHelper {
    public String path = "/data/data/com.example.mymoney/databases/moneydbmanager.db";
    String table_name = "table1";

    public moneyDB(Context context) {
        super(context, "moneydbmanager.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE table1 (name text,money integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS table1");
        onCreate(db);
    }

    public boolean recordExists(String name) {
        boolean exists = false;
        String result = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select name from table1 where name=?", new String[]{name});
        if (cursor.moveToNext()) {
            result = cursor.getString(0);
        }
        if (result != null)
            exists = true;
        cursor.close();
        db.close();
        return exists;
    }
    public long addRecord(String name, int money) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("money", money);
        long b = db.insert(table_name, null, values);
        System.out.println("ccccccccc ");
        System.out.println("ccccccccc ");
        db.close();
        return b;
    }

    public int getMoney(String name) {
        Cursor c;
        int result=0;
        SQLiteDatabase db = this.getReadableDatabase();
        c = db.rawQuery("select money from table1 where  name=?", new String[]{name});

        try {
            if (c != null) {
                c.moveToFirst();
                result = c.getInt(0);
            }
        }
        catch (SQLiteException e) {
            e.printStackTrace();
        }
        c.close();
        db.close();
        return result;
    }
    public int setMoney(String name,int money) {
        int result=0;
        SQLiteDatabase db = this.getWritableDatabase();
         db.execSQL("update table1 set money=" + money + " where  name=?", new String[]{name});
        db.close();
        return result;
    }
    public Map<String,Integer> getLent()
    {
        Cursor c;
        Map result=new HashMap<String,Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        c = db.rawQuery("select name,money from table1 where money>0 ",null);

        try {
            if (c != null) {
                c.moveToFirst();


                while (!c.isAfterLast()) {
                    System.out.println("ccccccccc " + result.size());
                    System.out.println("ccccccccc ");
                    result.put(c.getString(0), c.getInt(1));
                    c.moveToNext();

                }
            }
        }     catch (SQLiteException e) {
            e.printStackTrace();
        }
        c.close();
        db.close();
        return result;

    }
    public Map<String,Integer> getDebt()
    {
        Cursor c;
        Map result=new HashMap<String,Integer>();
        SQLiteDatabase db = this.getReadableDatabase();
        c = db.rawQuery("select name,money from table1 where money<0 ",null);

        try {
            if (c != null) {
                c.moveToFirst();


                while (!c.isAfterLast()) {
                    System.out.println("ccccccccc " + result.size());
                    System.out.println("ccccccccc ");
                    result.put(c.getString(0), c.getInt(1));
                    c.moveToNext();

                }
            }
        }     catch (SQLiteException e) {
            e.printStackTrace();
        }
        c.close();
        db.close();
        return result;

    }
}
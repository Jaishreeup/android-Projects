package mycontentprovider.example.com.cricket;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by JAISHREE UPRETI on 20-10-2015.
 */
public class infodb extends SQLiteOpenHelper {
    public String path = "/data/data/mycontentprovider.example.com.cricket/databases/infodbmanager.db";
    String table_name = "table1";

    public infodb(Context context) {
        super(context, "infodbmanager.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE table1 (id text,name text,age integer,country text,runs integer,wickets integer,catches integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS table1");
        onCreate(db);
    }

    public long addRecord(String id, String name, int age, String country, int runs, int wickets, int catches) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", id);
        values.put("name", name);
        values.put("age", age);
        values.put("country", country);
        values.put("runs", runs);
        values.put("wickets", wickets);
        values.put("catches", catches);

        long b = db.insert(table_name, null, values);
        db.close();
        return b;
    }
    public long modifyRecord(String id, String name, int age, String country, int runs, int wickets, int catches) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
     //   values.put("id", id);
        System.out.println("inside db");
        values.put("name", name);
        values.put("age", age);
        values.put("country", country);
        values.put("runs", runs);
        values.put("wickets", wickets);
        values.put("catches", catches);

        long b = db.update(table_name, values,"id"+"="+id,null);
        System.out.println("outside db");
        db.close();
        return b;
    }


    public int deleteRecord(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("db me hu ");
        int ret = db.delete(table_name, "id=?", new String[]{id});
        System.out.println("db me hu "+id);
        db.close();
        System.out.println("db me hu " + id);
        return ret;
    }
  public String[] getAll(String id)
  {
      String[] str=null;//=new  String[7];
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor c = null;
      c = db.rawQuery("select * from table1 where id=?", new String[]{id});
      try {
          if (c != null) {
              c.moveToFirst();
                   str = new String[]{c.getString(0),c.getString(1),""+ c.getInt(2),c.getString(3),""+c.getInt(4),""+c.getInt(5),""+c.getInt(6)};
          }
      } catch (SQLiteException e) {
          e.printStackTrace();
      }
      c.close();
      db.close();
      return str;

  }

    public ArrayList<String[]> getNames() {
        ArrayList<String[]> tracks = new ArrayList<String[]>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = null;
        c = db.rawQuery("select id,name from table1", null);
        try {
            if (c != null) {
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    String[] record = {c.getString(0),c.getString(1)};
                    tracks.add(record);
                    c.moveToNext();
                }
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        c.close();
        db.close();
        return tracks;
    }

        public ArrayList<String[]> getArrayList(String arg) {
        ArrayList<String[]> tracks = new ArrayList<String[]>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c=null;
            System.out.println("in  dbbb: "+arg);
                c = db.rawQuery(arg,null);
            System.out.println("in  dbbb: "+arg);

            try {
            if (c != null) {
                c.moveToFirst();
                while (!c.isAfterLast()) {
                    String[] record = {c.getString(0), c.getString(1),""+c.getInt(2), c.getString(3),""+c.getInt(4),""+ c.getInt(5),""+c.getInt(6)};
                    tracks.add(record);
                    c.moveToNext();
                }
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        c.close();
        db.close();
        return tracks;
    }

}

package com.example.sports;

import java.util.ArrayList;
import java.util.Currency;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Date;
public class Infodb extends SQLiteOpenHelper{
	public Infodb(Context context) {
		super(context, "infodbmanager.db", null, 1);
		// TODO Auto-generated constructor stub
	}

	public String path = "/data/data/com.example.sports/databases/infodbmanager.db";
    String table_name = "table1";
 
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String sql = "CREATE TABLE table1 (id text,sport text, Vacancies integer)";
        db.execSQL(sql);
    
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		 db.execSQL("DROP TABLE IF EXISTS table1");
	        onCreate(db);
	}
	 public long addRecord(String id,String sport,int vacancies) {
	        SQLiteDatabase db = this.getWritableDatabase();
	        ContentValues values = new ContentValues();
	        values.put("id", id);
	        values.put("sport", sport);
	        //values.put("date", date);
	        //values.put("time", time);
	        values.put("Vacancies", vacancies);
	        
	        long b = db.insert(table_name, null, values);
	        db.close();
	        return b;
	    }
	 
	 public long modifyRecord(String id,String sport, int vacancies  ) {
	        SQLiteDatabase db = this.getWritableDatabase();
	        ContentValues values = new ContentValues();
	     //   values.put("id", id);
	        System.out.println("inside db");
	        //values.put("id", id);
	        values.put("sport", sport);
	       // values.put("Date", date);
	       // values.put("time", time);
	        values.put("Vacancies", vacancies);
	        
	       
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
	                   str = new String[]{c.getString(0),c.getString(1),""+c.getInt(2)};
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
	        c = db.rawQuery("select id,sport from table1", null);
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
	                    String[] record = {c.getString(0), c.getString(1),""+c.getInt(2)};
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

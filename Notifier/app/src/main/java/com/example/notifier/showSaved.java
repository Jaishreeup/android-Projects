package com.example.notifier;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class showSaved extends Activity {

    NotificationDB db;
    ArrayList<String[]> arrayList;
    TextView t;
    ListView listView;
    String[] str;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_saved);
        db=new NotificationDB(getApplicationContext());
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/MERCEDES.TTF");
        t = (TextView) findViewById(R.id.tvcs);
        t.setTextSize(40);
        t.setTypeface(typeFace);
        t.setText("Saved Notices");
        listView = (ListView) findViewById(R.id.nlist);
        registerForContextMenu(listView);
        t = (TextView) findViewById(R.id.textView3);
        db = new NotificationDB(this);
        refresh();
  /*      int ukk = arrayList.size();
        final String notList[] = new String[arrayList.size()];
        for (int i = 0; i < ukk; i++) {
            str =arrayList.get(i);
            notList[i] = str[0];
        }
        if (arrayList.size() == 0)
            t.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_textview, R.id.tv, notList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String[] str = arrayList.get(position);
                for (int i = 0; i < str.length; i++)
                    System.out.println("item :" + i + "  " + str[i]);
                if (str[6].equals("empty")) {
                    Intent intent = new Intent(getApplicationContext(), info.class);
                    intent.putExtra("details", arrayList.get(position));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("fname", str[6]);
                    startActivity(intent);
                }
            }
        });
*/
    }
    public void refresh()
    {
        arrayList=db.getSaved();
        int ukk = arrayList.size();
        final String notList[] = new String[arrayList.size()];
        for (int i = 0; i < ukk; i++) {
            str =arrayList.get(i);
            notList[i] = str[0];
        }
        if (arrayList.size() == 0)
            t.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_textview, R.id.tv, notList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String[] str = arrayList.get(position);
                for (int i = 0; i < str.length; i++)
                    System.out.println("item :" + i + "  " + str[i]);
                if (str[6].equals("empty")) {
                    Intent intent = new Intent(getApplicationContext(), info.class);
                    intent.putExtra("details", arrayList.get(position));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("fname", str[6]);
                    startActivity(intent);
                }
            }
        });

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.nlist) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.saved_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String[] str=arrayList.get(info.position);
        switch(item.getItemId()) {
            case R.id.delete:
                db.deleteRecord(str[4]);
                refresh();
                // remove stuff here
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notifications, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

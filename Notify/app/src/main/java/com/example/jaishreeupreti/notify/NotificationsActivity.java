package com.example.jaishreeupreti.notify;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class NotificationsActivity extends ActionBarActivity {
    String preference;
    ListAdapter la;
    ListView listView;
    NotificationDB db;
    String[] str;
    TextView t;
    ArrayList<String[]> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Log.d("checking", "there");
        preference = getIntent().getExtras().get("preference").toString();
        listView = (ListView) findViewById(R.id.nlist);
        t=(TextView)findViewById(R.id.textView3);
        db = new NotificationDB(this);
        notifications = db.getArrayList(preference);
        final String notList[] = new String[notifications.size()];
        for (int i = 0; i < notifications.size(); i++) {
            str = notifications.get(i);
            notList[i++] = str[1];
        }
        if(notifications.size()==0)
            t.setVisibility(1);
        Log.d("checking", "mhere"+preference);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, notList);
        Log.d("checking", "mhere");
        listView.setAdapter(adapter);
        Log.d("checking", "mhere");
        Toast.makeText(getApplicationContext(), "yoo", Toast.LENGTH_SHORT).show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Log.d("checkingooo", "mhere");
                Intent intent = new Intent(getApplicationContext(), info.class);
                intent.putExtra("details", notifications.get(position));
                startActivity(intent);

            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notifications, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

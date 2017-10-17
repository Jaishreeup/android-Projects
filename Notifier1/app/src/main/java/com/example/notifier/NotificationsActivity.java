package com.example.notifier;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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


public class NotificationsActivity extends Activity 
{
    String preference;
    ListAdapter la;
    ListView listView;
    NotificationDB db;
    String[] str;
    TextView t;
    ArrayList<String[]> notifications;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        preference = getIntent().getExtras().get("preference").toString();
        listView = (ListView) findViewById(R.id.nlist);
        t=(TextView)findViewById(R.id.textView3);
        db = new NotificationDB(this);
        notifications = db.getArrayList(preference);
        final String notList[] = new String[notifications.size()];
        for (int i = 0; i < notifications.size(); i++) 
        {
            str = notifications.get(i);
            notList[i++] = str[1];
        }
        if(notifications.size()==0)
            t.setVisibility(1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, notList);
        listView.setAdapter(adapter);
        Toast.makeText(getApplicationContext(), "yoo", Toast.LENGTH_SHORT).show();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() 
        {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) 
            {
            	Toast.makeText(getApplicationContext(), "asdasd", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), info.class);
                intent.putExtra("details", notifications.get(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notifications, menu);
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

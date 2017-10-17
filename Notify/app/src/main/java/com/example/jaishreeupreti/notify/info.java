package com.example.jaishreeupreti.notify;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class info extends Activity {
    TextView a, b, c, d, e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       setContentView(R.layout.activity_info);
        a = (TextView) findViewById(R.id.textView);
        b = (TextView) findViewById(R.id.event);
        c = (TextView) findViewById(R.id.date);
        d = (TextView) findViewById(R.id.info);
        e = (TextView) findViewById(R.id.issuer);
        String str[] = (String[]) getIntent().getExtras().get("details");
        a.setText(str[0]);
        b.setText(str[1]);
        c.setText(str[2]);
        d.setText(str[3]);
        e.setText(str[4]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
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

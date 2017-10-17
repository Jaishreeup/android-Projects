package com.example.jaishreeupreti.notice;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class info extends Activity {
    TextView b, c, d, e;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        b = (TextView) findViewById(R.id.event);
        c = (TextView) findViewById(R.id.date);
        d = (TextView) findViewById(R.id.info);
        e = (TextView) findViewById(R.id.issuer);
        String str[] = (String[]) getIntent().getExtras().get("details");
        b.setText(str[0]);
        c.setText(str[1]);
        d.setText(str[2]);
        e.setText(str[3]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.info, menu);
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

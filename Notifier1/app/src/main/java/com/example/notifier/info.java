package com.example.notifier;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;


public class info extends Activity 
{
    TextView  b, c, d, e;

    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        b = (TextView) findViewById(R.id.event);
        c = (TextView) findViewById(R.id.date);
        d = (TextView) findViewById(R.id.info);
        e = (TextView) findViewById(R.id.issuer);
        String str[] = (String[]) getIntent().getExtras().get("details");
        b.setPaintFlags(b.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);	
        b.setText(str[1]);
        c.setText(str[2]);
        d.setText(str[3]);
        e.setText(str[4]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        // Inflate the menu; this adds items to the action bar if it is present.
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

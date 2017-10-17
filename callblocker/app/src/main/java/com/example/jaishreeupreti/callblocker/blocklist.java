package com.example.jaishreeupreti.callblocker;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


public class blocklist extends ActionBarActivity {

    TextView layout;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blocklist);
        sp=getSharedPreferences("callblocker", Context.MODE_PRIVATE);
        String blocklist=sp.getString("blocklist","");
        Log.d("blist",blocklist);
        layout = (TextView) findViewById(R.id.blist);
        if(blocklist.equals(""))
        { Log.d("here","yup");
            layout.setText("\n\n  blocklist is empty!!");
        }
        else
            layout.setText(blocklist);
    }
    public void emptyblist(View view)
    {
        sp.edit().putString("blocklist","").commit();
        layout.setText("\n\n  blocklist is empty!!");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_blocklist, menu);
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

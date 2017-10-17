package com.example.notifier;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class abtDev extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abt_dev);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/MERCEDES.TTF");
        TextView view = (TextView) findViewById(R.id.title);
        view.setTypeface(typeFace);
        view = (TextView) findViewById(R.id.texthead);
        String str = "This application is developed by B.Tech Final year students CSE Branch.";
        view.setText(str);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_abt_dev, menu);
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

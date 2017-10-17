package com.example.notifier;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class AbtApp extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abt_app);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/MERCEDES.TTF");
        TextView view = (TextView) findViewById(R.id.textView1);
        view.setTypeface(typeFace);
        view = (TextView) findViewById(R.id.textview);
        String str = "Notifier is an app which can be used to view the college notices from anywhere, anytime.\nNow no need to check the notice boards daily because you'll automatically be notified about any new notices from your set preferences.\n\nHave a wonderful experience with your handy notice board!!";
        view.setText(str);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_abt_app, menu);
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

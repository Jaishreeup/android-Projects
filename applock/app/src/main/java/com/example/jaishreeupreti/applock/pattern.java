package com.example.jaishreeupreti.applock;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Toast;


public class pattern extends ActionBarActivity {
GridLayout pattern;
ImageView  dots[]=new ImageView[9];
    String pat="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pattern);
        pattern = (GridLayout) findViewById(R.id.pattern);
        dots[0] = (ImageView) findViewById(R.id.imageView1);
        dots[1] = (ImageView) findViewById(R.id.imageView2);
        dots[2] = (ImageView) findViewById(R.id.imageView3);
        dots[3] = (ImageView) findViewById(R.id.imageView4);
        dots[4] = (ImageView) findViewById(R.id.imageView5);
        dots[5] = (ImageView) findViewById(R.id.imageView6);
        dots[5] = (ImageView) findViewById(R.id.imageView7);
        dots[7] = (ImageView) findViewById(R.id.imageView8);
        dots[8] = (ImageView) findViewById(R.id.imageView9);
        dots[0].setId(1);
        dots[1].setId(2);
        dots[2].setId(3);
        dots[3].setId(4);
        dots[4].setId(5);
            dots[0].setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                 Toast.makeText(getApplicationContext(),"hi"+event.getAction(),Toast.LENGTH_LONG).show();
                    switch (event.getAction()) {
                        case MotionEvent.ACTION_DOWN:
                            pat += "" + v.getId();
                            if (pat == "1234")
                                Toast.makeText(getApplicationContext(), "found", Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(), "down" + pat, Toast.LENGTH_SHORT).show();

                            break;
                        case MotionEvent.ACTION_UP:
                            pat = "";
                            Toast.makeText(getApplicationContext(), "up", Toast.LENGTH_SHORT).show();
                            break;

                    }

                    //   Toast.makeText(getApplicationContext(),"touched",Toast.LENGTH_SHORT).show();
                    return false;
                }

            });
        dots[1].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        pat += "" + v.getId();
                        if (pat == "1234")
                            Toast.makeText(getApplicationContext(), "found", Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "down" + pat, Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_UP:
                        pat = "";
                        Toast.makeText(getApplicationContext(), "up", Toast.LENGTH_SHORT).show();
                        break;
                }

                //   Toast.makeText(getApplicationContext(),"touched",Toast.LENGTH_SHORT).show();
                return false;
            }

        });
        dots[2].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        pat += "" + v.getId();
                        if (pat == "1234")
                            Toast.makeText(getApplicationContext(),"found",Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "down"+pat, Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_UP:
                        pat = "";
                        Toast.makeText(getApplicationContext(), "up", Toast.LENGTH_SHORT).show();
                        break;
                }

                //   Toast.makeText(getApplicationContext(),"touched",Toast.LENGTH_SHORT).show();
                return false;
            }

        });
        dots[3].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        pat += "" + v.getId();
                        if (pat == "1234")
                            Toast.makeText(getApplicationContext(),"found",Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "down"+pat, Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_UP:
                        pat = "";
                        Toast.makeText(getApplicationContext(), "up", Toast.LENGTH_SHORT).show();
                        break;
                }

                //   Toast.makeText(getApplicationContext(),"touched",Toast.LENGTH_SHORT).show();
                return false;
            }

        });
        dots[4].setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        pat += "" + v.getId();
                        if (pat == "1234")
                            Toast.makeText(getApplicationContext(),"found",Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext(), "down"+pat, Toast.LENGTH_SHORT).show();
                        break;
                    case MotionEvent.ACTION_UP:
                        pat = "";
                        Toast.makeText(getApplicationContext(), "up", Toast.LENGTH_SHORT).show();
                        break;
                }

                //   Toast.makeText(getApplicationContext(),"touched",Toast.LENGTH_SHORT).show();
                return false;
            }

        });

        }
    public void print(View view)
    {
        Toast.makeText(getApplicationContext(),"clicked",Toast.LENGTH_SHORT).show();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pattern, menu);
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

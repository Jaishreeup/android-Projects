package com.example.notifier;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

public class SplashScreen extends Activity {
    TextView tv1, tv2, tv3, tv4, tv5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tv1 = (TextView) findViewById(R.id.textView1);
        tv2 = (TextView) findViewById(R.id.textView2);
        tv3 = (TextView) findViewById(R.id.textView3);
        tv4 = (TextView) findViewById(R.id.textView4);
        tv5 = (TextView) findViewById(R.id.textView5);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/MERCEDES.TTF");
        Typeface typeFace2 = Typeface.createFromAsset(getAssets(), "fonts/JANDACELEBRATIONSCRIPT.TTF");
        tv1.setTypeface(typeFace);
        tv2.setTypeface(typeFace2);
        tv3.setTypeface(typeFace2);
        tv4.setTypeface(typeFace2);
        tv5.setTypeface(typeFace2);
        tv2.setTextSize(56);
        tv3.setTextSize(56);
        tv4.setTextSize(56);
        tv5.setTextSize(56);
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                startActivity(intent);
            }
        }, 3000);
    }
}

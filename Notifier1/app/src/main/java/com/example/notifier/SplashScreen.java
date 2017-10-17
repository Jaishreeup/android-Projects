package com.example.notifier;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class SplashScreen extends Activity 
{
	TextView tv1,tv2,tv3,tv4,tv5;
	@Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
                                        WindowManager.LayoutParams.FLAG_FULLSCREEN);        
         
        setContentView(R.layout.activity_splash);
        tv1=(TextView)findViewById(R.id.textView1);
        tv2=(TextView)findViewById(R.id.textView2);
        tv3=(TextView)findViewById(R.id.textView3);
        tv4=(TextView)findViewById(R.id.textView4);
        tv5=(TextView)findViewById(R.id.textView5);
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/MERCEDES.TTF");
        Typeface typeFace2=Typeface.createFromAsset(getAssets(),"fonts/JANDACELEBRATIONSCRIPT.TTF");
        tv1.setTypeface(typeFace);
        tv2.setTypeface(typeFace2);
        tv3.setTypeface(typeFace2);
        tv4.setTypeface(typeFace2);
        tv5.setTypeface(typeFace2);
        tv2.setTextSize(46);
        tv3.setTextSize(46);
        tv4.setTextSize(46);
        tv5.setTextSize(46);        
        final Handler handler = new Handler();
        
        handler.postDelayed(new Runnable() 
        {
			@Override
			public void run() 
			{
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),MainScreen.class);
				startActivity(intent);
			}
        }, 1000);
    }
	  @Override
    public boolean onCreateOptionsMenu(Menu menu) 
    {
        getMenuInflater().inflate(R.menu.splash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) 
    {
        int id = item.getItemId();
        if (id == R.id.action_settings) 
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

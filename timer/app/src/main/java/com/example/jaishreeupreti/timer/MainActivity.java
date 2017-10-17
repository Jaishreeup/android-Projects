package com.example.jaishreeupreti.timer;

import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity
{
    TimePicker tp;
    SharedPreferences sp;
    int ho,mi;
    EditText edit;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        sp=getSharedPreferences("Timer",Context.MODE_PRIVATE );
    }
    public void sendvalue(View view)
    {
        Log.d("yahaaa","hkjk");
        tp = (TimePicker)findViewById(R.id.timePicker1);
        ho=tp.getCurrentHour();
        mi=tp.getCurrentMinute();
        sp.edit().putString("hour", ""+ho).commit();
        sp.edit().putString("minute",""+ mi).commit();
        edit=(EditText)findViewById(R.id.editText1);
        edit.setText(ho+":"+mi);
        Toast.makeText(this,ho+":"+mi, Toast.LENGTH_LONG).show();
        startService(new Intent(MainActivity.this,myservice.class));
    }
    public void stopService(View view)
    {
        stopService(new Intent(getBaseContext(), myservice.class));
    }

}

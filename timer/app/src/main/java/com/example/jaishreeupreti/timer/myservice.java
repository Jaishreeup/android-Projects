package com.example.jaishreeupreti.timer;

import java.net.CacheRequest;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

public class myservice extends Service
{
    Timer timer;
    String ho,mi;
    SharedPreferences sp;
    Calendar c;
    int hour,minute,sec,diff,finalhour,finalmin;
    public void onCreate(){
    }
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        timer= new Timer();
        sp = getSharedPreferences("Timer", Context.MODE_PRIVATE);
        ho=sp.getString("hour", "");
        mi=sp.getString("minute", "");
        c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        sec=c.get(Calendar.SECOND);
        finalhour=Integer.parseInt(ho);
        finalmin=Integer.parseInt(mi);
        if(finalhour<hour)
        {
            if(finalmin<minute)
                diff=(23-hour+finalhour)*60*60*1000+(finalmin-minute+60)*60*1000-sec*1000;
            else
                diff=(finalhour-hour+23)*60*60*1000+(finalmin-minute)*60*1000-sec*1000;

        }
        else
        {
            if(finalmin<minute)
                diff=(finalhour-hour)*60*60*1000+(finalmin-minute+60)*60*1000-sec*1000;
            else
                diff=(finalhour-hour)*60*60*1000+(finalmin-minute)*60*1000-sec*1000;

        }
        Log.d("timeis",""+diff+"   "+sec);
        timer.schedule(new TimerTask() {
            public void run() {
                Looper.prepare();
                Log.d("yooo", "kjhk");
                Intent i=new Intent(getApplicationContext(),reminder.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                Toast.makeText(getApplicationContext(), "Welcome", Toast.LENGTH_LONG).show();
            }
        }, diff);


        return START_STICKY;
    }
    public IBinder onBind(Intent intent)
    {
        return null;
    }
    @Override
    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
}

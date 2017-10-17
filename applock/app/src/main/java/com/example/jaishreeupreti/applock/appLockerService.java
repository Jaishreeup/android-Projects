package com.example.jaishreeupreti.applock;

import android.app.ActivityManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.os.Parcel;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;
import java.util.concurrent.BrokenBarrierException;

public class appLockerService extends Service {
    static SharedPreferences sp;
    BroadcastReceiver checkLock,stopAtScreenOff;
    Timer timer;
    static TimerTask t, temp;
    int fcount = 0;
    int count;

    public void onCreate() {

      Log.d("createeee","yup");
        sp = getSharedPreferences("applock", Context.MODE_PRIVATE);
        timer=new Timer();
        t=new CheckRunningActivity(getApplicationContext());
        timer.scheduleAtFixedRate(t,500, 500);
        checkLock=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               CheckRunningActivity.currentApp=new CheckRunningActivity(getApplicationContext()).getApp();


                    if(sp.getString(CheckRunningActivity.currentApp, "").equals("on"))
                {
                    Intent lockIntent = new Intent(context, LockScreen.class);
                    lockIntent.putExtra("receiver","yes");
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, lockIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                    try {
                        pendingIntent.send();
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                    context.stopService(new Intent(context, appLockerService.class));
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_ON);
      //  filter.addAction(Intent.ACTION_SCREEN_OFF);
        this.registerReceiver(checkLock, filter);
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {
//        Log.d("createeee","yup");
          return START_STICKY;
    }

    public void onDestroy() {
        Log.d("destroyedd","yup");
        unregisterReceiver(checkLock);
        timer.cancel();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

   @Override
    public void onTaskRemoved(Intent rootIntent) {
       super.onTaskRemoved(rootIntent);
       //Toast.makeText(getApplicationContext(),"removed",Toast.LENGTH_LONG).show();
   }
}
class CheckRunningActivity extends TimerTask {


    ActivityManager am = null;
    Context context = null;
    String lastApp,topApp;
    ComponentName topActivity;
    static String currentApp;

    public CheckRunningActivity(Context con) {

        context = con;
    }
    public String getApp()
    {
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            List<ActivityManager.RunningAppProcessInfo> tasks = am.getRunningAppProcesses();
            topApp=tasks.get(0).processName;
            //topActivity=tasks.get(0).importanceReasonComponent;
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            topActivity=taskInfo.get(0).topActivity;
        }
        else
        {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            topApp = taskInfo.get(0).topActivity.getPackageName();
            topActivity=taskInfo.get(0).topActivity;
        }
        return topApp;
    }
    public void run() {

        currentApp=getApp();
        lastApp = appLockerService.sp.getString("lastApp", "");
            if(topActivity.toString().equals("ComponentInfo{com.example.jaishreeupreti.applock/com.example.jaishreeupreti.applock.LockScreen}"))
        {
            currentApp=lastApp;
            context.stopService(new Intent(context, appLockerService.class));
        }
           Log.d("yahape", currentApp + " yo " + lastApp+" act"+topActivity);
        //if(topActivity.toString().equals("ComponentInfo{com.example.jaishreeupreti.applock/com.example.jaishreeupreti.applock.LockScreen}"))
          //  context.stopService(new Intent(context, appLockerService.class));

        if (!currentApp.equals(lastApp))
        {
                appLockerService.sp.edit().putString("lastApp", currentApp).commit();
                if (appLockerService.sp.getString(currentApp, "").equals("on")) {
                    Log.d("threaddd", currentApp);
                    Intent lockIntent = new Intent(context, LockScreen.class);
                    lockIntent.putExtra("receiver","no");
                    PendingIntent pendingIntent = PendingIntent.getActivity(context, 1, lockIntent, PendingIntent.FLAG_CANCEL_CURRENT);
                    try {
                        pendingIntent.send();
                    } catch (PendingIntent.CanceledException e) {
                        e.printStackTrace();
                    }
                    context.stopService(new Intent(context, appLockerService.class));

                }
        }
    }
}


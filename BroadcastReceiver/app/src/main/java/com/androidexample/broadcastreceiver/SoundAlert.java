package com.androidexample.broadcastreceiver;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by dell on 12-Nov-16.
 */

public class SoundAlert extends Service {


    /* constants */
    private static final int POLL_INTERVAL = 10000;

    /** running state **/
    private boolean mRunning = false;

    /** config state **/
    private double mThreshold;

    private PowerManager.WakeLock mWakeLock;

    private android.os.Handler mHandler = new android.os.Handler();

    SharedPreferences sp;
    /* sound data source */

    private Detect_noise mSensor = new Detect_noise();

    /****************** Define runnable thread again and again detect noise *********/

    private Runnable mSleepTask = new Runnable() {
        public void run() {
            Log.i("Noise", "runnable mSleepTask");

            start();
        }
    };

    // Create runnable thread to Monitor Voice
    private Runnable mPollTask = new Runnable() {
        public void run() {
            double amp = mSensor.getAmplitude();
            Log.i("Noise", "runnable mPollTask");
            updateDisplay("Monitoring Voice...", amp);
            //initializeApplicationConstants();

            mThreshold = 20;
            if ((amp > mThreshold)) {

                callForHelp(amp);
                Log.i("Noise", "==== onCreate ===");
            }
            // Runnable(mPollTask) will again execute after POLL_INTERVAL
            mHandler.postDelayed(mPollTask, POLL_INTERVAL);
        }
    };
    public int onStartCommand(Intent intent,int flags, int startId) {
        System.out.println("oooooooooooo: ");

        return START_STICKY;
    }
    /** Called when the activity is first created. */
    public  void onCreate() {
        sp =getSharedPreferences("project", MODE_PRIVATE);

        System.out.println("chhhhhhhhhhhhhhhhhhhhhh: "+sp);
        mSensor = new Detect_noise();
        PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "NoiseAlert");

        start();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //@Override
   /* public void onResume() {
        super.onResume();
        //Log.i("Noise", "==== onResume ===");

        initializeApplicationConstants();
        if (!mRunning) {
            mRunning = true;
            start();
        }
    }
    //@Override
    public void onStop() {
        super.onStop();
        // Log.i("Noise", "==== onStop ===");
        //Stop noise monitoring
        stop();
    }
    */
    private void start() {
        Log.i("Noise", "==== start ===");
        mSensor.start();

        if (!mWakeLock.isHeld()) {
            mWakeLock.acquire();
        }
        //Noise monitoring start
        // Runnable(mPollTask) will execute after POLL_INTERVAL
        mHandler.postDelayed(mPollTask, POLL_INTERVAL);
    }
    private void stop() {
        Log.i("Noise", "==== Stop Noise Monitoring===");
        if (mWakeLock.isHeld()) {
            mWakeLock.release();
        }
        mHandler.removeCallbacks(mSleepTask);
        mHandler.removeCallbacks(mPollTask);
        mSensor.stop();
        updateDisplay("stopped...", 0.0);
        mRunning = false;

    }


  /*  private void initializeApplicationConstants() {
        // Set Noise Threshold
        mThreshold = 20;

    }*/

    private void updateDisplay(String status, double signalEMA) {
        //mStatusView.setText(status);
        //
        //bar.setProgress((int)signalEMA);
        Log.d("SoundAlert", String.valueOf(signalEMA)+"dB");
        sp.edit().putString("sound", String.valueOf(signalEMA)).commit();
        Log.d("SoundAlert sp",sp.getString("sound","10"));
        //tv_noice.setText(signalEMA+"dB");
    }


    private void callForHelp(double signalEMA) {

        //stop();
        // Show alert when noise thersold crossed
        Toast.makeText(getApplicationContext(), "Noise Thersold Crossed, do here your stuff.",
                Toast.LENGTH_SHORT).show();
        Detect_noise_2 dn = new Detect_noise_2();
        long time = System.currentTimeMillis();
        Log.d("-----------time----------",""+time);
        sp.edit().putLong("prevTime", time ).commit();
        Log.d("-----prevTime------",""+sp.getLong("prevTime",0));
       /* dn.start();
        sleep(20000);
        dn.stop();
        */Log.d("HELP", String.valueOf(signalEMA)+"dB");
       // tv_noice.setText(signalEMA+"dB");
    }

}

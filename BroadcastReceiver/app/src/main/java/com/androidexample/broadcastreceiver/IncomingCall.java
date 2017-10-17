package com.androidexample.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.SENSOR_SERVICE;
import static java.lang.Thread.sleep;

/**
 * Created by dell on 03-Nov-16.
 */

public class IncomingCall extends BroadcastReceiver implements SensorEventListener {

    Context pcontext;
    SensorManager sensorManager;
    private Sensor mAmbientTemp;
    AudioRecordTest record;
    String phoneNumber;
    SharedPreferences sp;
    RejectCall rj = new RejectCall();
    private static final String TAG = "LightSensorManager";
    private static final String TAG1 = "IncomingCall";

    @Override
    public void onReceive(Context context, Intent intent) {
        pcontext = context;
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String state = extras.getString(TelephonyManager.EXTRA_STATE);
            Log.d("MY_DEBUG_TAG", state);
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING)) {
                phoneNumber = extras.getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
                Log.d("MY_DEBUG_TAG", phoneNumber);

                sensorManager = (SensorManager) pcontext.getSystemService(SENSOR_SERVICE);
                mAmbientTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

                if(mAmbientTemp != null){
                    sensorManager.registerListener(
                            this,
                            mAmbientTemp,
                            SensorManager.SENSOR_DELAY_NORMAL);

                }else{

                }

                sp= pcontext.getSharedPreferences("project", MODE_PRIVATE);

                // retrieving value of movement
                String str = sp.getString("movement", "UNKNOWN");
                Log.d("----------movement----------",str);

                // retrieving value of sound
                double decibel = Double.parseDouble(sp.getString("sound","10"));
                Log.d("----------decibel-----------",""+decibel);
                Toast.makeText(context.getApplicationContext(), " Sound : "+decibel, Toast.LENGTH_SHORT).show();

                // retrieving value of temperature
                float tmp = sp.getFloat("temperature",25);

                // retrieving value of light
                float light = sp.getFloat("light",50);
                Log.d("-----------light------------",""+light);

                long prev_time = sp.getLong("prevTime",0);
                long time= System.currentTimeMillis();

                String result ="";
                if((time-prev_time)<=120000) {
                    try {
                        result = record.doFileUpload();
                    } catch (Exception e) {

                    }
                }

                // getting time for the screen to turn on
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(str.equalsIgnoreCase("IN_VEHICLE")) {
                    rj.rejectCall(pcontext, phoneNumber);
                    Log.d("Diconnected","Driving");
                }
                else if(str.equalsIgnoreCase("WALKING") && decibel >20) {
                    rj.rejectCall(pcontext, phoneNumber);
                    Log.d("Diconnected","Walking on busy road");
                }
                /*else if((str.equalsIgnoreCase("STILL") || str.equalsIgnoreCase("TILT"))  && tmp < 26.6) {
                    rj.rejectCall(pcontext, phoneNumber);
                    Log.d("Diconnected","Cinema Hall");
                }*/
                else if(str.equalsIgnoreCase("STILL") && light < 100 && decibel < 20) {
                    rj.rejectCall(pcontext, phoneNumber);
                    Log.d("Diconnected", "Sleeping");
                }
                else if(result.equalsIgnoreCase("yes")) {
                    rj.rejectCall(pcontext, phoneNumber);
                }


            }
        }
    }



    @Override
    public void onSensorChanged(SensorEvent event) {

        Log.d("     inside     ", "    sensor changed     ");
        float tmp = event.values[0];
        sp.edit().putFloat("temperature", tmp).commit();
        Log.d("-----temperature-------",""+tmp);
        }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }


    protected void onPause() {
        //super.onPause();
        sensorManager.unregisterListener(this);
    }

}
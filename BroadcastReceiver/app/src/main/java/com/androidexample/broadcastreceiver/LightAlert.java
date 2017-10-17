package com.androidexample.broadcastreceiver;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by dell on 13-Nov-16.
 */

public class LightAlert extends Service implements SensorEventListener {

    private float luxLevel;
    SensorManager sensorManager;
    Sensor lightSensor;
    private static final int THRESHOLD_DAY_LUX = 130;
    private static final int THRESHOLD_NIGHT_LUX = 100;
    private static final String TAG = "LightAlert";

    SharedPreferences sp;

    public int onStartCommand(Intent intent,int flags, int startId) {
        System.out.println("LighAlert");

        return START_STICKY;
    }

    public void onCreate() {

        sp = getSharedPreferences("project", MODE_PRIVATE);
        sensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
        lightSensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

        if (lightSensor != null) {
            sensorManager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
            Log.d(TAG, "----------registered----------");

        } else {

            Log.w(TAG, "Light sensor is not supported");

        }

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        Log.d("     inside     ", "    sensor changed     ");
        luxLevel = event.values[0];
        sp.edit().putFloat("light", luxLevel).commit();
        Log.d("LightAlert lightacq",""+luxLevel);
        if (luxLevel < THRESHOLD_NIGHT_LUX) {
            Toast.makeText(getApplicationContext(), "NIGHT "+luxLevel, Toast.LENGTH_SHORT).show();

        } else if (luxLevel > THRESHOLD_DAY_LUX) {
            Toast.makeText(getApplicationContext(), " DAY  "+luxLevel, Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

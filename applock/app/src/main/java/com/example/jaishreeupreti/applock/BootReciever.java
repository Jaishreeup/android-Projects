package com.example.jaishreeupreti.applock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;
import android.widget.Toast;

import static android.support.v4.app.ActivityCompat.startActivity;

public class BootReciever extends BroadcastReceiver {
    public BootReciever() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"hhhh",Toast.LENGTH_LONG).show();
        context.startService(new Intent(context, appLockerService.class));
    }
}

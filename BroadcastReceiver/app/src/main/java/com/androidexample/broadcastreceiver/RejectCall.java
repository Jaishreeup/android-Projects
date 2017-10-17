package com.androidexample.broadcastreceiver;

import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * Created by dell on 07-Nov-16.
 */

public class RejectCall {

    public RejectCall(){

    }

    Context pcontext;
    public void rejectCall(Context context, String phoneNumber){

        pcontext = context;
        try {

            // Get the boring old TelephonyManager
            TelephonyManager telephonyManager =
                    (TelephonyManager) pcontext.getSystemService(Context.TELEPHONY_SERVICE);

            // Get the getITelephony() method
            Class classTelephony = Class.forName(telephonyManager.getClass().getName());
            Method methodGetITelephony = classTelephony.getDeclaredMethod("getITelephony");

            // Ignore that the method is supposed to be private
            methodGetITelephony.setAccessible(true);

            // Invoke getITelephony() to get the ITelephony interface
            Object telephonyInterface = methodGetITelephony.invoke(telephonyManager);

            // Get the endCall method from ITelephony
            Class telephonyInterfaceClass =
                    Class.forName(telephonyInterface.getClass().getName());
            Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");

            // Invoke endCall()
            methodEndCall.invoke(telephonyInterface);
            Log.d("----------Disconnected----------",""+phoneNumber);

            // send a notification for missed call

            NotificationCompat.Builder missedCall = new NotificationCompat.Builder(pcontext);
            missedCall.setContentText(phoneNumber);
            missedCall.setSmallIcon(R.drawable.ic_call_missed_white_48dp);
            missedCall.setContentTitle("You missed a call");
            NotificationManagerCompat.from(pcontext).notify(1, missedCall.build());

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.d("       reject call     ","            exception          ");
        }

    }

}

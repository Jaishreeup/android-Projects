package com.example.notifier;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

public class NotificationService extends Service {

    NotificationCompat.Builder mNotifyBuilder;

    public NotificationService() {
    }

    public void onCreate() {
        if (isConnected())
        {
            //new LoadTask().execute("https://10.0.2.2:8080/MyServerProject/fetchNotification", "https://10.0.2.2:8080/MyServerProject/notify");
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
/*		Notification notification = new Notification(R.drawable.abc_btn_default_mtrl_shape, "New Message", System.currentTimeMillis());
        //Toast.makeText(getBaseContext(),simserial, Toast.LENGTH_LONG).show();
		Intent notificationIntent = new Intent(this, MainActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,notificationIntent, 0);
		notification.setLatestEventInfo(getApplicationContext(), "Theftalert", "dsfdsfdg", pendingIntent);
		notificationManager.notify(9999, notification);
        this.setFinishOnTouchOutside(true);
        notificationManager.cancel(9999);
*/
        Intent notificationIntent = new Intent(this, NotificationMain.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        int notifyID = 1;
        mNotifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("New Message")
                .setContentText("You've received new messages.")
                .setSmallIcon(R.drawable.logo);
        mNotifyBuilder.setContentIntent(pendingIntent);
        mNotifyBuilder.setAutoCancel(true);
        //mNotifyBuilder.build().flags=Notification.FLAG_AUTO_CANCEL;
        int numMessages = 0;
        // for(int i=0;i<5;i++)
        // mNotifyBuilder.setContentText("Hello")
        //       .setNumber(++numMessages);
        // Because the ID remains unchanged, the existing notification is
        // updated.
        //mNotifyBuilder.getNotification().flags |= Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(
                notifyID,
                mNotifyBuilder.build());

    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
}
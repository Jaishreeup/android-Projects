package com.example.jaishreeupreti.notify;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class notifycheck extends ActionBarActivity {

    NotificationCompat.Builder mNotifyBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
        int notifyID = 1;
        mNotifyBuilder = new NotificationCompat.Builder(this)
                .setContentTitle("New Message")
                .setContentText("You've received new messages.")
                .setSmallIcon(R.drawable.abc_btn_radio_material);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

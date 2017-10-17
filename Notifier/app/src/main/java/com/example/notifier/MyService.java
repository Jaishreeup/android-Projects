package com.example.notifier;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Iterator;

public class MyService extends Service {
    String host = "abesec.in";
    int num;

    public MyService() {
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        num = 1;
        new LoadTask().execute("http://" + host + "/notifier/fetchnotifications.php", "http://" + host + "/notifier/notify.php");
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    class LoadTask extends AsyncTask<String, Void, Void> {
        private final HttpClient Client = new DefaultHttpClient();
        BigInteger newId, oldId;
        preferenceDB db;
        NotificationDB db1;
        SharedPreferences sp;
        Context ctx;
        int success = 0;
        private String Content, Content1;

        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        protected Void doInBackground(String... urls) {
            try {
                db = new preferenceDB(getApplicationContext());
                db1 = new NotificationDB(getApplicationContext());
                sp = getSharedPreferences("notify", Context.MODE_PRIVATE);
                ctx = getApplicationContext();
                try {
                    HttpGet httpget = new HttpGet(urls[0]);
                    HttpGet httpget1 = new HttpGet(urls[1]);
                    ResponseHandler<String> responseHandler = new BasicResponseHandler();
                    Content = Client.execute(httpget, responseHandler);

                    ResponseHandler<String> responseHandler1 = new BasicResponseHandler();
                    Content1 = Client.execute(httpget1, responseHandler1);

                    JSONObject j = new JSONObject(Content);
                    JSONObject json = j.getJSONObject("results");

                    if (sp.getBoolean("isDatabaseLoaded", false)) {
                        ctx.deleteDatabase(db.getDatabaseName());
                        ctx.deleteDatabase(db1.getDatabaseName());
                    }

                    @SuppressWarnings("unchecked")
                    Iterator<String> keys = json.keys();
                    while (keys.hasNext()) {
                        String key = keys.next(), temp = "";
                        JSONArray array = json.getJSONArray(key);
                        for (int i = 0; i < array.length(); i++) {
                            System.out.println("elements " + array.get(i));
                            temp += array.get(i) + "&&&";
                        }
                        temp = temp.substring(0, temp.length() - 3);
                        db.addRecord(key, temp);
                    }
                    j = new JSONObject(Content1);
                    JSONArray json1 = j.getJSONArray("results");
                    int len = json1.length();
                    System.out.println(json1.getJSONArray(0).get(5));
                    newId = new BigInteger((String) json1.getJSONArray(0).get(4));
                    for (int i = 0; i < len - 1; i++) {
                        JSONArray jarr = json1.getJSONArray(i);
                        db1.addRecord("" + jarr.get(0), "" + jarr.get(1), "" + jarr.get(2), "" + jarr.get(3), "" + jarr.get(4), "" + jarr.get(5), "" + jarr.get(6),0);
                        if (!jarr.get(6).equals("empty")) {
                            new downloadPDF().downloadAndOpenPDF((String) jarr.get(6));
                        }
                    }
                    oldId = new BigInteger(sp.getString("oldId", "0"));
                    if (newId.compareTo(oldId) > 0)//&& oldId != 0)
                        sendNotification();
                    sp.edit().putString("oldId", "" + newId).commit();
                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } catch (Exception e) {
                if (num != 2) {
                    num++;
                    new LoadTask().execute("http://" + host + "/notifier/fetchnotifications.php", "http://" + host + "/notifier/notify.php");
                }
            }
            success = 1;
            return null;
        }


        public void sendNotification() {
            int count = db1.newNotificationCount("" + oldId);
            if (count != 0) {
                NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                NotificationCompat.Builder mNotifyBuilder;

                Intent notificationIntent = new Intent(getApplicationContext(), NotificationMain.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, notificationIntent, 0);
                int notifyID = 1;
                mNotifyBuilder = new NotificationCompat.Builder(getApplicationContext())
                        .setContentTitle("Notifier")
                        .setContentText("You have " + count + " new notifications.")
                        .setSmallIcon(R.drawable.logo);
                mNotifyBuilder.setContentIntent(pendingIntent);
                mNotifyBuilder.setAutoCancel(true);
                notificationManager.notify(
                        notifyID,
                        mNotifyBuilder.build());
            }
        }

    }
}


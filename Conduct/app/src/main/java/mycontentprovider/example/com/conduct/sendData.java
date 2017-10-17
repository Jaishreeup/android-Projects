package mycontentprovider.example.com.conduct;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import static mycontentprovider.example.com.conduct.functions.isConnected;

public class sendData extends Service {
    static SharedPreferences sp;
    static TimerTask t, temp;
    Timer timer;

    public sendData() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate() {
        sp = getSharedPreferences("conduct", Context.MODE_PRIVATE);
        timer = new Timer();
        System.out.println("wwwwwwwwwwwwwwwwwww");
        t = new CheckRunningActivity(getApplicationContext());
        timer.scheduleAtFixedRate(t, 10 * 1000, 10 * 1000);
    }


    class CheckRunningActivity extends TimerTask {


        ActivityManager am = null;
        Context context = null;

        public CheckRunningActivity(Context con) {

             context = con;
        }

        public void run() {
            if (isConnected(getApplicationContext())) {
                int val = sp.getInt("available", 60);
                String bid = sp.getString("bid", "default");
                System.out.println("ccccccccchhh" +val);

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://wittybus.000webhostapp.com/getData.php");
                JSONObject json = new JSONObject();
                try {
                    // Add your data
                    json.put("available", val);
                    json.put("bid", bid);
                    json.put("fr", sp.getString("fr", "U"));
                    StringEntity se = new StringEntity(json.toString());
                    httppost.setEntity(se);
                    // Execute HTTP Post Request

                    // 7. Set some headers to inform server about the type of the content
                    httppost.setHeader("Accept", "application/json");
                    httppost.setHeader("Content-type", "application/json");

                    // 8. Execute POST request to the given URL
                    HttpResponse httpResponse = httpclient.execute(httppost);

                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}


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
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class sendData extends Service {
    static SharedPreferences sp;
    Timer timer;
    static TimerTask t, temp;

    public sendData() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onCreate() {
System.out.println("insiiiiiiiiii");
        sp = getSharedPreferences("conduct", Context.MODE_PRIVATE);
        timer = new Timer();
        t = new CheckRunningActivity(getApplicationContext());
        System.out.println("ooooooooooooooooo");
        timer.scheduleAtFixedRate(t,10 * 1000, 10 * 1000);

    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


    class CheckRunningActivity extends TimerTask {


        ActivityManager am = null;
        Context context = null;

        public CheckRunningActivity(Context con) {

            context = con;
        }

        public void run() {
            if (isConnected()) {
                int val = sp.getInt("available", 60);
                String bid = sp.getString("bid", "default");

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://abesec.in/witty/getData.php");
                JSONObject json = new JSONObject();
                System.out.println("hereeeeeeee");
                try {
                    // Add your data
                    json.put("available", val);
                    json.put("bid", bid);
                    StringEntity se = new StringEntity(json.toString());
                    httppost.setEntity(se);
                    // Execute HTTP Post Request

                    // 7. Set some headers to inform server about the type of the content
                    httppost.setHeader("Accept", "application/json");
                    httppost.setHeader("Content-type", "application/json");
                    System.out.println("yooooo");

                    // 8. Execute POST request to the given URL
                    HttpResponse httpResponse = httpclient.execute(httppost);
                    System.out.println("llllleeeee");

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


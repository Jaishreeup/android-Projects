package mycontentprovider.example.com.conduct;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MyService extends Service {
    String bid;
    SharedPreferences sp;
    private LocationManager locationManager;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sp = getSharedPreferences("conduct", Context.MODE_PRIVATE);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 5, new gps_1());
    }

    class gps_1 implements LocationListener {
        public boolean isConnected() {
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected())
                return true;
            else
                return false;
        }

        @Override
        public void onLocationChanged(Location location) {
            final double lat = location.getLatitude(), longi = location.getLongitude();
            String msg = "New Latitude: " + lat
                    + "New Longitude: " + longi;

            Toast.makeText(getBaseContext(), msg, Toast.LENGTH_LONG).show();
            if (isConnected()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        HttpClient httpclient = new DefaultHttpClient();
                        HttpPost httppost = new HttpPost("http://wittybus.000webhostapp.com/gpsstore.php");

                      //  HttpPost httppost = new HttpPost("http://10.5.18.103/verify_speaker");
                        JSONObject json = new JSONObject();
                        bid = sp.getString("bid", "null");
                        System.out.println("hereeeeeeee");
                        try {
                            // Add your data
                            json.put("id", bid);
                            json.put("lati", lat);
                            json.put("longi", longi);
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

                }).start();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

        @Override
        public void onProviderDisabled(String provider) {

            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            Toast.makeText(getBaseContext(), "Gps is turned off!! ",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderEnabled(String provider) {

            Toast.makeText(getBaseContext(), "Gps is turned on!! ",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub

        }

    }

}

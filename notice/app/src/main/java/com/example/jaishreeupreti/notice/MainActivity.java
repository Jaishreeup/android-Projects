package com.example.jaishreeupreti.notice;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    Button b;
    String host = "abesec.in";// "172.16.7.114";//"172.16.3.25";//"192.168.0.102";//"172.16.5.32";//"172.26.1.254";
    preferenceDB db;
    NotificationDB db1;
    SharedPreferences sp;
    Context ctx;
    BigInteger newId, oldId;
    public void sync(View view) {
        if (isConnected()) {
            new LoadTask().execute("http://" + host + "/notifier/fetchnotifications.php", "http://" + host + "/notifier/notify2.php");
        } else {
            Toast.makeText(getApplicationContext(), "Network connection not available", Toast.LENGTH_LONG).show();
            //   finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = new preferenceDB(this);
        db1 = new NotificationDB(this);
        sp = getSharedPreferences("notify", Context.MODE_PRIVATE);
        ctx = getApplicationContext();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sync(view);

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    class LoadTask extends AsyncTask<String, Void, Void> {
        private final HttpClient Client = new DefaultHttpClient();
        int success = 0;
        private String Content, Content1;
        private ProgressDialog Dialog = new ProgressDialog(MainActivity.this);

        protected void onPreExecute() {
            Dialog.setCancelable(false);
            Dialog.setTitle("Loading...");
            Dialog.setMessage("Please wait...");
            Dialog.show();
        }

        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        protected Void doInBackground(String... urls) {
            String PATH = Environment.getExternalStorageDirectory() + "/notifier/";
            File file = new File(PATH);
                /*if (file.isDirectory()) {
                    String[] children = file.list();
                    for (int i = 0; i < children.length; i++) {
                        new File(file, children[i]).delete();
                    }
                }*/

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(urls[1]);
            ArrayList<NameValuePair> namevaluepairs=new ArrayList<NameValuePair>();
            JSONObject json = new JSONObject();
            //  System.out.println("hereeeeeeee");
            oldId = new BigInteger(sp.getString("oldId", "0"));
            System.out.println("thisssssssss issss  "+oldId);
            namevaluepairs.add(new BasicNameValuePair("id",""+oldId));
            try{
                // Add your data
                //    json.put("id", oldId);
                //   StringEntity se = new StringEntity(json.toString());
                httppost.setEntity(new UrlEncodedFormEntity(namevaluepairs));
                // Execute HTTP Post Request

                // 7. Set some headers to inform server about the type of the content
                //       httppost.setHeader("Accept", "application/json");
                //     httppost.setHeader("Content-type", "application/json");
                // 8. Execute POST request to the given URL
                HttpResponse httpResponse = httpclient.execute(httppost);
                System.out.println("llllleeeee");
                InputStream inputStream = httpResponse.getEntity().getContent();

                // convert inputstream to string
                if (inputStream != null)
                    Content1 = convertInputStreamToString(inputStream);
                else
                    Content1 = "Did not work!";

                HttpGet httpget = new HttpGet(urls[0]);
                //            HttpGet httpget1 = new HttpGet(urls[1]);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                Content = Client.execute(httpget, responseHandler);
                System.out.println("ccoonntent  "+Content1);
                //    ResponseHandler<String> responseHandler1 = new BasicResponseHandler();
                //   Content1 = Client.execute(httpget1, responseHandler1);

                JSONObject j = new JSONObject(Content);
                json = j.getJSONObject("results");
                if (sp.getBoolean("isDatabaseLoaded", false)) {
                    ctx.deleteDatabase(db.getDatabaseName());
                    //ctx.deleteDatabase(db1.getDatabaseName());
                }
                @SuppressWarnings("unchecked")
                Iterator<String> keys = json.keys();
                while (keys.hasNext()) {
                    String key = keys.next(), temp = "";
                    JSONArray array = json.getJSONArray(key);
                    for (int i = 0; i < array.length(); i++) {
                        temp += array.get(i) + "&&&";
                    }
                    temp = temp.substring(0, temp.length() - 3);
                    db.addRecord(key, temp);
                }
                j = new JSONObject(Content1);
                JSONArray json1 = j.getJSONArray("results");
                int len = json1.length();
                newId = new BigInteger((String) json1.getJSONArray(0).get(4));
                for (int i = 0; i < len - 1; i++) {

                    JSONArray jarr = json1.getJSONArray(i);

                    db1.addRecord("" + jarr.get(0), "" + jarr.get(1), "" + jarr.get(2), "" + jarr.get(3), "" + jarr.get(4), "" + jarr.get(5), "" + jarr.get(6),0);
                    String temp = (String) jarr.get(6);
                    if (!(temp.equals("empty"))) {
                        System.out.println("going to get pdf::" + temp);
                        new downloadPDF().downloadAndOpenPDF(temp);
                    }
                }
                if (newId.compareTo(oldId) > 0 && !(oldId.compareTo(BigInteger.valueOf(0))==0))
                    sendNotification();
                System.out.println("newwwwww  " + newId);
                if(!(newId.compareTo(BigInteger.valueOf(-1))==0))
                    sp.edit().putString("oldId", "" + newId).commit();
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            success = 1;
            return null;

        }



        protected void onPostExecute(Void unused) {
            Dialog.dismiss();
            if (success == 1) {
                sp.edit().putBoolean("isDatabaseLoaded", true).commit();
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Confirmation")
                        .setMessage("The notices have been synchronized.\n")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                //finish();
                            }
                        })
                        .show();
            }
        }

    }
    private static String convertInputStreamToString(InputStream inputStream) throws IOException{
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        String title = getString(R.string.app_name);

        if (id == R.id.nav_camera) {
            // Handle the camera action
            fragment = new AbtApp();
            title="about App";
        } else if (id == R.id.nav_gallery) {
            fragment = new AbtDev();
            title="about Developers";

        } else if (id == R.id.nav_slideshow) {
            fragment = new SetPreferences();
            title="set your preference";

        } else if (id == R.id.nav_manage) {
            fragment = new NotificationMain();
            title="Notices";

        } else if (id == R.id.nav_share) {
            fragment = new showSaved();
            title="Notices";
        } else if (id == R.id.nav_send) {

        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            if (getSupportActionBar() != null)
                getSupportActionBar().setTitle(title);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void sendNotification() {
        int count = db1.newNotificationCount("" + oldId);
    }

}

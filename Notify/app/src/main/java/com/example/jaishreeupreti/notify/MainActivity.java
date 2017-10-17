package com.example.jaishreeupreti.notify;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;
import java.util.Iterator;

public class MainActivity extends ActionBarActivity {
    preferenceDB db;
    NotificationDB db1;
    SharedPreferences sp;
    Context ctx;
    TextView tv;
    Handler handler = new Handler();
    ExpandableListView expandableList;
    private ProgressBar progressBar = null;
    private Button myButton;
    private int progressStatus = 0;
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();
    private int i = 0;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.main_screen);
        ctx = getApplicationContext();
      //  expandableList = (ExpandableListView) findViewById(R.id.list);
        db = new preferenceDB(this);
        db1 = new NotificationDB(this);
        sp = getSharedPreferences("notify", Context.MODE_PRIVATE);
        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);
        db.deleteRecord("departments");
        db.deleteRecord("clubs");
        db.addRecord("Departments", "cse me it");
        db.addRecord("Clubs","env techcs huhc");
        db1.deleteRecord("1");
        db1.addRecord("tcs","codevita","08-08-2015","this is to inform everyone that tcs is organising contest","abesec","1","cse");
        sp.edit().putBoolean("isDatabaseLoaded",true).commit();
        if (sp.getBoolean("isDatabaseLoaded", false)) {
            setGroupParents();
            setChildData();

            MyExpandableAdapter adapter = new MyExpandableAdapter(parentItems, childItems, getApplicationContext());

            adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), MainActivity.this);
            expandableList.setAdapter(adapter);
            expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    return false;
                }
            });


        } else {
            //   if (isConnected())
            {
                Log.d("yahape", "hello");
                new LoadTask().execute("https://10.0.2.2:8080/MyServerProject/fetchNotification", "https://10.0.2.2:8080/MyServerProject/notify");
            }

            //else {

///				Toast.makeText(getApplicationContext(), "Network connection not available", Toast.LENGTH_LONG).show();

            //		finish();


            //		}
        }

    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {

            case R.id.sync:

                if (isConnected()) {
                    new LoadTask().execute("https://10.0.2.2:8080/MyServerProject/fetchNotification", "https://10.0.2.2:8080/MyServerProject/notify");
                } else {
                    Toast.makeText(getApplicationContext(), "Network connection not available", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.save:
                new AlertDialog.Builder(this)
                        .setTitle("Confirmation")
                        .setMessage("Your preferences have been saved\n")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete
                                Intent intent = new Intent(getApplicationContext(), FirstActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        })
                        .show();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    public void setGroupParents() {
        ArrayList<String[]> Choices = db.getArrayList();
        String[] str;
        for (int i = 0; i < Choices.size(); i++) {
            str = Choices.get(i);
            parentItems.add(str[0]);
        }
    }

    public void setChildData() {

        // Android
        ArrayList<String> child;
        ArrayList<String[]> Choices = db.getArrayList();
        String var = "";
        String[] str;
        for (int i = 0; i < Choices.size(); i++) {
            var = "";
            child = new ArrayList<String>();
            Log.d("kabbb", "yup" + Choices.size());
            str = Choices.get(i);
            String[] list = str[1].split(" ");
            for (int j = 0; j < list.length; j++)
                child.add(list[j]);
            childItems.add(child);
            var = "";

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

    private class LoadTask extends AsyncTask<String, Void, Void> {

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
            try {

                System.out.println("heeeeeeeeeeee");
                HttpGet httpget = new HttpGet(urls[0]);
                HttpGet httpget1 = new HttpGet(urls[1]);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                Content = Client.execute(httpget, responseHandler);

                ResponseHandler<String> responseHandler1 = new BasicResponseHandler();
                Content1 = Client.execute(httpget1, responseHandler1);
                System.out.println("JSON fetched---------------- " + Content);
                System.out.println("JSON fetched---------------- " + Content1);

                JSONObject j = new JSONObject(Content);
                JSONObject json = j.getJSONObject("results");
                System.out.println("JSON object    " + json + " hi " + json.length());

                if (sp.getBoolean("isDatabaseLoaded", false))
                    ctx.deleteDatabase(db.getDatabaseName());
                System.out.println("yahaaannnnnnnnnnn    ");


                Iterator<String> keys = json.keys();
                while (keys.hasNext()) {

                    String key = keys.next(), temp = "";
                    JSONArray array = json.getJSONArray(key);
                    System.out.println("hello   " + key + "  " + array);
                    for (int i = 0; i < array.length(); i++) {
                        System.out.println("elements " + array.get(i));
                        temp += array.get(i) + " ";
                    }
                    db.addRecord(key, temp);
                }
                j = new JSONObject(Content1);
                System.out.println("yuuuu");
                JSONArray json1 = j.getJSONArray("results");
                int len = json1.length();
                for (int i = 0; i < len; i++) {
                    JSONArray jarr = json1.getJSONArray(i);
                    db1.addRecord("" + jarr.get(0), "" + jarr.get(1), "" + jarr.get(2), "" + jarr.get(3), "" + jarr.get(4), "" + jarr.get(5), "" + jarr.get(6));
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
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
                Log.d("jayaa", "" + sp.getBoolean("isDatabaseLoaded", false));

                sp.edit().putBoolean("isDatabaseLoaded", true).commit();
                setGroupParents();
                setChildData();

                MyExpandableAdapter adapter = new MyExpandableAdapter(parentItems, childItems, getApplicationContext());

                adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), MainActivity.this);
                expandableList.setAdapter(adapter);
                expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                    @Override
                    public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                        return false;
                    }
                });

            }
        }
    }

}

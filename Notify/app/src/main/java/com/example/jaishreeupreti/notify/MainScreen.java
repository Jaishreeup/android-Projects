package com.example.jaishreeupreti.notify;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ExpandableListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
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

public class MainScreen extends ExpandableListActivity {

    Context ctx;
    SharedPreferences sp;
    preferenceDB db;
    ExpandableListView expandableList;
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // setContentView(R.layout.main_screen);
        ctx = this;
        ///expandableList = (ExpandableListView) findViewById(R.id.list);
        expandableList.setDividerHeight(2);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);
        db = new preferenceDB(ctx);
        db.addRecord("departments", "cse ece it me");
        db.addRecord("clubs", "env bus abc");
        sp = getSharedPreferences("notify", Context.MODE_PRIVATE);
        /*if(sp.getString("done","no").equals("no")) {
			sp.edit().putBoolean("isDatabaseLoaded", false).commit();
			sp.edit().putString("done", "y");
		}*/
        sp.edit().putBoolean("isDatabaseLoaded", true).commit();


        if (sp.getBoolean("isDatabaseLoaded", false)) {
            Log.d("woooo", "yup");

            setGroupParents();
            setChildData();
            Log.d("imhereaaa", "yup");

            MyExpandableAdapter adapter = new MyExpandableAdapter(parentItems, childItems, getApplicationContext());

            //adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),MainScreen.this);
            //expandableList.setAdapter(adapter);
            //expandableList.setOnChildClickListener(this);

            Log.d("yahoooo", "hello");
            Log.d("spppp", "" + sp.getBoolean("isDatabaseLoaded", false));

        } else {
            //if (isConnected())
            {
                Log.d("yahape", "hello");
                new LoadTask().execute("https://10.0.2.2:8080/MyServerProject/fetchNotification");
            }
/*
			else {

				Toast.makeText(ctx, "Network connection not available", Toast.LENGTH_LONG).show();

		//		finish();


			}*/
        }
    }

    public void setGroupParents() {
        Log.d("imhere", "yup");

        String[] Choices = db.getPreferences(1);
        for (int i = 0; i < Choices.length; i++)
            parentItems.add(Choices[i]);
    }

    public void setChildData() {

        String[] Choices = db.getPreferences(2);
        String var = "";
        for (int i = 0; i < Choices.length; i++) {
            var = "";
            for (int j = 0; j < Choices[i].length(); i++) {
                if (!(Choices[i].charAt(j) == ' '))
                    var += Choices[i].charAt(j);
                else {
                    ArrayList<String> child = new ArrayList<String>();
                    child.add(var);
                    childItems.add(child);
                    var = "";
                }
            }
        }
    }
/*
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.sync:

                if (isConnected()) {
                    new LoadTask().execute("https://10.0.2.2:8080/MyServerProject/fetchNotification");
                } else {
                    Toast.makeText(ctx, "Network connection not available", Toast.LENGTH_LONG).show();
                }


                break;
        }
        return super.onOptionsItemSelected(item);
    }
*/
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
        private String Content;
        private ProgressDialog Dialog = new ProgressDialog(MainScreen.this);

        protected void onPreExecute() {


            Dialog.setCancelable(false);
            Dialog.setTitle("Loading...");
            Dialog.setMessage("Please wait...");
            Dialog.show();
        }


        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        protected Void doInBackground(String... urls) {
            try {


                HttpGet httpget = new HttpGet(urls[0]);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                Content = Client.execute(httpget, responseHandler);

                System.out.println("JSON fetched---------------- " + Content);

                try {


                    JSONObject j = new JSONObject(Content);
                    JSONArray json = j.getJSONArray("results");

                    if (sp.getBoolean("isDatabaseLoaded", false))
                        ctx.deleteDatabase(db.getDatabaseName());

                    for (int i = 0; i < json.length(); i++) {
                        JSONObject jo = json.getJSONObject(i);
                        db.addRecord(jo.getString("list"), jo.getString("subcategory"));
                        //db.addRecord(jo.getString("organiser"), jo.getString("eventname"), jo.getString("date"), jo.getString("info"), jo.getString("issuer"), jo.getString("id"));
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                success = 1;

            } catch (ClientProtocolException e) {
                e.printStackTrace();
                cancel(true);
            } catch (IOException e) {
                e.printStackTrace();
                cancel(true);
            }

            return null;
        }

        protected void onPostExecute(Void unused) {

            Dialog.dismiss();

            if (success == 1) {
                Log.d("jayaa", "" + sp.getBoolean("isDatabaseLoaded", false));

                sp.edit().putBoolean("isDatabaseLoaded", true).commit();
				/*ArrayList<String[]> Choices =db.getArrayList();

				ArrayAdapter<String> adapter;
				adapter = new ArrayAdapter<String>(getApplicationContext(),
						android.R.layout.simple_list_item_1, android.R.id.text1, Choices);
				lv.setAdapter(adapter);*/
                setGroupParents();
                setChildData();

                MyExpandableAdapter adapter = new MyExpandableAdapter(parentItems, childItems, getApplicationContext());

                //adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE),this);
                //expandableList.setAdapter(adapter);
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




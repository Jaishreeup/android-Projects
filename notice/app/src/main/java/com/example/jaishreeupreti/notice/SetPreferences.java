package com.example.jaishreeupreti.notice;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

public class SetPreferences extends android.support.v4.app.Fragment {
    private static final String ARG_PARAM2 = "param2";
    private int mParam1;
    private int mParam2;
    preferenceDB db;
    NotificationDB db1;
    SharedPreferences sp;
    String host;
    Context ctx;
    TextView tv;
    Handler handler = new Handler();
    ExpandableListView expandableList;
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();

    public SetPreferences() {
        // Required empty public constructor
    }

    public static android.support.v4.app.Fragment newInstance() {
        SetPreferences fragment = new SetPreferences();
        System.out.println("aaaaaaaaaaaaaaaa");
        Bundle args = new Bundle();
//        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //  mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_set_preferences, container, false);
        ctx = getContext();
        expandableList = (ExpandableListView) v.findViewById(R.id.list);
        db = new preferenceDB(getActivity());
        db1 = new NotificationDB(getActivity());
        sp = getActivity().getSharedPreferences("notify", Context.MODE_PRIVATE);
        expandableList.setClickable(true);
        host = "abesec.in";
//        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MERCEDES.TTF");
        tv = (TextView) v.findViewById(R.id.tex);
//        tv.setTypeface(typeFace);
        tv.setTextSize(30);
        if (sp.getBoolean("isDatabaseLoaded", false)) {
            setGroupParents();
            setChildData();
            MyExpandableAdapter adapter = new MyExpandableAdapter(parentItems, childItems, getContext());
            adapter.setInflater((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE), SetPreferences.this);
            expandableList.setAdapter(adapter);
            expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    return false;
                }
            });
        } else {
            if (isConnected()) {
                new LoadTask().execute("http://" + host + "/notifier/fetchnotifications.php", "http://" + host + "/notifier/notify.php");
            } else {
                Toast.makeText(getContext(), "Network connection not available", Toast.LENGTH_LONG).show();
//                finish();
            }
        }

        return v;
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

    class LoadTask extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        int success = 0;
        private String Content;
        private String Content1;
        private ProgressDialog Dialog = new ProgressDialog(getActivity());

        protected void onPreExecute() {
            Dialog.setCancelable(false);
            Dialog.setTitle("Loading...");
            Dialog.setMessage("Please wait...");
            Dialog.show();
        }

        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        protected Void doInBackground(String... urls) {
            try {
                String PATH = Environment.getExternalStorageDirectory() + "/notifier/";
                File file = new File(PATH);
                if (file.isDirectory()) {
                    String[] children = file.list();
                    for (int i = 0; i < children.length; i++) {
                        new File(file, children[i]).delete();
                    }
                }

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
                        temp += array.get(i) + "&&&";
                    }
                    temp = temp.substring(0, temp.length() - 3);
                    db.addRecord(key, temp);
                }

                j = new JSONObject(Content1);
                JSONArray json1 = j.getJSONArray("results");
                int len = json1.length();
                sp.edit().putString("oldId", (String) json1.getJSONArray(0).get(4)).commit();
                for (int i = 0; i < len - 1; i++) {
                    JSONArray jarr = json1.getJSONArray(i);
                    db1.addRecord("" + jarr.get(0), "" + jarr.get(1), "" + jarr.get(2), "" + jarr.get(3), "" + jarr.get(4), "" + jarr.get(5), "" + jarr.get(6),0);
                    if (!jarr.get(6).equals("empty")) {
                        new downloadPDF().downloadAndOpenPDF((String) jarr.get(6));
                    }
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
                sp.edit().putBoolean("isDatabaseLoaded", true).commit();
                setGroupParents();
                setChildData();
                MyExpandableAdapter adapter = new MyExpandableAdapter(parentItems, childItems, getContext());
                adapter.setInflater((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE), SetPreferences.this);
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
    public void setGroupParents() {
        ArrayList<String[]> Choices = db.getArrayList();
        String[] str;
        for (int i = 0; i < Choices.size(); i++) {
            str = Choices.get(i);
            parentItems.add(str[0]);
        }
    }

    public void setChildData() {

        ArrayList<String> child;
        ArrayList<String[]> Choices = db.getArrayList();
        String[] str;
        for (int i = 0; i < Choices.size(); i++) {
            child = new ArrayList<String>();
            str = Choices.get(i);
            String[] list = str[1].split("&&&");
            for (int j = 0; j < list.length; j++)
                child.add(list[j]);
            childItems.add(child);
        }
    }

}

package com.example.jaishreeupreti.notify;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class FirstActivity extends ActionBarActivity
{
    preferenceDB db;
    SharedPreferences sp;
    NotificationDB db1;
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
        setContentView(R.layout.activity_first);
        expandableList = (ExpandableListView) findViewById(R.id.list1);
        db = new preferenceDB(this);
        expandableList.setDividerHeight(5);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);
        setGroupParents();
        setChildData();
        MyExpandableAdapter1 adapter = new MyExpandableAdapter1(parentItems, childItems, getApplicationContext());

        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), FirstActivity.this);
        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });


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

    public void save(View view) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("Your preferences have been saved\n")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .show();
    }
}

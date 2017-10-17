package com.example.notifier;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NotificationMain extends Activity {
    preferenceDB db;
    SharedPreferences sp;
    NotificationDB db1;
    TextView tv;
    Handler handler = new Handler();
    ExpandableListView expandableList;
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_main);
        expandableList = (ExpandableListView) findViewById(R.id.list1);
        db = new preferenceDB(this);
        tv = (TextView) findViewById(R.id.te);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/MERCEDES.TTF");
        tv.setTypeface(typeFace);
        tv.setTextSize(30);
        expandableList.setDividerHeight(2);
        expandableList.setClickable(true);
        expandableList.setIndicatorBounds(25, 50);
        setGroupParents();
        setChildData();
        MyExpandableAdapter1 adapter = new MyExpandableAdapter1(parentItems, childItems, getApplicationContext());
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), NotificationMain.this);
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
        getMenuInflater().inflate(R.menu.notification_main, menu);
        return true;
    }

    public void setGroupParents() {
        ArrayList<String[]> Choices = db.getArrayList();
        String[] str;
        if (Choices.size() != 0)
            for (int i = 0; i < Choices.size(); i++) {
                str = Choices.get(i);
                parentItems.add(str[0]);
            }
        else {
            new AlertDialog.Builder(this)
                    .setTitle("REQUEST")
                    .setMessage("Please synchronize and set the preferences\n")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            Intent intent = new Intent(getApplicationContext(), MainScreen.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .show();
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
            for (int j = 0; j < list.length; j++) {
                child.add(list[j]);
            }
            childItems.add(child);

        }
    }

    public void onBackPressed() {
        Intent i = new Intent(this, MainScreen.class);
        startActivity(i);
        finish();
    }
}

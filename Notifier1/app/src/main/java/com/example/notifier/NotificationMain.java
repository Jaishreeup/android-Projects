package com.example.notifier;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class NotificationMain extends Activity
{
    preferenceDB db;
    SharedPreferences sp;
    NotificationDB db1;
    TextView tv;
    Handler handler = new Handler();
    ExpandableListView expandableList;
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_main);
        expandableList = (ExpandableListView) findViewById(R.id.list1);
        db = new preferenceDB(this);
        expandableList.setDividerHeight(1);
        expandableList.setGroupIndicator(null);
        expandableList.setClickable(true);
        setGroupParents();
        setChildData();
        MyExpandableAdapter1 adapter = new MyExpandableAdapter1(parentItems, childItems, getApplicationContext());
        adapter.setInflater((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE), NotificationMain.this);
        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() 
        {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notification_main, menu);
        return true;
    }

    public void setGroupParents() 
    {
        ArrayList<String[]> Choices = db.getArrayList();
        String[] str;
        for (int i = 0; i < Choices.size(); i++) {
            str = Choices.get(i);
            parentItems.add(str[0]);
        }
    }

    public void setChildData() 
    {
        ArrayList<String> child;
        ArrayList<String[]> Choices = db.getArrayList();
        String[] str;
        for (int i = 0; i < Choices.size(); i++) {
            child = new ArrayList<String>();
            str = Choices.get(i);
            String[] list = str[1].split(" ");
            for (int j = 0; j < list.length; j++)
                child.add(list[j]);
            childItems.add(child);

        }
    }
}

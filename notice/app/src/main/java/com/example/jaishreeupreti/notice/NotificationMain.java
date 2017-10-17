package com.example.jaishreeupreti.notice;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.ArrayList;

public class NotificationMain extends android.support.v4.app.Fragment {
    private static final String ARG_PARAM2 = "param2";
    private int mParam1;
    private int mParam2;
    preferenceDB db;
    SharedPreferences sp;
    NotificationDB db1;
    TextView tv;
    Handler handler = new Handler();
    ExpandableListView expandableList;
    private ArrayList<String> parentItems = new ArrayList<String>();
    private ArrayList<Object> childItems = new ArrayList<Object>();

    public NotificationMain() {
        // Required empty public constructor
    }

    public static android.support.v4.app.Fragment newInstance() {
        NotificationMain fragment = new NotificationMain();
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
        View v = inflater.inflate(R.layout.activity_notification_main, container, false);
        expandableList = (ExpandableListView) v.findViewById(R.id.list1);
        db = new preferenceDB(getActivity());
        tv = (TextView) v.findViewById(R.id.te);
//        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MERCEDES.TTF");
//        tv.setTypeface(typeFace);
        tv.setTextSize(30);
        expandableList.setDividerHeight(2);
        expandableList.setClickable(true);
        expandableList.setIndicatorBounds(25, 50);
        setGroupParents();
        setChildData();
        MyExpandableAdapter1 adapter = new MyExpandableAdapter1(parentItems, childItems, getContext());
        adapter.setInflater((LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE), NotificationMain.this);
        expandableList.setAdapter(adapter);
        expandableList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                return false;
            }
        });
        return v;
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
            new AlertDialog.Builder(getActivity())
                    .setTitle("REQUEST")
                    .setMessage("Please synchronize and set the preferences\n")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
//                            // continue with delete
//                            Intent intent = new Intent(getContext(), MainScreen.class);
//                            startActivity(intent);
//                            finish();
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

}

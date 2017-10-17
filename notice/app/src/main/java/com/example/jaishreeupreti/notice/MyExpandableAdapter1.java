package com.example.jaishreeupreti.notice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


public class MyExpandableAdapter1 extends BaseExpandableListAdapter {
    preferenceDB db;
    SharedPreferences sp;
    Context ctx;
    private Activity activity;
    private ArrayList<Object> childtems;
    private LayoutInflater inflater;
    private ArrayList<String> parentItems, child;


    public MyExpandableAdapter1(ArrayList<String> parents, ArrayList<Object> childern, Context context) {
        this.parentItems = parents;
        this.childtems = childern;
        db = new preferenceDB(context);
        ctx = context;
        sp = context.getSharedPreferences("notify", Context.MODE_PRIVATE);
    }

    public void setInflater(LayoutInflater inflater, NotificationMain activity) {
        this.inflater = inflater;
    }


    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        child = (ArrayList<String>) childtems.get(groupPosition);
        final TextView textView;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.expandablelistview2, null);
        }
        final ImageView newNot = (ImageView) convertView.findViewById(R.id.im1);

        textView = (TextView) convertView.findViewById(R.id.textView2);
//        Typeface typeFace = Typeface.createFromAsset(ctx.getAssets(), "fonts/MERCEDES.TTF");
//        textView.setTypeface(typeFace);
        textView.setTextSize(30);
        textView.setText(child.get(childPosition));
        if (sp.getBoolean("newNot" + child.get(childPosition), false)) {
            newNot.setVisibility(View.VISIBLE);
        }

        textView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                sp.edit().putBoolean("newNot" + textView.getText(), false).commit();
                newNot.setVisibility(View.INVISIBLE);
                System.out.println("yyyyyyyyyyyy  ");
                Intent i = new Intent(ctx, NotificationsActivity.class);
                i.putExtra("preference", textView.getText());
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                ctx.startActivity(i);

            }
        });

        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row1, null);
        }
        convertView.setPadding(100, 0, 0, 0);
//        Typeface typeFace = Typeface.createFromAsset(ctx.getAssets(), "fonts/MERCEDES.TTF");
//        ((CheckedTextView) convertView).setTypeface(typeFace);
        ((CheckedTextView) convertView).setTextSize(40);
        ((CheckedTextView) convertView).setText(parentItems.get(groupPosition));
        ((CheckedTextView) convertView).setChecked(isExpanded);

        return convertView;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return ((ArrayList<String>) childtems.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getGroupCount() {
        return parentItems.size();
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {
        super.onGroupCollapsed(groupPosition);
    }

    @Override
    public void onGroupExpanded(int groupPosition) {
        super.onGroupExpanded(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }


    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

}

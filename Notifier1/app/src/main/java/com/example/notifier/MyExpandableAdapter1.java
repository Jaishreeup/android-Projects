package com.example.notifier;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.Toast;
import android.widget.TextView;

import java.util.ArrayList;


public class MyExpandableAdapter1 extends BaseExpandableListAdapter 
{
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
        this.activity = activity;
    }


    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) 
    {
        child = (ArrayList<String>) childtems.get(groupPosition);
        final TextView textView;
        if (convertView == null) 
        {
            convertView = inflater.inflate(R.layout.expandablelistview2, null);
        }

        textView = (TextView) convertView.findViewById(R.id.textView2);
        textView.setText(child.get(childPosition));
        textView.setOnClickListener(new OnClickListener() 
        {
            @Override
            public void onClick(View view) 
            {
                if (sp.getBoolean(""+textView.getText(), false)) 
                {
                    Intent i = new Intent(ctx, NotificationsActivity.class);
                    i.putExtra("preference", textView.getText());
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    ctx.startActivity(i);
                } 
                else 
                {
                    Toast.makeText(ctx, "you have not set "+textView.getText()+" as your preferennce", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return convertView;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.row, null);
        }

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

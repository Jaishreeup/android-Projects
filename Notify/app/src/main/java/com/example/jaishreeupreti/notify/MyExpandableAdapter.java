package com.example.jaishreeupreti.notify;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MyExpandableAdapter extends BaseExpandableListAdapter {
    preferenceDB db;
    SharedPreferences sp;
    Context ctx;
    private Activity activity;
    private ArrayList<Object> childtems;
    private LayoutInflater inflater;
    private ArrayList<String> parentItems, child;

    public MyExpandableAdapter(ArrayList<String> parents, ArrayList<Object> childern, Context context) {
        this.parentItems = parents;
        this.childtems = childern;
        ctx = context;
        db = new preferenceDB(context);
        sp = context.getSharedPreferences("notify", Context.MODE_PRIVATE);
    }

    public void setInflater(LayoutInflater inflater, MainActivity activity) {
        this.inflater = inflater;
        this.activity = activity;
    }


    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        child = (ArrayList<String>) childtems.get(groupPosition);

        final CheckBox textView ;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.group, null);
        }

        textView = (CheckBox) convertView.findViewById(R.id.textView1);
        textView.setText(child.get(childPosition));
        if (sp.getBoolean(child.get(childPosition), false))
            textView.setChecked(true);
     //   final CheckBox finalTextView = textView;
        convertView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                if (textView.isChecked() == false) {
                    textView.setChecked(true);
                    Toast.makeText(ctx,textView.getText(), Toast.LENGTH_LONG).show();
                    sp.edit().putBoolean("" + textView.getText(), true).commit();
                } else {
                    textView.setChecked(false);
                    sp.edit().putBoolean(""+textView.getText(), false).commit();
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
        TextView text=(TextView)convertView.findViewById(R.id.textView1);
        ImageView im=(ImageView)convertView.findViewById(R.id.im);
        im.setImageDrawable(convertView.getResources().getDrawable(R.drawable.sync));
                text.setText(parentItems.get(groupPosition));
      //  ((CheckedTextView) convertView).setChecked(isExpanded);

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

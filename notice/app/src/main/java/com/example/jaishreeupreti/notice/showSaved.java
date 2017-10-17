package com.example.jaishreeupreti.notice;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class showSaved extends android.support.v4.app.Fragment {
    private static final String ARG_PARAM2 = "param2";
    private int mParam1;
    private int mParam2;
    NotificationDB db;
    ArrayList<String[]> arrayList;
    TextView t;
    ListView listView;
    String[] str;

    public showSaved() {
        // Required empty public constructor
    }

    public static android.support.v4.app.Fragment newInstance() {
        showSaved fragment = new showSaved();
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
        View v = inflater.inflate(R.layout.activity_show_saved, container, false);
        db=new NotificationDB(getContext());
//        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MERCEDES.TTF");
        t = (TextView) v.findViewById(R.id.tvcs);
        t.setTextSize(40);
//        t.setTypeface(typeFace);
        t.setText("Saved Notices");
        listView = (ListView) v.findViewById(R.id.nlist);
        registerForContextMenu(listView);
        t = (TextView) v.findViewById(R.id.textView3);
        db = new NotificationDB(getActivity());
        refresh();
        return v;
    }
    public void refresh()
    {
        arrayList=db.getSaved();
        int ukk = arrayList.size();
        final String notList[] = new String[arrayList.size()];
        for (int i = 0; i < ukk; i++) {
            str =arrayList.get(i);
            notList[i] = str[0];
        }
        if (arrayList.size() == 0)
            t.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.my_textview, R.id.tv, notList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String[] str = arrayList.get(position);
                for (int i = 0; i < str.length; i++)
                    System.out.println("item :" + i + "  " + str[i]);
                if (str[6].equals("empty")) {
                    Intent intent = new Intent(getContext(), info.class);
                    intent.putExtra("details", arrayList.get(position));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getContext(), pdfActivity.class);
                    intent.putExtra("fname", str[6]);
                    startActivity(intent);
                }
            }
        });

    }
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.nlist) {
            MenuInflater inflater = getActivity().getMenuInflater();
            inflater.inflate(R.menu.saved_list, menu);
        }
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String[] str=arrayList.get(info.position);
        switch(item.getItemId()) {
            case R.id.delete:
                db.deleteRecord(str[4]);
                refresh();
                // remove stuff here
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getActivity().getMenuInflater().inflate(R.menu.notifications, menu);
        return true;
    }


}

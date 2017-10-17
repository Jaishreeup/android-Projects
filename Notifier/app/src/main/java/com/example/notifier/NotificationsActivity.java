package com.example.notifier;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class NotificationsActivity extends Activity {
    String preference;
    ListAdapter la;
    ListView listView;
    NotificationDB db;
    String[] str;
    TextView t;
    ArrayList<String[]> notifications;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/MERCEDES.TTF");
        t = (TextView) findViewById(R.id.tvcs);
        t.setTextSize(40);
        t.setTypeface(typeFace);
        preference = getIntent().getExtras().get("preference").toString();
        t.setText(preference);
        listView = (ListView) findViewById(R.id.nlist);
        registerForContextMenu(listView);
        t = (TextView) findViewById(R.id.textView3);
        db = new NotificationDB(this);
     refresh();
/*        notifications = db.getArrayList(preference);
        int ukk = notifications.size();
        final String notList[] = new String[notifications.size()];
        System.out.println("hjjh " + notifications.size() + " " + preference);
        for (int i = 0; i < ukk; i++) {
            str = notifications.get(i);
            notList[i] = str[0];
        }
        if (notifications.size() == 0)
            t.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_textview, R.id.tv, notList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String[] str = notifications.get(position);
                for (int i = 0; i < str.length; i++)
                    System.out.println("item :" + i + "  " + str[i]);
                if (str[6].equals("empty")) {
                    Intent intent = new Intent(getApplicationContext(), info.class);
                    intent.putExtra("details", notifications.get(position));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("fname", str[6]);
                    startActivity(intent);
                }
            }
        });
*/
    }
    public void refresh()
    {
        notifications = db.getArrayList(preference);
        int ukk = notifications.size();
        final String notList[] = new String[notifications.size()];
        System.out.println("hjjh " + notifications.size() + " " + preference);
        for (int i = 0; i < ukk; i++) {
            str = notifications.get(i);
            notList[i] = str[0];
        }
        if (notifications.size() == 0)
            t.setVisibility(View.VISIBLE);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.my_textview, R.id.tv, notList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                String[] str = notifications.get(position);
                for (int i = 0; i < str.length; i++)
                    System.out.println("item :" + i + "  " + str[i]);
                if (str[6].equals("empty")) {
                    Intent intent = new Intent(getApplicationContext(), info.class);
                    intent.putExtra("details", notifications.get(position));
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("fname", str[6]);
                    startActivity(intent);
                }
            }
        });

    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId()==R.id.nlist) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_list, menu);
        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        String[] str=notifications.get(info.position);
        switch(item.getItemId()) {
            case R.id.save:db.setSaved(str[4]);
                refresh();
                // add stuff here
                return true;
            case R.id.delete:
                db.deleteRecord(str[4]);
                refresh();
                // remove stuff here
                return true;
            case R.id.resync:
                if(isConnected()){
                    new downloadPDF().downloadAndOpenPDF(str[6]);
                    new AlertDialog.Builder(this)
                            .setTitle("Confirmation")
                            .setMessage("Resync in progress but it might take some time. \n")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    // continue with delete

                                }
                            }).show();

                }
                else
                new AlertDialog.Builder(this)
                        .setTitle("No Network")
                        .setMessage("Please Check your Internet Connection?\n")
                        .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // continue with delete

                            }
                        }).show();

            default:
                return super.onContextItemSelected(item);
        }
    }

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.notifications, menu);
        return true;
    }
public void deleteAll(View view)
{
    new AlertDialog.Builder(this)
            .setTitle("CONFIRM")
            .setMessage("Do you want to delete all the notices of this category?\n")
            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // continue with delete

                    db.deletePreference(preference);
                    refresh();
                }
            }).setNegativeButton(android.R.string.no, null).show();

}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete) {
            db.deletePreference(preference);
            refresh();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

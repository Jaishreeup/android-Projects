package com.example.jaishreeupreti.applock;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.app.Activity;
import java.util.List;
import android.view.ViewGroup.LayoutParams;

class AppInfo {
    String appname = "";
    String pname = "";
    String versionName = "";
    int versionCode = 0;
    Drawable icon;
}
public class section1 extends Activity {
    String temp="";
    TableLayout table;
    Drawable image;
    ImageView unlock;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        sp=getSharedPreferences("applock",Context.MODE_WORLD_READABLE);

        int k=0;

        table = (TableLayout) findViewById(R.id.table);
        List<PackageInfo> apps = getPackageManager().getInstalledPackages(0);
        //ArrayList<AppInfo> res = new ArrayList<AppInfo>();
        try {
            for (int i = 0; i < apps.size(); i++) {
                PackageInfo p = apps.get(i);
                ApplicationInfo ai;
                ai = getPackageManager().getApplicationInfo(p.packageName, 0);
                final AppInfo newInfo = new AppInfo();
                if ((ai.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
                   // System.out.println(">>>>>>packages is system package" + p.packageName);
                    continue;
                }

                newInfo.appname = p.applicationInfo.loadLabel(getPackageManager()).toString();
                newInfo.pname = p.packageName;
                if(p.packageName.equals("com.example.jaishreeupreti.applock"))continue;
                //newInfo.versionName = p.versionName;
                //newInfo.versionCode = p.versionCode;
                newInfo.icon = p.applicationInfo.loadIcon(getPackageManager());
               // res.add(newInfo);

                RelativeLayout row = new RelativeLayout(this);
                row.setLayoutParams(new ViewGroup.LayoutParams(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT));

                RelativeLayout.LayoutParams param = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT);
                param.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                param.setMargins(0, 5, 0, 0);

                ImageView icon = new ImageView(this);
                icon.setImageDrawable(newInfo.icon);
                icon.setId(1001);

                RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT);
                params2.addRule(RelativeLayout.RIGHT_OF, 1001);
                params2.addRule(RelativeLayout.CENTER_VERTICAL);
                params2.setMargins(10, 0, 0, 0);

                TextView textview = new TextView(this);
                textview.setText(newInfo.appname);
                textview.setTextSize(15);

                RelativeLayout.LayoutParams params3 = new RelativeLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                        ActionBar.LayoutParams.WRAP_CONTENT);
                params3.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params3.setMargins(0, 10, 0, 0);

                final  Switch sw = new Switch(this);
                sw.setLayoutParams(params3);
                String state=sp.getString(newInfo.pname,"");

                if(state.equals("on"))
                    sw.setChecked(true);
                else sw.setChecked(false);
                sw.setId(k++);

                sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                  @Override
                                                  public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                                                      if (isChecked) {
                                                          sp.edit().putString(newInfo.pname, "on").commit();
                                                          Toast.makeText(getApplicationContext(), newInfo.appname + " locked", Toast.LENGTH_SHORT).show();
                                                      } else {
                                                          sp.edit().putString(newInfo.pname, "off").commit();
                                                          Toast.makeText(getApplicationContext(), newInfo.appname + " unlocked", Toast.LENGTH_SHORT).show();
                                                      }
                                                  }
                                              }
                );

                icon.setLayoutParams(param);
                textview.setLayoutParams(params2);

                row.addView(sw);
                row.addView(textview);
                row.addView(icon, 100, 100);
                table.addView(row);
                startService(new Intent(section1.this, appLockerService.class));
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public void go(View view)
    {
        Intent intent=new Intent(getApplicationContext(),MainActivity.class);
        startActivity(intent);
    }
public void onStop()
{
    super.onStop();
    sp.edit().putString("com.example.jaishreeupreti.applock", "off").commit();
}
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // do something on back.
            Intent startHomescreen = new Intent(Intent.ACTION_MAIN);
            startHomescreen.addCategory(Intent.CATEGORY_HOME);
            startHomescreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(startHomescreen);

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_section1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

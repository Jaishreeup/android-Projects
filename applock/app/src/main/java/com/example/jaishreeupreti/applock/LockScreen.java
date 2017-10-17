package com.example.jaishreeupreti.applock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import java.util.Timer;


public class LockScreen extends Activity{

    EditText password;
    Button button[] = new Button[12];
    String temp;
    String value,check="";
    Bundle b;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock_screen);
        b=getIntent().getExtras();
        check=(String)b.get("receiver");
        sp=getSharedPreferences("applock",Context.MODE_PRIVATE);
        value=sp.getString("pin","1234");
        password = (EditText) findViewById(R.id.pass);
        password.setKeyListener(null);
        Log.d("lockkk","hkkji");
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button0:
                password.append("0");
                temp = password.getText().toString();
                if (temp.equals(value)) {
                    password.setText("");
                    finish();
                }
                break;
            case R.id.button1:
                password.append("1");
                temp = password.getText().toString();
                if (temp.equals(value)) {
                    password.setText("");
                    finish();
                }
                break;
            case R.id.button2:
                password.append("2");
                temp = password.getText().toString();
                if (temp.equals(value)) {
                    password.setText("");
                    finish();
                }
                break;
            case R.id.button3:
                password.append("3");
                temp = password.getText().toString();
                if (temp.equals(value)) {
                password.setText("");
                finish();
            }
                break;
            case R.id.button4:
                password.append("4");
                temp = password.getText().toString();
                if (temp.equals(value)) {
                    password.setText("");
                    finish();
                    }
                break;
            case R.id.button5:
                password.append("5");
                temp = password.getText().toString();
                if (temp.equals(value)) {
                    password.setText("");
                    finish();
                }
                break;
            case R.id.button6:
                password.append("6");
                temp = password.getText().toString();
                if (temp.equals(value)) {
                    password.setText("");
                    finish();
                }
                break;
            case R.id.button7:
                password.append("7");
                temp = password.getText().toString();
                if (temp.equals(value)) {
                    password.setText("");
                    finish();
                }
                break;
            case R.id.button8:
                password.append("8");
                temp = password.getText().toString();
                if (temp.equals(value)) {
                    password.setText("");
                    finish();
                }
                break;
            case R.id.button9:
                password.append("9");
                temp = password.getText().toString();
                if (temp.equals(value)) {
                    password.setText("");
                    finish();
                }
                break;
            case R.id.buttonback:
                temp = password.getText().toString();
                if (temp.length() > 0) {
                    temp = temp.substring(0, temp.length() - 1);
                    password.setText(temp);
                }
                break;
            case R.id.buttonOK:
                Intent startHomescreen = new Intent(Intent.ACTION_MAIN);
                startHomescreen.addCategory(Intent.CATEGORY_HOME);
               /// startHomescreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(startHomescreen);
                finish();
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lock_screen, menu);
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

    public void onStop()
    {
        super.onStop();
        if(!check.equals("yes")) {
            Log.d("maiyahan", "yup");
            startService(new Intent(getApplicationContext(), appLockerService.class));
         //   Toast.makeText(getApplicationContext(),"onstop",Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                finishAndRemoveTask();
        }
        else
        {
            Log.d("maiyahanhu", "yupoo");
            check="yes";
            startService(new Intent(getApplicationContext(), appLockerService.class));
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent startHomescreen = new Intent(Intent.ACTION_MAIN);
            startHomescreen.addCategory(Intent.CATEGORY_HOME);

           // startHomescreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startService(new Intent(getApplicationContext(), appLockerService.class));
            startActivity(startHomescreen);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
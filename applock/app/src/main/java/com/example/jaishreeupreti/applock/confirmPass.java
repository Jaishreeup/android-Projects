package com.example.jaishreeupreti.applock;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class confirmPass extends ActionBarActivity {

    String cpass,pass;
    EditText et;
    Bundle b;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmpass);
        b=getIntent().getExtras();
        pass=b.get("pass").toString();
        sp=getSharedPreferences("applock", Context.MODE_PRIVATE);
       // Toast.makeText(getApplicationContext(),"hi"+pass,Toast.LENGTH_LONG).show();
    }

    public void getPass(View view)
    {
       // Toast.makeText(getApplicationContext(),"hi"+pass,Toast.LENGTH_LONG).show();
        et=(EditText)findViewById(R.id.pass2);
        cpass=et.getText().toString();
        if(cpass.equals(pass))
        {
            sp.edit().putString("pin",pass).commit();
            new AlertDialog.Builder(this)
                    .setTitle("Confimation")
                    .setMessage("Your PIN has been set\n")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            Intent intent=new Intent(getApplicationContext(),section1.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .show();
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setTitle("Wrong PIN")
                    .setMessage("PIN does not match, please try again!\n")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    })
                    .show();
        }




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_confirm_pass, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

package com.example.jaishreeupreti.callblocker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;


public class MainActivity extends ActionBarActivity {

    SharedPreferences sp;
    String num="";
    Switch sw,sw_;
    EditText line,e_name,line_,e_name_;
    Button number,contacts;
    ImageButton image;
   Drawable c1,c2;
    KeyListener k,k1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.numberlayout);
   /*     Parse.initialize(this, "pNJrlrhERLPxjLX7lxLfJchGTXcl3tNyHjkLHsxz", "aLJ2ZDhhEO9gUbY1M9vjlrvSPQBaB6Hd42CgLx7d");
        ParseObject testObject = new ParseObject("TestObject");
        testObject.put("foo", "bar");
        testObject.saveInBackground();
     */   line = (EditText)findViewById(R.id.line);
        e_name = (EditText)findViewById(R.id.name);
        sp = getSharedPreferences("callblocker", Context.MODE_PRIVATE);
         sw = (Switch) findViewById(R.id.switch1);
          number=(Button)findViewById(R.id.button);
        contacts=(Button)findViewById(R.id.button2);
        image=(ImageButton)findViewById(R.id.contacts);

        String state=sp.getString("state","");
        if(state.equals("on"))
        sw.setChecked(true);
        else
        sw.setChecked(false);
        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                          @Override
                                          public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                              if (isChecked) {
                                                  sp.edit().putString("state", "on").commit();
                                                  startService(new Intent(MainActivity.this, CallBlockerService.class));
                                                  Toast.makeText(getApplicationContext(), "blocker enabled", Toast.LENGTH_SHORT).show();
                                              } else {
                                                  sp.edit().putString("state", "off").commit();
                                                  stopService(new Intent(MainActivity.this, CallBlockerService.class));
                                                  Toast.makeText(getApplicationContext(), "blocker disabled", Toast.LENGTH_SHORT).show();
                                              }
                                          }
                                      }
        );
         k= line.getKeyListener();
        k1=e_name.getKeyListener();
         c1=number.getBackground();
         c2=contacts.getBackground();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void changeLayout(View view)
    {
        line.setKeyListener(null);
        e_name.setKeyListener(null);
        number.setBackground(c2);
        contacts.setBackground(c1);
        image.setVisibility(View.VISIBLE);
        line.setText("");
        e_name.setText("");
    }

    public void changeAgain(View view)
    {
        Log.d("change","yes");
        line.setKeyListener(k);
        e_name.setKeyListener(k1);
        number.setBackground(c1);
        contacts.setBackground(c2);
        image.setVisibility(View.INVISIBLE);
        line.setText("");
        e_name.setText("");
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
        if (id == R.id.blocklist) {
           /* int count=0;
            String temp="", list="";
            for(int i=0;i<num.length();i++)
            {
                if(num.charAt(i)==',')
                {
                    count++;
                    list+=temp+" ";
                    num=num.replaceAll(temp+',',"");
                    temp="";
                    Log.d("length",num);
                    Log.d("list",list);
                    i=-1;
                }
                else temp+=num.charAt(i);
            }
                count++;
                //list+=temp+" ";
*/
            String temp="\n"+sp.getString("blocklist","").replaceAll(",","\n");
       sp.edit().putString("blocklist",temp).commit();
            Intent i=new Intent(getApplicationContext(),blocklist.class);
            startActivity(i);
          /*  new AlertDialog.Builder(this)
                    .setTitle("Block List")
                    .setMessage("You have blocked the following numbers\n"+temp+"\n")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete

                        }
                    })
                    .show();*/
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public void pickContacts(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, 0);
    }

    public void block(View view)
     {
         String num1= line.getText().toString();
         String name=e_name.getText().toString();
         if(!num1.isEmpty())
         {
             num=num1+"\n";
             String temp=sp.getString("blocklist","")+num.replaceAll(" ","");
             sp.edit().putString("blocklist",temp).commit();
         Toast.makeText(getApplicationContext(), "contacts successfully added to the block list!!", Toast.LENGTH_SHORT).show();
         }
         else
             Toast.makeText(getApplicationContext(),"please enter atleast one contact number",Toast.LENGTH_SHORT).show();
      line.setText("");
         e_name.setText("");
     }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri uri = data.getData();


            if (uri != null) {
                Cursor c = null;
                try {
                    c = getContentResolver().query(
                              uri,new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,
                            //new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,
                                 ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME},null,null,null);   // null, null, null);

                    if (c != null && c.moveToFirst()) {
                        String number = c.getString(0);
                        String name = c.getString(1);
                        String temp=e_name.getText().toString();
                       // String name=c.getString(2);
                          String s = line.getText().toString();
                        if (!s.equals(""))
                        {
                            s += ", " + number;
                            temp+=", " + name;
                        }
                        else
                        {
                            s = number;
                            temp=name;
                        }
                        s=s.replaceAll(" ","");
                        line.setText(s);
                        e_name.setText(temp);
                    }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
            }
        }
    }

}

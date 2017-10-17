package com.example.sports;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewUser extends ActionBarActivity {
	EditText ename,eage,epno,euname,epass;
	Button esub,eres;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_user);
		ename=(EditText)findViewById(R.id.name);
		eage=(EditText)findViewById(R.id.age);
		epno=(EditText)findViewById(R.id.pno);
		euname=(EditText)findViewById(R.id.uname);
		epass=(EditText)findViewById(R.id.pass);
		esub=(Button)findViewById(R.id.submit);
		eres=(Button)findViewById(R.id.reset);
		
		
		
		esub.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String sname=ename.getText().toString();
				String sage=eage.getText().toString();
				String spno=epno.getText().toString();
				String suname=euname.getText().toString().trim();
				String spass=epass.getText().toString();
				String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
				String emailpattern2 = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+\\.+[a-z]+";

				if(sname.equals(""))
				{ 
						Toast.makeText(getApplicationContext(),"Enter the name ",Toast.LENGTH_SHORT).show();
				}
			    else if(sage.equals(""))
					{
						Toast.makeText(getApplicationContext(),"Enter age",Toast.LENGTH_SHORT).show();
					}
				else if(spno.equals("")||spno.length()<10)
				    { 
					Toast.makeText(getApplicationContext(),"Invalid Contact",Toast.LENGTH_SHORT).show();
					}
				else if(spass.equals("")||spass.length()<6)
				{
					Toast.makeText(getApplicationContext(),"Password should be of atleast 6 characters",Toast.LENGTH_SHORT).show();
				}
				else if(!suname.matches(emailpattern)
						&& !suname.matches(emailpattern2)) 
				{
					Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_LONG).show();
				}
					
				else
				{Intent i=new Intent(NewUser.this,Login.class);
				 startActivity(i);
				}				
			}
			
		}
		);
		eres.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ename.setText("");
				eage.setText("");
				euname.setText("");
				epass.setText("");
				epno.setText("");
				}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_user, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

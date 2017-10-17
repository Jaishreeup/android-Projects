package com.example.sports;

import android.support.v7.app.ActionBarActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Login extends ActionBarActivity {
    EditText username,password;
    Button ok;
   
    
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		username=(EditText)findViewById(R.id.username);
		password=(EditText)findViewById(R.id.password);
		ok=(Button)findViewById(R.id.ok);
		Toast.makeText(getApplicationContext(), "abcd",Toast.LENGTH_LONG).show();
	   ok.setOnClickListener(new OnClickListener(){
 
		@Override
		public void onClick(View v) {
			  System.out.println("hiiiiiiiii");
			// TODO Auto-generated method stub
			Intent j=new Intent(Login.this,Start.class);
			startActivity(j);
		}
		
	});
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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

package com.example.notifier;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainScreen extends Activity 
{
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_screen);
	}
	public void changescreen(View view)
	{
		int id=view.getId();
		if(id==R.id.button1)
		{
			Intent intent = new Intent(this,NotificationMain.class);
			Toast.makeText(this, "Clicked", Toast.LENGTH_LONG).show();
			startActivity(intent);
		}
		if(id==R.id.button2)
		{
			Intent intent = new Intent(this,SetPreferences.class);
			startActivity(intent);
		}
	}
	@SuppressWarnings("deprecation")
	public void onBackPressed()
	{
		AlertDialog alert_back = new AlertDialog.Builder(this).create();
		alert_back.setTitle("Quit?");
		alert_back.setMessage("Are you sure want to quit?");
		alert_back.setButton("Yes",new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface arg0, int arg1) 
			{
				arg0.dismiss();
				MainScreen.this.finish();
			}
		});
		alert_back.setButton2("No",new DialogInterface.OnClickListener() 
		{
			public void onClick(DialogInterface arg0, int arg1) 
			{
				arg0.dismiss();
			}
		});
		alert_back.show();
	}

	public boolean onCreateOptionsMenu(Menu menu) 
	{
		getMenuInflater().inflate(R.menu.main_screen, menu);
		return true;
	}
	public boolean onOptionsItemSelected(MenuItem item) 
	{
		return super.onOptionsItemSelected(item);
	}
}
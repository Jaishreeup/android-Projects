package com.example.sports;

import java.util.Calendar;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CreateGroup extends Activity {
	Spinner dropdown ;
	private Button date;
	private TextView mDateDisplay;
	private int mYear;
	private int mMonth;
	private int mDay;
 
	static final int DATE_DIALOG_ID = 0;
	private Button mPickTime;
	private TextView mTimeDisplay;
    private int mHour;
	private int mMinute;
	static final int TIME_DIALOG_ID = 1;
	Infodb db;
    int count;
    Button venue;
	EditText vac;
	 SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_create_group);
		db=new Infodb(getApplicationContext());
	    sp=getSharedPreferences("sports", Context.MODE_PRIVATE);
	    count=sp.getInt("count",5);
		
	        dropdown= (Spinner)findViewById(R.id.spinner1);
		String[] items = new String[]{"--SELECT--","BasketBall", "Football", "Badminton","Cricket"};
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
		dropdown.setAdapter(adapter);
		mDateDisplay=(TextView)findViewById(R.id.textView3);
		date=(Button)findViewById(R.id.button1);
		mTimeDisplay = (TextView) findViewById(R.id.textView4);
		mPickTime = (Button) findViewById(R.id.button2);
		venue=(Button)findViewById(R.id.button3);
		
		vac=(EditText)findViewById(R.id.editText1);
		
		
		date.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        mPickTime.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);
			}
		});
       
        
		// display the current date
		

        // get the current date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
		mMinute = c.get(Calendar.MINUTE);
        // display the current date (this method is below)
        updateDisplay();
        timeupdateDisplay();
        
        
    }
	 public void save(View view)
	    {
	        System.out.println("inside save");
	        if(""+vac.getText()=="")
	            Toast.makeText(getApplicationContext(),"Please fill all entries..",Toast.LENGTH_LONG).show();
	       else {
	           try
	           {
	               System.out.println("ye hai count :" + count);
	               String str[]={"" + count, "" + dropdown.getSelectedItem().toString(),"" + vac.getText(),""};
	               
	               db.addRecord(str[0], str[1], Integer.parseInt(str[2]));
	               sp.edit().putInt("count", ++count).commit();
	            //finish();
	               Intent intent=new Intent();
	              intent.putExtra("RESULT_STRING",str);
	               setResult(RESULT_OK, intent);
	              
	            //Intent i=new Intent(this,MyActivity.class);
	            //startActivity(intent);
	            finish();
	           }
	           catch (Exception e)
	           {
	               Toast.makeText(getApplicationContext(),"Please fill all entries correctly.",Toast.LENGTH_LONG).show();
	           }

	        }

	    }
	 private TimePickerDialog.OnTimeSetListener mTimeSetListener =
     		new TimePickerDialog.OnTimeSetListener() {
     			public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
     				mHour = hourOfDay;
     				mMinute = minute;
     				timeupdateDisplay();
     			}
     		};
     			@Override
       protected Dialog onCreateDialog(int id) {
         switch (id) {
         case DATE_DIALOG_ID:
             return new DatePickerDialog(this, mDateSetListener, mYear, mMonth, mDay);
             
         case TIME_DIALOG_ID:
             return new TimePickerDialog(this,mTimeSetListener, mHour, mMinute, false);
        }
         return null;
     }
    
   
	
    // updates the date we display in the TextView
    private void updateDisplay() {
        mDateDisplay.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonth + 1).append("-")
                    .append(mDay).append("-")
                    .append(mYear).append(" "));
    }
    
    private String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}	
    
    private void timeupdateDisplay() {
		mTimeDisplay.setText(
			new StringBuilder()
			.append(pad(mHour)).append(":")
			.append(pad(mMinute)));
	}
    // the callback received when the user "sets" the date in the dialog
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    updateDisplay();
                }

               
                		
	};
    
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.create_group, menu);
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

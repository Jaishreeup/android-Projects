package mycontentprovider.example.com.conductor;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class conductor extends Activity {
	EditText src,dest,date,qty,bid;
	 Button submit;
	routeDB db;
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.fragment_main);
			db=new routeDB(getApplicationContext());
			String valbid=(String) getIntent().getExtras().get("bid");
	    src=(EditText)findViewById(R.id.editText2);
	    dest=(EditText)findViewById(R.id.editText3);
	    date=(EditText)findViewById(R.id.editText6);
	    qty=(EditText)findViewById(R.id.editText4);
	    bid=(EditText)findViewById(R.id.editText1);
		bid.setText(valbid);
		String result=	db.getRoute(valbid);
		String route[]=result.split(" ");// now use this array to put in the spinner drop down menu
	    submit=(Button)findViewById(R.id.button1);
	    submit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	    
	  
}
}
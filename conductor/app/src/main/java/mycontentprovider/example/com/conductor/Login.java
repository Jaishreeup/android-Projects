package mycontentprovider.example.com.conductor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;



public class Login extends Activity {
	
	EditText e1,e2;
	Button b;

	EditText e1,e2;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginmodule);
		e1=(EditText)findViewById(R.id.busid);
		e2=(EditText)findViewById(R.id.cid);
    	b=(Button)findViewById(R.id.button1);
    	
    	b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(),conductor.class);
				i.putExtra("bid",e1.getText());
				startActivity(i);
			}
		});
        
	}
    
	
	

}

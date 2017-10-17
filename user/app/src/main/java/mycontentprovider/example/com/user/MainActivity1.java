package mycontentprovider.example.com.user;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MainActivity1 extends Activity {

	TextView tv1,tv2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_splash1);
      /*if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
      /*tv1=(TextView)findViewById(R.id.textView1);
      tv2=(TextView)findViewById(R.id.textView2);
      Typeface face=Typeface.createFromAsset(getAssets(),"fonts/broadway.ttf");
      tv1.setTypeface(face);
      tv2.setTypeface(face);*/
      Thread timer=new Thread()
      {
      	
      	public void run()
      	
      	{
      		try
      		{
      			
      			sleep(5000);
      			
      		}
      		catch(InterruptedException e)
      		{
      			e.printStackTrace();
      		}
      		
      		finally
      		{
      			Intent nextactivity= new Intent(getApplicationContext(),firstPage.class);
      			startActivity(nextactivity);
      			
      		}
      		
      		
      	} 
      	
      };
      timer.start();
	}
  				
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main_activity1, menu);
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

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main_activity1,
					container, false);
			return rootView;
		}
	}

}

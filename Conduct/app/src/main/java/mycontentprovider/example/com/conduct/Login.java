package mycontentprovider.example.com.conduct;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Login extends Activity {

	Button b;
	EditText e1,e2,e3;
	routeDB db;
	String b_id,rid;
	static SharedPreferences sp;

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginmodule);
		e1=(EditText)findViewById(R.id.busid);
		e2=(EditText)findViewById(R.id.cid);
		e3=(EditText)findViewById(R.id.routeid);
		b=(Button)findViewById(R.id.button1);
		db=new routeDB(getApplicationContext());
		sp = getSharedPreferences("conduct", Context.MODE_PRIVATE);
		b.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 b_id=""+e1.getText();
				rid=""+e3.getText();
				try {
					send();
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Intent i=new Intent(getApplicationContext(),conductor.class);
				i.putExtra("bid",b_id);
				startActivity(i);
				finish();

			}
		});
        
	}


	public void send() throws InterruptedException {

		if(isConnected())
		{
			System.out.println("conneeeeeeee");
			new Thread(new Runnable() {
				@Override
				public void run() {

					HttpClient httpclient = new DefaultHttpClient();
					HttpPost httppost = new HttpPost("http://abesec.in/witty/buslogin.php");
					JSONObject json = new JSONObject();
					try {
						// Add your data
						json.put("bid", b_id);
						StringEntity se = new StringEntity(json.toString());
						httppost.setEntity(se);
						// Execute HTTP Post Request
						// 7. Set some headers to inform server about the type of the content
						httppost.setHeader("Accept", "application/json");
						httppost.setHeader("Content-type", "application/json");
						String result;
						System.out.println("oooookkkkkk");
						System.out.println("nnnnooooo");
						// 8. Execute POST request to the given URL
						HttpResponse httpResponse = httpclient.execute(httppost);
						InputStream inputStream = httpResponse.getEntity().getContent();
						System.out.println("cheeeeccckk");

						// convert inputstream to string
						if (inputStream != null)
							result = convertInputStreamToString(inputStream);
						else
							result = "Did not work!";

						System.out.println("this issss: " + result);
						//result="[\"available\":\"48\"]";
						JSONObject j = new JSONObject(result);
						db.addRecord(b_id,""+j.get("num_stops"),""+j.get("route"));
						System.out.println("hhhhhhhhhhhhh");

					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}

			}).start();
			Thread.sleep(1000);
		}
		else Toast.makeText(this,"internet not available",Toast.LENGTH_LONG).show();
	}
	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();
		return result;

	}
	public boolean isConnected() {

		System.out.println("eeeeeeee");
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected())
			return true;
		else
			return false;
	}


}

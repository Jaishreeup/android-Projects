package mycontentprovider.example.com.conduct;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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

 import static mycontentprovider.example.com.conduct.functions.*;


public class Login extends Activity {
    static SharedPreferences sp;
    Button b;
    EditText e1;
    routeDB db;
    String b_id;
    LinearLayout sv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.loginmodule);
        e1 = (EditText) findViewById(R.id.busid);
        e1.setHintTextColor(Color.WHITE);
        sv = (LinearLayout) findViewById(R.id.ll);
        b = (Button) findViewById(R.id.button1);
        db = new routeDB(getApplicationContext());
        sp = getSharedPreferences("conduct", Context.MODE_PRIVATE);
        b.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                b_id = "" + e1.getText();
                try {
                    send();
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent i = new Intent(getApplicationContext(), conductor.class);
                i.putExtra("bid", b_id);
                startService(new Intent(getApplicationContext(), MyService.class));
                startActivity(i);
                finish();
            }
        });
        sv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                if (e1.isFocused()) {
                    e1.clearFocus();
                    hideSoftKeyboard(Login.this);
                }
                return false;
            }
        });
    }

    public void send() throws InterruptedException {
        if (isConnected(getApplicationContext())) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://wittybus.000webhostapp.com/buslogin.php");
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
                        // 8. Execute POST request to the given URL
                        HttpResponse httpResponse = httpclient.execute(httppost);
                        InputStream inputStream = httpResponse.getEntity().getContent();
                        // convert inputstream to string
                        if (inputStream != null)
                            result = convertInputStreamToString(inputStream);
                        else
                            result = "Did not work!";
                        System.out.println("hhhhhhhhhhhhhhh+ "+result+" "+b_id);
                        JSONObject j = new JSONObject(result);
                        db.addRecord(b_id, "" + j.get("num_stops"), "" + j.get("route"));

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
        } else Toast.makeText(this, "Internet not available", Toast.LENGTH_LONG).show();
    }


}

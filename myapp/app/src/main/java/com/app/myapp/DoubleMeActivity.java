package com.app.myapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class DoubleMeActivity extends Activity implements OnClickListener {

    EditText inputValue=null;
    Integer doubledValue =0;
    Button doubleMe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calculate);

        inputValue = (EditText) findViewById(R.id.inputNum);
        doubleMe = (Button) findViewById(R.id.doubleme);

        doubleMe.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.doubleme:

                new Thread(new Runnable() {
                    public void run() {

                        try{
                            URL url = new URL("http://10.0.2.2:8080//MyServerProject/DoubleMeServlet");
                            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                            String inputString = inputValue.getText().toString();
                            //inputString = URLEncoder.encode(inputString, "UTF-8");
                            //connection.setMethod("POST");


                            connection.setDoOutput(true);
                            Log.d("inputString", inputString);
                            OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
                            out.write(inputString);
                            out.close();
                            // Get the response code
                            int statusCode = connection.getResponseCode();

                            InputStream is = null;

                            if (statusCode >= 200 && statusCode < 400) {
                                // Create an InputStream in order to extract the response object
                                is = connection.getInputStream();
                            }
                            else {
                                is = connection.getErrorStream();
                            }

                            Log.d("inputStringgggg", inputString);
                            BufferedReader in = new BufferedReader(new InputStreamReader(is));//connection.getInputStream()));

                            String returnString="";
                            doubledValue =0;
                            Log.d("inputString", inputString);
                            while ((returnString = in.readLine()) != null)
                            {
                                Log.d("inputString", inputString);
                                doubledValue= Integer.parseInt(returnString);
                            }
                            in.close();


                            runOnUiThread(new Runnable() {
                                public void run() {

                                    inputValue.setText(doubledValue.toString());

                                }
                            });

                        }catch(Exception e)
                        {
                            Log.d("Exception",e.toString());
                        }

                    }
                }).start();

                break;
        }
    }

}
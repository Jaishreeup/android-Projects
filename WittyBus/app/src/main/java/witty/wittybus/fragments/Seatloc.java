package witty.wittybus.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import witty.wittybus.R;
import witty.wittybus.activity.SeatStatus;

public class Seatloc extends android.support.v4.app.Fragment
{
    private static final String ARG_PARAM2 = "param2";

    private int mParam2;
    ProgressDialog progress;
    public static android.support.v4.app.Fragment newInstance(int param2)
    {
        Seatloc fragment = new Seatloc();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    SharedPreferences sp;
    public Seatloc() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }
    String s1,s2;
    Button btn;
    EditText src,dest;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.activity_seat_status_loc, container, false);
        sp=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        src=(EditText)v.findViewById(R.id.src);
        dest=(EditText)v.findViewById(R.id.dest);
        btn=(Button)v.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1 = ""+src.getText();
                s2 = ""+dest.getText();
                progress = new ProgressDialog(getActivity());
                progress.setTitle("Finding Buses");
                progress.setMessage("Please wait while loading...");
                progress.show();
                progress.setCancelable(false);
                new load().execute();
            }
        });
        return v;
    }
    class load extends AsyncTask<String, Void, Void>
    {
        protected void onPreExecute() {
        }
        String result;

        protected Void doInBackground(String... urls)
        {
            if (isConnected())
            {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://wittybus.000webhostapp.com/final.php");
                JSONObject json = new JSONObject();
                try
                {
                    json.put("src",s1);
                    json.put("dest", s2);
                    System.out.print("loiuyghjyufgcvbj");
                    StringEntity se = new StringEntity(json.toString());
                    httppost.setEntity(se);
                    httppost.setHeader("Accept", "application/json");
                    httppost.setHeader("Content-type", "application/json");
                    HttpResponse httpResponse = httpclient.execute(httppost);
                    InputStream inputStream = httpResponse.getEntity().getContent();
                    if (inputStream != null)
                        result = convertInputStreamToString(inputStream);
                    else
                        result = "Did not work!";
                } catch (ClientProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        protected void onPostExecute(Void unused)
        {
            progress.dismiss();
            if(result.length()<6)
                Toast.makeText(getActivity(), "Sorry no buses available between these locations", Toast.LENGTH_LONG).show();
            else
            {
                Intent intent = new Intent(getActivity(), SeatStatus.class);
                intent.putExtra("json", result);
                startActivity(intent);
            }
        }
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

        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
}

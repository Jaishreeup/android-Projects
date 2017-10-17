package witty.wittybus.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
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
import witty.wittybus.activity.showRoute;


public class Routeid extends android.support.v4.app.Fragment
{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ProgressDialog progress;
    private int mParam1;
    private int mParam2;

    public static android.support.v4.app.Fragment newInstance(int param2)
    {
        Routeid fragment = new Routeid();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Routeid() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //  mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }
    EditText rid;
    String result,r1;
    int k;
    Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.activity_bus_route_id, container, false);
        rid=(EditText)v.findViewById(R.id.et);
        btn=(Button)v.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    r1 = ""+rid.getText();
                    k = Integer.parseInt(r1);
                    progress = new ProgressDialog(getActivity());
                    progress.setTitle("Searching route for this ID");
                    progress.setMessage("Please wait while loading...");
                    progress.show();
                    progress.setCancelable(false);

                    getroutes(v);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        return v;
    }

    public void getroutes(View view)throws InterruptedException {
        if (isConnected()) {

            new load().execute("http://wittybus.000webhostapp.com/sendroutes2.php");
        }
    }

    public boolean isConnected() {

        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }


    class load extends AsyncTask<String, Void, Void> {
        protected void onPreExecute() {
        }

        protected Void doInBackground(String... urls) {

            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(urls[0]);
            JSONObject json = new JSONObject();
            try {
                json.put("rid", k);
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
            System.out.println("tttttttttttt "+result+ " "+ k);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void unused) {
            progress.dismiss();
            if(result.length()<6)
                Toast.makeText(getActivity()," Sorry wrong route ID",Toast.LENGTH_LONG).show();
            else
            {
                Intent intent = new Intent(getActivity(), showRoute.class);
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
}

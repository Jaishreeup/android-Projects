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


public class Routeloc extends android.support.v4.app.Fragment {
    private static final String ARG_PARAM2 = "param2";

    private int mParam1;
    private int mParam2;

    public static android.support.v4.app.Fragment newInstance(int param2) {
        Routeloc fragment = new Routeloc();
        System.out.println("aaaaaaaaaaaaaaaa");
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Routeloc() {
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
    EditText src,dest,rid;
    String result,s1,s2;
    Button btn;
    ProgressDialog progress;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.activity_bus_route_loc, container, false);
        src=(EditText)v.findViewById(R.id.src);
        dest=(EditText)v.findViewById(R.id.dest);
        btn=(Button)v.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    s1 = ""+src.getText();
                    s2 = ""+dest.getText();
                    progress = new ProgressDialog(getActivity());
                    progress.setTitle("Loading");
                    progress.setMessage("Wait while loading...");
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
        System.out.println("oooooooo");
        if(isConnected()) {
            new load().execute("http://wittybus.000webhostapp.com/sendroutes.php");
        }
        // else Toast.makeText(, "internet not available", Toast.LENGTH_LONG).show();
    }

    public boolean isConnected() {

        System.out.println("eeeeeeee");
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
            System.out.println("just checcccc");
            try {
                // Add your data
                json.put("src", s1);
                json.put("dest", s2);
                StringEntity se = new StringEntity(json.toString());
                httppost.setEntity(se);
                // Execute HTTP Post Request

                // 7. Set some headers to inform server about the type of the content
                httppost.setHeader("Accept", "application/json");
                httppost.setHeader("Content-type", "application/json");
                System.out.println("yooooo");

                // 8. Execute POST request to the given URL
                HttpResponse httpResponse = httpclient.execute(httppost);
                System.out.println("llllleeeee");
                InputStream inputStream = httpResponse.getEntity().getContent();

                // convert inputstream to string
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
            return null;
        }

        protected void onPostExecute(Void unused) {
            progress.dismiss();
            if(result.length()<6)
                Toast.makeText(getActivity(),"Sorry no route available between these locations",Toast.LENGTH_LONG).show();
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

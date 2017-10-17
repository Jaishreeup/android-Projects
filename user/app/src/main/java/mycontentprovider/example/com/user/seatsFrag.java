package mycontentprovider.example.com.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link seatsFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link seatsFrag#newInstance} factory method to
 * create an instance of this fragment.
 */
public class seatsFrag extends android.support.v4.app.Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private int mParam2;

   // private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment seatsFrag.
     */
    // TODO: Rename and change types and number of parameters
    public static android.support.v4.app.Fragment newInstance(int param2) {
        seatsFrag fragment = new seatsFrag();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    EditText et;
    SharedPreferences sp;
    static  int available;

    public seatsFrag() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
           // mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }
Button btn;
    EditText src,dest;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.activity_seat_status, container, false);
        sp=getActivity().getSharedPreferences("user", Context.MODE_PRIVATE);
        et= (EditText)v.findViewById(R.id.et);
        src=(EditText)v.findViewById(R.id.src);
        dest=(EditText)v.findViewById(R.id.dest);
        btn=(Button)v.findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new load().execute();
            }
        });
        return v;
    }
    class load extends AsyncTask<String, Void, Void> {
        protected void onPreExecute() {
        }
        String result;

        protected Void doInBackground(String... urls) {
         //   final String bid = "" + et.getText();
            if (isConnected()) {
                System.out.println("conneeeeeeee");

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://abesec.in/witty/final.php");
                JSONObject json = new JSONObject();
                System.out.println("hereeeeeeee");
                try {
                    // Add your data
                    json.put("src",src.getText());
                    json.put("dest",dest.getText());
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

                    System.out.println("this issss: " + result);
                    //result="[\"available\":\"48\"]";
                 //   JSONArray j = new JSONArray(result);
                    System.out.println("hhhhhhhhhhhhh");

                    //available = (int) j.get("available");
                   // System.out.println("avaaaa : " + available);
                   // sp.edit().putInt("available", available).commit();
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

        protected void onPostExecute(Void unused) {
           // Toast.makeText(getActivity(), "Available Seats: " + sp.getInt("available", 60), Toast.LENGTH_LONG).show();

              Intent intent = new Intent(getActivity(), SeatStatus.class);
            intent.putExtra("json", result);
            startActivity(intent);
        }

        //

        // else Toast.makeText(this,"internet not available",Toast.LENGTH_LONG).show();
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
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }
}

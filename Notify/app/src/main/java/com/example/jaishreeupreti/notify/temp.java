/*package com.example.jaishreeupreti.notify;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by JAISHREE UPRETI on 03-08-2015.


        import android.annotation.TargetApi;
        import android.app.Activity;
        import android.app.ActionBar;
        import android.app.Fragment;
        import android.app.ProgressDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.net.ConnectivityManager;
        import android.net.NetworkInfo;
        import android.os.AsyncTask;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.AdapterView;
        import android.widget.AdapterView.OnItemClickListener;
        import android.widget.ArrayAdapter;
        import android.widget.BaseAdapter;
        import android.widget.ImageView;
        import android.widget.ListAdapter;
        import android.widget.ListView;
        import android.widget.TextView;
        import android.widget.Toast;
        import android.os.Build;

        import org.apache.http.client.ClientProtocolException;
        import org.apache.http.client.HttpClient;
        import org.apache.http.client.ResponseHandler;
        import org.apache.http.client.methods.HttpGet;
        import org.apache.http.impl.client.BasicResponseHandler;
        import org.apache.http.impl.client.DefaultHttpClient;
        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.io.IOException;
        import java.net.MalformedURLException;
        import java.net.URL;
        import java.util.ArrayList;

public class temp extends Activity {
    ListView lv;
    Context ctx;
    SharedPreferences sp;
    preferenceDB db;
    ListAdapter la;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        lv = (ListView) findViewById(R.id.listView1);
        ctx = this;
        db = new preferenceDB(ctx);
        sp = getSharedPreferences("notify", Context.MODE_PRIVATE);
        if(sp.getString("done","no").equals("no")) {
            sp.edit().putBoolean("isDatabaseLoaded", false).commit();
            sp.edit().putString("done", "y");
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                // TODO Auto-generated method stub
//				final TextView NotificationId = (TextView) arg1.findViewById(R.id.textView6);
                if (isConnected()) {
                    new AsyncTask<String, String, String>() {

                        private ProgressDialog Dialog = new ProgressDialog(MainScreen.this);
                        String Content;

                        protected void onPreExecute() {

                            Dialog.setTitle("Loading...");
                            Dialog.setMessage("Please wait...");
                            Dialog.show();
                        }

                        ;

                        @Override
                        protected String doInBackground(String... params) {

                            HttpGet httpget = new HttpGet("https://itunes.apple.com/lookup?id=" + NotificationId.getText().toString());
                            ResponseHandler<String> responseHandler = new BasicResponseHandler();
                            HttpClient Client = new DefaultHttpClient();
                            try {
                                Content = Client.execute(httpget, responseHandler);
                                System.out.println(Content);
                            } catch (ClientProtocolException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                            return null;
                        }

                        protected void onPostExecute(String result) {
                            Dialog.dismiss();
                            Intent i = new Intent(MainScreen.this, NotificationDetail.class);
                            i.putExtra("detail", Content);
                            startActivity(i);

                        }

                        ;
                    }.execute("");
                } else
                    Toast.makeText(ctx, "Network connection not available", Toast.LENGTH_SHORT).show();

            }
        });

        if (sp.getBoolean("isDatabaseLoaded", false)) {
            String[] Choices =db.getArrayList();

            ArrayAdapter<String> adapter;
            adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, android.R.id.text1, Choices);
            lv.setAdapter(adapter);
            Log.d("yahoooo", "hello");
            Log.d("spppp",""+sp.getBoolean("isDatabaseLoaded", false));

        } else {
            if (isConnected())
            {
                Log.d("yahape","hello");
                new LoadTask().execute("https://10.0.2.2:8080/MyServerProject/fetchNotification");
            }

		/*	else {

				Toast.makeText(ctx, "Network connection not available", Toast.LENGTH_LONG).show();

				finish();


			}
		}
    }
    /*
        String[] Choices = new String[]
                {
                        "Notifications from your preferred Departments",
                        "Notifications from your preferred Clubs",
                        "Notifications from your other preferred sources",
                        "Notifications Manatory for all"
                };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, Choices);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new

        OnItemClickListener() {
            public void onItemClick (AdapterView < ? > parent, View view,
            int position, long id){

            }

        }

        );

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.sync:

                if (isConnected()) {
                    new LoadTask().execute("https://10.0.2.2:8080/MyServerProject/fetchNotification");
                } else {
                    Toast.makeText(ctx, "Network connection not available", Toast.LENGTH_LONG).show();
                }


                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private class LoadTask extends AsyncTask<String, Void, Void> {

        private final HttpClient Client = new DefaultHttpClient();
        private String Content;
        int success = 0;
        private ProgressDialog Dialog = new ProgressDialog(MainScreen.this);

        protected void onPreExecute() {


            Dialog.setCancelable(false);
            Dialog.setTitle("Loading...");
            Dialog.setMessage("Please wait...");
            Dialog.show();
        }


        @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
        protected Void doInBackground(String... urls) {
            try {


                HttpGet httpget = new HttpGet(urls[0]);
                ResponseHandler<String> responseHandler = new BasicResponseHandler();
                Content = Client.execute(httpget, responseHandler);

                System.out.println("JSON fetched---------------- " + Content);

                try {


                    JSONObject j = new JSONObject(Content);
                    JSONArray json = j.getJSONArray("results");

                    if (sp.getBoolean("isDatabaseLoaded", false))
                        ctx.deleteDatabase(db.getDatabaseName());

                    for (int i = 0; i < json.length(); i++) {
                        JSONObject jo = json.getJSONObject(i);
                        db.addRecord(jo.getString("list"),jo.getString("subcategory"));
                        //db.addRecord(jo.getString("organiser"), jo.getString("eventname"), jo.getString("date"), jo.getString("info"), jo.getString("issuer"), jo.getString("id"));
                    }

                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }


                success = 1;

            } catch (ClientProtocolException e) {
                e.printStackTrace();
                cancel(true);
            } catch (IOException e) {
                e.printStackTrace();
                cancel(true);
            }

            return null;
        }

        protected void onPostExecute(Void unused) {

            Dialog.dismiss();

            if (success == 1) {
                Log.d("jayaa", "" + sp.getBoolean("isDatabaseLoaded", false));

                sp.edit().putBoolean("isDatabaseLoaded", true).commit();
                ArrayList<String[]> Choices =db.getArrayList();

                ArrayAdapter<String> adapter;
                adapter = new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1, android.R.id.text1, Choices);
                lv.setAdapter(adapter);

            }
        }

    }

	public class ListAdapter extends BaseAdapter {
		final Context context;
		preferenceDB db;
		ProgressDialog dialog;
		ArrayList<String[]> preferences = new ArrayList<String[]>();



		public ListAdapter(Context context) {
			super();
			this.context = context;
			db=new preferenceDB(context);
			preferences=db.getArrayList();

		}
/*
		@Override
		public View getView(final int position, View v, ViewGroup parent) {
			Log.d("viewww","yup");
			if(v==null)
			{

				LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

				v = inflater.inflate(R.layout.track_list_item, parent, false);
				viewholder.artwork = (ImageView) v.findViewById(R.id.imageView1);
				v.setTag(viewholder);
			}
			else{
				viewholder=(MyViewholder)v.getTag();
			}
			final TextView organiser = (TextView) v.findViewById(R.id.textView1);
			final TextView event = (TextView) v.findViewById(R.id.textView2);
			final TextView date = (TextView) v.findViewById(R.id.textView3);
			final TextView info = (TextView) v.findViewById(R.id.textView4);
			final TextView issuer= (TextView) v.findViewById(R.id.textView5);
			final TextView id = (TextView) v.findViewById(R.id.textView6);

			Log.d("viewww","yupa");

			final MyViewholder finalViewholder = viewholder;
			new AsyncTask<String, String, String>(){



				Bitmap bmp;
				protected void onPreExecute() {


				};
				@Override
				protected String doInBackground(String... params) {


					URL url=null;
					try {


						url = new URL(notifications.get(position)[5]);
						bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());


					}  catch (MalformedURLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					return null;
				}
				protected void onPostExecute(String result) {

					finalViewholder.artwork.setImageBitmap(bmp);
				};
			}.execute("");

			organiser.setText("Organizer: "+notifications.get(position)[0]);
			event.setText("Event name: "+notifications.get(position)[1]);
			date.setText("Date: "+notifications.get(position)[2]);
			info.setText("info: "+notifications.get(position)[3]);
			issuer.setText("issued by: "+notifications.get(position)[4]);
			id.setText(notifications.get(position)[5]);



			return v;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return preferences.size();
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return null;
		}


	}

    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected())
            return true;
        else
            return false;
    }

}



*/
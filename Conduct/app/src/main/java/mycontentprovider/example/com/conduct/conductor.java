package mycontentprovider.example.com.conduct;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class conductor extends Activity {
	Spinner src,dest,jny;
	EditText date,qty,bid;
	Button submit;
	routeDB db;
	int available=65,arrived,dept[];
	Map map=new HashMap<String,Integer>();
	SharedPreferences sp;
	int k;

	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.fragment_main);
			startService(new Intent(this, sendData.class));
			sp = getSharedPreferences("conduct", Context.MODE_PRIVATE);
			db = new routeDB(getApplicationContext());
			//Boolean ans=sp.getBoolean("dataloaded",false);
			/*if(!ans) {
				db.addRecord("1", "5", "A B C D E");
				db.addRecord("2", "6", "F G H I J K");
				db.addRecord("3", "4", "L M N O");
				sp.edit().putBoolean("dataloaded",true).commit();
			}*/
			String valbid = getIntent().getExtras().get("bid").toString();

			sp.edit().putString("bid", valbid).commit();
			src = (Spinner) findViewById(R.id.editText2);
			dest = (Spinner) findViewById(R.id.editText3);
			jny = (Spinner) findViewById(R.id.journey);
			date = (EditText) findViewById(R.id.editText6);
			qty = (EditText) findViewById(R.id.editText4);
			bid = (EditText) findViewById(R.id.editText1);
			bid.setText(valbid);
			int size = Integer.parseInt(db.getStops(valbid));
			dept = new int[size];

			String result = db.getRoute(valbid);
			final String route[] = result.split(","), journey[] = {"forward", "return"};
			final int len = route.length;
			for (int i = 0; i < len; i++) {
				map.put(route[i], i);
				dept[i] = 0;
			}

			submit = (Button) findViewById(R.id.button1);
			submit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					int stop = (int) map.get(src.getSelectedItem());
					int destStop = (int) map.get(dest.getSelectedItem());
					arrived = Integer.parseInt("" + qty.getText());
					dept[destStop] += arrived;
					available = available + dept[stop] - arrived;
					dept[stop] = 0;
					Toast.makeText(getApplicationContext(), "available seats: " + available, Toast.LENGTH_LONG).show();
					sp.edit().putInt("available", available).commit();
				}
			});

			final ArrayAdapter<CharSequence> adapterj = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item, journey);
			adapterj.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			jny.setAdapter(adapterj);
			jny.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
				String retroute[];
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					if (position == 0) {
						final ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_spinner_item, route);
						adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						src.setAdapter(adapter);
						src.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
							String nroute[];

							@Override
							public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
								for (int i = 0; i < len; i++) {

									System.out.println("lai yahan hu" + position);
									if (i == position) {
										System.out.println("yyyy yahan hu" + i);

										nroute = new String[len - i];
										k = 0;
										for (int j = i; j < len; j++) {
											nroute[k++] = route[j];
											//		System.out.println("routttt "+nroute[k-1]);
										}
										break;
									}
								}
								ArrayAdapter<CharSequence> adapter1 = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_spinner_item, nroute);
								adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
								dest.setAdapter(adapter1);
							}

							@Override
							public void onNothingSelected(AdapterView<?> parent) {
								dest.setAdapter(adapter);
							}
						});

					} else

					{
						k = 0;
						retroute = new String[len];
						for (int i = len - 1; i >= 0; i--) {
							retroute[k++] = route[i];
						}
						final ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_spinner_item, retroute);
						adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
						src.setAdapter(adapter);
						src.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
							String nroute[];

							@Override
							public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
								for (int i = 0; i < len; i++) {

									System.out.println("lai yahan hu" + position);
									if (i == position) {
										System.out.println("yyyy yahan hu" + i);

										nroute = new String[len - i];
										k = 0;
										for (int j = i; j < len; j++) {
											nroute[k++] = retroute[j];
											//		System.out.println("routttt "+nroute[k-1]);
										}
										break;
									}
								}
								ArrayAdapter<CharSequence> adapter1 = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_spinner_item, nroute);
								adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
								dest.setAdapter(adapter1);
							}

							@Override
							public void onNothingSelected(AdapterView<?> parent) {
								dest.setAdapter(adapter);
							}
						});


					}
				}


				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}

			});

		}}
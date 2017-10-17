package com.example.jaishreeupreti.callblocker;


import java.lang.reflect.Method;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import com.android.internal.telephony.ITelephony;

	public class CallBlockerService extends Service {

		SharedPreferences sp;
		BroadcastReceiver Callreceiver;
		String phones;
		String incomingnumber = "";

		@Override
		public IBinder onBind(Intent arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void onCreate() {
			sp = getSharedPreferences("callblocker", Context.MODE_PRIVATE);
			Log.d("service","yoooo");
			// System.out.println("2"+phones);
			/*
			 * sl=sl.replace("[", "").replace("]", "").replaceAll(" ", ""); int b=0;
			 * 
			 * 
			 * 
			 * for(int a=0;a<sl.length();a++){
			 * 
			 * if(sl.charAt(a)==',') {
			 * 
			 * phones.add(sl.substring(b, a)); b=a+1; }
			 * 
			 * }
			 */

			//Toast.makeText(getBaseContext(), "Call Blocker enabled", Toast.LENGTH_SHORT).show();

			Callreceiver = new BroadcastReceiver() {

				@Override
				public void onReceive(Context context, Intent intent) {

					phones = sp.getString("blocklist", "");

				//	Toast.makeText(getBaseContext(), "phns"+phones, Toast.LENGTH_LONG).show();

					TelephonyManager telephonyManager = (TelephonyManager) context
							.getSystemService(Context.TELEPHONY_SERVICE);
					try {
						// String action= intent.getAction();
						// if(action.equals(TelephonyManager.ACTION_PHONE_STATE_CHANGED)){

						if (intent.getStringExtra(TelephonyManager.EXTRA_STATE)
								.equals(TelephonyManager.EXTRA_STATE_RINGING)) {

							incomingnumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);

							//Toast.makeText(context,
							// "Incoming call from: "+incomingnumber,
							 //Toast.LENGTH_SHORT).show();

							if (phones.contains(incomingnumber.substring(incomingnumber.length() - 10, 10))) {
								try {
									
									Class c = Class.forName(telephonyManager.getClass().getName());

									Method m = c.getDeclaredMethod("getITelephony");

									m.setAccessible(true);

									ITelephony telephonyService = (ITelephony) m.invoke(telephonyManager);

									telephonyService.endCall();

								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									//Toast.makeText(context,
										//	 "error"+e.toString(),
											// Toast.LENGTH_LONG).show();
									e.printStackTrace();
								}

							}
						}

					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			};

			IntentFilter filter = new IntentFilter();
			filter.addAction("android.intent.action.PHONE_STATE");
			this.registerReceiver(Callreceiver, filter);

		}

		public int onStartCommand()
		{
			return START_STICKY;
		}
		@Override
		public void onDestroy() {
			super.onDestroy();

			if (Callreceiver != null) {
				this.unregisterReceiver(Callreceiver);
			}
//			Toast.makeText(getBaseContext(), "Call Blocker disabled", Toast.LENGTH_SHORT).show();

		}

	}



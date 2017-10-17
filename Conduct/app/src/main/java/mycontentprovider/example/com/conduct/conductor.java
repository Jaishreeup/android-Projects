
  package mycontentprovider.example.com.conduct;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static mycontentprovider.example.com.conduct.functions.hideSoftKeyboard;

public class conductor extends Activity {
    Spinner src, dest, jny;
    EditText qty;
    TextView date, bid;
    Button submit;
    routeDB db;
    RelativeLayout sv;
    int available = 65, arrived, dept[];
    Map map = new HashMap<String, Integer>();
    SharedPreferences sp;
    int k;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.fragment_main);
        startService(new Intent(this, sendData.class));
        sp = getSharedPreferences("conduct", Context.MODE_PRIVATE);
        db = new routeDB(getApplicationContext());
        String valbid = getIntent().getExtras().get("bid").toString();

        sp.edit().putString("bid", valbid).commit();
        src = (Spinner) findViewById(R.id.editText2);
        dest = (Spinner) findViewById(R.id.editText3);
        jny = (Spinner) findViewById(R.id.journey);
        date = (TextView) findViewById(R.id.date);
        qty = (EditText) findViewById(R.id.editText4);
        bid = (TextView) findViewById(R.id.busid);
        bid.setText("Bus-ID:  " + valbid);
        qty.setHintTextColor(Color.WHITE);
        int size = Integer.parseInt(db.getStops(valbid));
        dept = new int[size];
        String result = db.getRoute(valbid);
        final String route[] = result.split(","), journey[] = {"Forward", "Return", "Not Moving"};
        final int len = route.length;
        for (int i = 0; i < len; i++) {
            map.put(route[i], i);
            dept[i] = 0;
        }
        String curdate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        date.setText("Date:    " + curdate);
        sv = (RelativeLayout) findViewById(R.id.rl);
        sv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent arg1) {
                qty.clearFocus();

                if (qty.isFocused()) {
                    hideSoftKeyboard(conductor.this);
                }
                return false;
            }
        });
        submit = (Button) findViewById(R.id.button1);
        submit.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {

                int stop = (int) map.get(src.getSelectedItem());
                int destStop = (int) map.get(dest.getSelectedItem());
                arrived = Integer.parseInt("" + qty.getText());
                dept[destStop] += arrived;
                int i = 0, sum = 0;
                if(jny.getSelectedItemPosition()==0)
                {
                    while (!route[i].equals(src.getSelectedItem())) {
                        sum += dept[i];
                        System.out.println("rukkk gaya " + dept[i] + "  " + sum);
                        dept[i++] = 0;
                    }
                }
                else if(jny.getSelectedItemPosition()==1)
                {
                    while (!route[len-i-1].equals(src.getSelectedItem())) {
                        sum += dept[len-i-1];
                        System.out.println("rukkk gaya " + dept[len-i-1] + "  " + sum);
                        dept[len-i-1] = 0;
                        i++;
                    }

                }

                available = available + sum + dept[stop] - arrived;
                dept[stop] = 0;
                Toast.makeText(getApplicationContext(), "available seats: " + available+" "+sum, Toast.LENGTH_LONG).show();
                sp.edit().putInt("available", available).commit();
            }
        });

        final ArrayAdapter<CharSequence> adapterj = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_spinner_item, journey);
        adapterj.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        jny.setAdapter(adapterj);
        jny.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            String retroute[];

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                // ((TextView) parent.getChildAt(1)).setTextColor(Color.WHITE);
                //((TextView) parent.getChildAt(2)).setTextColor(Color.WHITE);

                if (position == 0) {
                    final ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(getApplicationContext(), android.R.layout.simple_spinner_item, route);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    src.setAdapter(adapter);
                    sp.edit().putString("fr", "U").commit();
                    src.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        String nroute[];

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            for (int i = 0; i < len; i++) {

                                if (i == position) {
                                    nroute = new String[len - i];
                                    k = 0;
                                    for (int j = i; j < len; j++) {
                                        nroute[k++] = route[j];
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

                } else if (position == 1) {
                    k = 0;
                    sp.edit().putString("fr", "D").commit();

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

    }

}
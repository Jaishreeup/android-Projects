package com.example.jaishreeupreti.stopseva;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Spinner;


public class MainActivity extends ActionBarActivity {

    Spinner spinner1,spinner2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner1=(Spinner)findViewById(R.id.spinner1);
        spinner2=(Spinner)findViewById(R.id.spinner2);

        spinner1.setOnItemSelectedListener(new AdapterView<?>().OnItemSelectedListener() {

            @Override
            onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                YourAdapter adapter = (YourAdapter) spinner2.getAdapter();

                switch (position) {
                    case COUNTRY_ONE:
                        adapter.setData(resources.getArray(R.array.cities_you_need_1));
                        break;

                    case COUNTRY_TWO:
                        adapter.setData(resources.getArray(R.array.cities_you_need_2));
                        break;
                }
            }

            @Override
            onNothingSelected(AdapterView<?> parent) {
                ((YourAdapter) spinner2.getAdapter()).clear();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

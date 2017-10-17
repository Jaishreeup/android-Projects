package witty.wittybus.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import witty.wittybus.R;
import witty.wittybus.adapter.RVAdapter1;


public class SeatStatus extends ActionBarActivity
{
    private RecyclerView mRecyclerView;
    public RVAdapter1 mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<String[]> routes=new ArrayList<String[]>();
    String[] list;
    String result;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_availability);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        result=getIntent().getStringExtra("json");
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        try {
            JSONArray arr = new JSONArray(result),ar;
            int len = arr.length();
            for (int i = 0; i < len; i++) {
                list=new String[4];
                ar = (JSONArray)arr.get(i);
                list[0] = ar.get(0).toString();
                list[1] = ar.get(1).toString();
                list[2]=ar.get(2).toString();
                list[3]=ar.get(3).toString();
                routes.add(list);
            }
            Collections.sort(routes, new Comparator<String[]>() {
                public int compare(String[] strings, String[] otherStrings) {
                    return Integer.compare(Integer.parseInt(strings[3]),Integer.parseInt(otherStrings[3]));
                }
            });
            mAdapter = new RVAdapter1(routes,getApplicationContext());
            mRecyclerView.setAdapter(mAdapter);

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_show_route, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

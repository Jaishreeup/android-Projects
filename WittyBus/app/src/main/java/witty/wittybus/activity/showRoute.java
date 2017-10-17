package witty.wittybus.activity;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import witty.wittybus.R;
import witty.wittybus.adapter.RVAdapter;


public class showRoute extends ActionBarActivity {
    private RecyclerView mRecyclerView;
    public RVAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<String[]> routes=new ArrayList<String[]>();
    String[] list;
    String result;
    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_route);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);
        System.out.println("mai  haaaaa");
        mLayoutManager = new GridLayoutManager(getApplicationContext(),1);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));

        result=getIntent().getStringExtra("json");
        System.out.println("yeeeeeeeee  "+result);

        try {
            JSONArray arr = new JSONArray(result),ar;
            JSONObject temp;

            //JSONArray arr = new JSONArray(j), ar;
            int len = arr.length();
            for (int i = 0; i < len; i++) {
                System.out.println("hhhhhhhhhhhhh");
                list=new String[4];
                ar = (JSONArray)arr.get(i);
                //ar = new JSONArray(temp);
                list[0] = ar.get(0).toString();
                list[1] = ar.get(1).toString();
                list[2] = ar.get(2).toString();
                list[3] = ar.get(3).toString();
                System.out.println("hhhhhhhhhhhhh  "+list[0]+" "+list[3]);
                routes.add(list);

                System.out.println("cheeeeeccc " );// routes.get(i)[0]);

            }

            mAdapter = new RVAdapter(routes,getApplicationContext());
            System.out.println("llllllllllll");
            //mAdapter.setOnLongClickListener(this);
            mRecyclerView.setAdapter(mAdapter);

        } catch (JSONException e) {
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

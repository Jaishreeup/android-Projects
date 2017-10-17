package mycontentprovider.example.com.user;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class routeInfo extends ActionBarActivity {
String[] routes;
    TextView path,rno,src,dest;
ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_info);
        path=(TextView)findViewById(R.id.path);
        rno=(TextView)findViewById(R.id.rno);
        src=(TextView)findViewById(R.id.src);
        dest=(TextView)findViewById(R.id.dest);
        routes=getIntent().getStringArrayExtra("details");
        String s=routes[1].replace(",","\n");
        path.setText(s);
        SpannableString si = new SpannableString("WITTY BUS TRACKER");
        si.setSpan(new TypefaceSpan("bold"), 0, si.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
       /*Typeface type=Typeface.createFromAsset(getApplicationContext().getAssets(),
                "fonts/MERCEDES.TTF");*/
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ea37dee0")));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#ea37dee0")));
        actionBar.setTitle(si);

        rno.setText(routes[0]);
        src.setText(routes[2]);
        dest.setText(routes[3]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_route_info, menu);
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

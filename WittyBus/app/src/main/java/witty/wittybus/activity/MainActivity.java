package witty.wittybus.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import witty.wittybus.R;
import witty.wittybus.fragments.Help;
import witty.wittybus.fragments.Home;
import witty.wittybus.fragments.Routeid;
import witty.wittybus.fragments.Routeloc;
import witty.wittybus.fragments.Seatloc;
import witty.wittybus.supportfiles.ConnectionDetector;
import witty.wittybus.supportfiles.Alert;
public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener
{

    NavigationView navigationView = null;
    Toolbar toolbar = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkconnection();
        //Set the fragment initially
        Home fragment = new Home();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);

        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        if (id == R.id.nav_seatlocation)
        {
            fragment = new Seatloc();
            title = "Seat Status";
        } else if (id == R.id.nav_routerid)
        {
            fragment = new Routeid();
            title = "Bus Routes";
        } else if (id == R.id.nav_routelocation)
        {
            fragment = new Routeloc();
            title = "Bus Routes";
        } else if (id == R.id.nav_facebook)
        {

        } else if (id == R.id.nav_twitter)
        {

        } else if (id == R.id.nav_googleplus)
        {

        } else if (id == R.id.nav_help)
        {
            fragment = new Help();
            title="Help";
        } else if (id == R.id.nav_quit)
        {
            android.os.Process.killProcess(android.os.Process.myPid());
        }

        if (fragment != null)
        {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.commit();
            if(getSupportActionBar()!=null)
                getSupportActionBar().setTitle(title);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void checkconnection()
    {
        ConnectionDetector conn = new ConnectionDetector(getApplicationContext());
        if (!conn.isConnectingToInternet())
        {
            String title    = "Network Error";
            String message  = "This device has limited/no connectivity. Either connect to a network or quit this application.";
            Alert alert = new Alert(this,title,message,this);
            alert.showNetworkAlert();
        }
    }
}

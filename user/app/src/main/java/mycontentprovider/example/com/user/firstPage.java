package mycontentprovider.example.com.user;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;


public class firstPage extends ActionBarActivity implements ActionBar.TabListener {
    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    String opcionesMenu[];
    DrawerLayout drawerLayout;
    ListView drawerList;
    int color;
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main1);
        color=1;
        SpannableString s = new SpannableString("WITTY BUS TRACKER");
        s.setSpan(new TypefaceSpan("bold"), 0, s.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
       /*Typeface type=Typeface.createFromAsset(getApplicationContext().getAssets(),
                "fonts/MERCEDES.TTF");*/
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ea96ea7f")));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#ea96ea7f")));
        actionBar.setTitle(s);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });


        actionBar.addTab(
                actionBar.newTab()
                        .setText("SEAT AVAILABILITY")
                        .setTabListener(this));
        actionBar.addTab(
                actionBar.newTab()
                        .setText("CHECK BUS ROUTE")
                        .setTabListener(this));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        super.onOptionsItemSelected(item);

        switch(item.getItemId()){
            case R.id.share:
                Toast.makeText(getBaseContext(), "share and help others to find their way!", Toast.LENGTH_SHORT).show();
                break;

            case R.id.rate:
                Toast.makeText(getBaseContext(), "please rate us", Toast.LENGTH_SHORT).show();
                break;

            case R.id.feedback:
                Toast.makeText(getBaseContext(), "please give us your feedback", Toast.LENGTH_SHORT).show();
                break;


        }
        return true;

    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
        if(color==1) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ea4eeaae")));
            actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#ea4eeaae")));
            mViewPager.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ead9f9e1")));
            color=2;
        }
        else
        {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ea37dee0")));
            actionBar.setStackedBackgroundDrawable(new ColorDrawable(Color.parseColor("#ea37dee0")));
            mViewPager.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ead7faf8")));
            color=1;
        }
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {

    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            if(position==1)
            return frag.newInstance(position + 1);
            else
                return seatsFrag.newInstance(position+1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }
/*
        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
                case 2:
                    return getString(R.string.title_section3).toUpperCase(l);
            }
            return null;
        }*/
    }

    /**
     * A placeholder fragment containing a simple view.
     */
/*    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";
        int s;
        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
  /*      public static PlaceholderFragment newInstance(int sectionNumber) {
            if(sectionNumber==1)
            frag fragment = new frag(sectionNumber);
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment(int sec) {
            s=sec;
        }
        EditText src,dest,rid;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView;
            if(s==1)
            {
                rootView=inflater.inflate(R.layout.activity_bus_route, container, false);
            }
            else
            {
                rootView=inflater.inflate(R.layout.activity_seat_status, container, false);
            }

            return rootView;
        }
*/    }



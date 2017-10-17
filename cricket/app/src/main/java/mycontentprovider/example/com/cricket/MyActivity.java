package mycontentprovider.example.com.cricket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;

public class MyActivity extends ActionBarActivity {
    private RecyclerView mRecyclerView;
    public RVAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    infodb db;
    ArrayList<String[]> persons;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler);
        sp=getSharedPreferences("cricket", Context.MODE_PRIVATE);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        db=new infodb(getApplicationContext());
        mRecyclerView.setHasFixedSize(true);
        System.out.println("mai  haaaaa");
        mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        int ans=sp.getInt("isdataloaded", 0);
        if(ans==0)
        {
            db.addRecord("1", "Virat Kohli", 26, "india", 6000, 250, 150);
            db.addRecord("2", "Sachin Tendulkar", 42, "india", 10000, 200, 150);
            db.addRecord("3", "M S Dhoni", 34, "india", 7000, 250, 170);
            db.addRecord("4", "Michael Clarke", 34, "australia", 6000, 100, 150);
            db.addRecord("5", "Shane Warne", 46, "australia", 5000, 150, 150);
            db.addRecord("6", "Joe Root", 24, "england", 4500, 150, 180);
            sp.edit().putInt("isdataloaded",1).commit();
        }
        System.out.println("ooooooooo");
        persons =db.getNames() ;
        System.out.println("looooooooooooo");
        mAdapter = new RVAdapter(persons,getApplicationContext());
        System.out.println("llllllllllll");
        //mAdapter.setOnLongClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
    }
    public void add(View view)
    {
        Intent intent=new Intent(this,form.class);
        startActivityForResult(intent, 1);
       // mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
    }
    public void filter(View view)
    {
        Intent intent=new Intent(this,filteer.class);
        startActivityForResult(intent, 2);
    }
    public void rfilter(View view) {

        persons =db.getNames() ;
        System.out.println("looooooooooooo");
        mAdapter = new RVAdapter(persons,getApplicationContext());
        System.out.println("llllllllllll");
        //mAdapter.setOnLongClickListener(this);
        mRecyclerView.setAdapter(mAdapter);

    }

        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String[] string = data.getStringArrayExtra("RESULT_STRING");
                System.out.println("stringggg  " + string[1]);
                mAdapter.add(string, Integer.parseInt(string[0]));
            }
        } else if (requestCode == 2) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String string = data.getStringExtra("RESULT_STRING");
                System.out.println("stringggg  " + string);
    //            mAdapter.add(string, Integer.parseInt(string[0]));
                persons =db.getArrayList(string) ;
                System.out.println("looooooooooooo");
                mAdapter = new RVAdapter(persons,getApplicationContext());
                System.out.println("llllllllllll");
                //mAdapter.setOnLongClickListener(this);
                mRecyclerView.setAdapter(mAdapter);

            }
        }
    }

}
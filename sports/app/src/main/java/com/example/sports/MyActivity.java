package com.example.sports;

import java.util.ArrayList;
import com.example.sports.Infodb;
import com.example.sports.MyAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MyActivity extends Activity {
	private RecyclerView mRecyclerView;
    public MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Infodb db;
    ArrayList<String[]> groups;
    SharedPreferences sp;
    Button add, filter;
    
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.recycler);
		sp=getSharedPreferences("sports", Context.MODE_PRIVATE);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        add=(Button)findViewById(R.id.button1);
        filter=(Button)findViewById(R.id.button2);
        
        db=new Infodb(getApplicationContext());
        mRecyclerView.setHasFixedSize(true);
        System.out.println("mai  haaaaa");
        mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
        mRecyclerView.setLayoutManager(mLayoutManager);
        int ans=sp.getInt("isdataloaded", 0);
        /*if(ans==0)
        {
            db.addRecord("1", "BasketBall", 2016-03-02, 04:30:00 ,3);
            db.addRecord("2", "Football", 2016-03-20, 06:00:00, 10);
            
            sp.edit().putInt("isdataloaded",1).commit();
        }*/
        
        System.out.println("ooooooooo");
        groups =db.getNames() ;
        System.out.println("looooooooooooo");
        mAdapter = new MyAdapter(groups,getApplicationContext());
        System.out.println("llllllllllll");
        //mAdapter.setOnLongClickListener(this);
        mRecyclerView.setAdapter(mAdapter);
	}
	
	 public void add(View view)
	    {
	        Intent intent=new Intent(this,CreateGroup.class);
	        startActivityForResult(intent, 1);
	       // mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
	    }
	    public void filter(View view)
	    {
	        Intent intent=new Intent(this,Filteer.class);
	        startActivityForResult(intent, 2);
	    }
	
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String[] string = data.getStringArrayExtra("RESULT_STRING");
                System.out.println("stringggg  " + string[1]);
               
            }
        } else if (requestCode == 2) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                String string = data.getStringExtra("RESULT_STRING");
                System.out.println("stringggg  " + string);
    //            mAdapter.add(string, Integer.parseInt(string[0]));
                groups =db.getArrayList(string) ;
                System.out.println("looooooooooooo");
                mAdapter = new MyAdapter(groups,getApplicationContext());
                System.out.println("llllllllllll");
                //mAdapter.setOnLongClickListener(this);
                mRecyclerView.setAdapter(mAdapter);

            }
        }
    }

	 
	
/*	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
}

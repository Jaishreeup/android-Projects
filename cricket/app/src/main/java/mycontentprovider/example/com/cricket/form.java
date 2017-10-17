package mycontentprovider.example.com.cricket;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class form extends ActionBarActivity {
EditText t1,t2,t3,t4,t5,t6;
   infodb db;
    int count;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);
        db=new infodb(getApplicationContext());
        sp=getSharedPreferences("cricket", Context.MODE_PRIVATE);
        count=sp.getInt("count",7);
        t1=(EditText)findViewById(R.id.textView3);
        t2=(EditText)findViewById(R.id.textView5);
        t3=(EditText)findViewById(R.id.textView7);
        t4=(EditText)findViewById(R.id.textView9);
        t5=(EditText)findViewById(R.id.textView11);
        t6=(EditText)findViewById(R.id.textView13);
    }
    public void save(View view)
    {
        System.out.println("inside save");
        if(""+t1.getText()==""||""+t2.getText()==""|| ""+t3.getText()==""||"" + t4.getText()==""|| "" + t5.getText()==""||""+ t6.getText()=="")
            Toast.makeText(getApplicationContext(),"Please fill all entries..",Toast.LENGTH_LONG).show();
       else {
           try
           {
               System.out.println("ye hai count :" + count);
               String str[]={"" + count, "" + t1.getText(), "" + t2.getText(), "" + t3.getText(), "" + t4.getText(),"" + t5.getText(),"" + t6.getText()};
               db.addRecord(str[0],str[1], Integer.parseInt(str[2]), str[3], Integer.parseInt(str[4]), Integer.parseInt(str[5]), Integer.parseInt(str[6]));
               sp.edit().putInt("count", ++count).commit();
            //finish();
               Intent intent=new Intent();
               intent.putExtra("RESULT_STRING",str);
               setResult(RESULT_OK, intent);
               finish();
           // Intent intent=new Intent(this,MyActivity.class);
            //startActivity(intent);
           }
           catch (Exception e)
           {
               Toast.makeText(getApplicationContext(),"Please fill all entries correctly.",Toast.LENGTH_LONG).show();
           }

        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
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

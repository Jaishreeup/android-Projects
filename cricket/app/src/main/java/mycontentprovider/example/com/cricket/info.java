package mycontentprovider.example.com.cricket;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class info extends ActionBarActivity {

    infodb db;
    TextView t1,t2,t3,t4,t5,t6;
    EditText et1,et2,et3,et4,et5,et6;
    String[] str;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        db=new infodb(getApplicationContext());
        id=(String)getIntent().getExtras().get("id");
        str=db.getAll(id);
        t1=(TextView)findViewById(R.id.textView3);
        t2=(TextView)findViewById(R.id.textView5);
        t3=(TextView)findViewById(R.id.textView7);
        t4=(TextView)findViewById(R.id.textView9);
        t5=(TextView)findViewById(R.id.textView11);
        t6=(TextView)findViewById(R.id.textView13);

        t1.setText(str[1]);
        t2.setText(str[2]);
        t3.setText(str[3]);
        t4.setText(str[4]);
        t5.setText(str[5]);
        t6.setText(str[6]);

    }
    public void editDetails(View  view)
    {
        setContentView(R.layout.activity_form);
        et1=(EditText)findViewById(R.id.textView3);
        et1.setKeyListener(null);
        et2=(EditText)findViewById(R.id.textView5);
        et3=(EditText)findViewById(R.id.textView7);
        et4=(EditText)findViewById(R.id.textView9);
        et5=(EditText)findViewById(R.id.textView11);
        et6=(EditText)findViewById(R.id.textView13);
        et1.setText(str[1]);
        et2.setText(str[2]);
        et3.setText(str[3]);
        et4.setText(str[4]);
        et5.setText(str[5]);
        et6.setText(str[6]);

    }
    public void save(View view) {
        System.out.println("inside save");
        if ("" + et1.getText() == "" || "" + et2.getText() == "" || "" + et3.getText() == "" || "" + et4.getText() == "" || "" + et5.getText() == "" || "" + et6.getText() == "")
            Toast.makeText(getApplicationContext(), "Please fill all entries..", Toast.LENGTH_LONG).show();
        else {
            try {
//                System.out.println("ye hai count :" + count);
                String str[] = {id, "" + et1.getText(), "" + et2.getText(), "" + et3.getText(), "" + et4.getText(), "" + et5.getText(), "" + et6.getText()};
                db.modifyRecord(str[0], str[1], Integer.parseInt(str[2]), str[3], Integer.parseInt(str[4]), Integer.parseInt(str[5]), Integer.parseInt(str[6]));
                Toast.makeText(this, "Changes saved successfully!", Toast.LENGTH_LONG).show();
                //Intent intent = new Intent();
                //intent.putExtra("RESULT_STRING", str);
                //setResult(RESULT_OK, intent);
                finish();
                // Intent intent=new Intent(this,MyActivity.class);
                //startActivity(intent);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Please fill all entries correctly.", Toast.LENGTH_LONG).show();
            }
        }
    }
        @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_info, menu);
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

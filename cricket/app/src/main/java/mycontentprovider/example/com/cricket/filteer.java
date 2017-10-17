package mycontentprovider.example.com.cricket;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;


public class filteer extends ActionBarActivity {
    infodb db;
    RadioGroup myRadioGroup1, myRadioGroup2, myRadioGroup3, myRadioGroup4, myRadioGroup5, myRadioGroup6;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filteer);
        db=new infodb(this);
        myRadioGroup1=(RadioGroup)findViewById(R.id.r1);
        myRadioGroup2=(RadioGroup)findViewById(R.id.r2);
        myRadioGroup3=(RadioGroup)findViewById(R.id.r3);
        myRadioGroup4=(RadioGroup)findViewById(R.id.r4);
      //  myRadioGroup5=(RadioGroup)findViewById(R.id.r5);
        myRadioGroup6=(RadioGroup)findViewById(R.id.r6);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_filteer, menu);
        return true;
    }
    public void apply(View view)
    {
        int index1,index2,index3,index4,index5,index6;
        RadioButton r1,r2,r3,r4,r6;
        String sql="select * from table1 where ";
        String[] str;
        r1=(RadioButton)(findViewById(myRadioGroup1.getCheckedRadioButtonId()));
        r2=(RadioButton)(findViewById(myRadioGroup2.getCheckedRadioButtonId()));
        r3=(RadioButton)(findViewById(myRadioGroup3.getCheckedRadioButtonId()));
        r4=(RadioButton)(findViewById(myRadioGroup4.getCheckedRadioButtonId()));
        r6=(RadioButton)(findViewById(myRadioGroup6.getCheckedRadioButtonId()));
        if(r1!=null)
        {
        //    System.out.println("r1: "+r1.getText().equals("less than 25"));
            if((r1.getText()).equals("less than 25"))
                sql+="age<25";
            else if(r1.getText().equals(">=25 and less than 40"))
                sql+="age>=25 and age<40";
            else  sql+="age>=40";
            sql+=" and ";
        }
        if(r2!=null)

        {
            if(r2.getText().equals("india"))
                sql+="country='india'";
            else if(r2.getText().equals("australia"))
                sql+="country='australia'";
            else  sql+="country='england'";
            sql+=" and ";
        };
        if(r3!=null)
        {
            if(r3.getText().equals("less than 2000"))
                sql+="runs<2000";
            else if(r3.getText().equals(">=2000 and less than 5000"))
                sql+="runs>=2000 and runs<5000";
            else  sql+="runs>=5000";
            sql+=" and ";
        }
        if(r4!=null)
        {
            if(r4.getText().equals("less than 100"))
                sql+="wickets<100";
            else if(r4.getText().equals(">=100 and less than 200"))
                sql+="wickets>=100 and wickets<200";
            else  sql+="wickets>=200";
            sql+=" and ";
        }
        if(r6!=null)
        {
            if(r6.getText().equals("less than 50"))
                sql+="catches<50";
            else if(r6.getText().equals(">=50 and less than 150"))
                sql+="catches>=50 and catches<150";
            else  sql+="catches>=150";
            sql+=" and ";
        }
        sql=sql.substring(0,sql.length()-4);
        Intent intent=new Intent();
       // str= new String[]{"" + r1.getText(),"" + r2.getText(),"" + r3.getText(),"" + r4.getText(),"" + r6.getText()};
        intent.putExtra("RESULT_STRING",sql);
        setResult(RESULT_OK, intent);

     //   db.getArrayList(1,""+r1.getText());
        finish();
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

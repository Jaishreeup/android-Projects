package mycontentprovider.example.com.mymoney;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;


public  class PlaceholderFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    Button sub;
    EditText e_name,amt;
    ImageButton contacts;
    moneyDB db;
    RadioGroup rd;
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
public void calculate() {
    int id = rd.getCheckedRadioButtonId();
    String name=""+e_name.getText();
    int amount=Integer.parseInt(""+amt.getText());
    if (!db.recordExists(name )) {
        if(id==R.id.g1)
            db.addRecord(name,amount);
else        db.addRecord(name,-amount);
    }
    else {
        int sum = db.getMoney(name);
        if (id == R.id.g1)
            sum += amount;
        else sum -= amount;
        db.setMoney(name, sum);
    }
}

    public PlaceholderFragment() {
    }
    public void pickContacts(View v) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
        startActivityForResult(intent, 0);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri uri = data.getData();


            if (uri != null) {
                Cursor c = null;
                try {
                    c = getActivity().getContentResolver().query(
                            uri, new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,
                                    //new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER,
                                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME}, null, null, null);   // null, null, null);

                    if (c != null && c.moveToFirst()) {
                        //   String number = c.getString(0);
                        String name = c.getString(1);
                       // String temp = e_name.getText().toString();
                        // String name=c.getString(2);
                        e_name.setText(name);
                    }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
            }
        }
    }
            @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        e_name=(EditText)rootView.findViewById(R.id.line);
                db=new moneyDB(getActivity());
                rd=(RadioGroup)rootView.findViewById(R.id.grp);
        amt=(EditText)rootView.findViewById(R.id.amt);
                sub=(Button)rootView.findViewById(R.id.sub);
                e_name.setKeyListener(null);
                contacts=(ImageButton)rootView.findViewById(R.id.contacts);
                contacts.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        pickContacts(v);
                    }
                });
sub.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        calculate();
        e_name.setText("");
        amt.setText("");
        Toast.makeText(getActivity(),"DONE !!",Toast.LENGTH_SHORT).show();
    }
});
                return rootView;
    }
}

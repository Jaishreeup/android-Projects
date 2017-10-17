package mycontentprovider.example.com.mymoney;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Map;
import java.util.Set;


public  class debt extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    moneyDB db;
    ListView listView,listView1;
    private static final String ARG_SECTION_NUMBER = "section_number";

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static debt newInstance(int sectionNumber) {
        debt fragment = new debt();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public debt() {
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        db=new moneyDB(getActivity());
        Map map=db.getDebt();
        System.out.println("paaaaaaaaa ");

        int ukk = map.size();
        final String notList[]=new String[map.size()];
        final String notList1[]=new String[map.size()];
        Set set=map.keySet();
        int i=0;
        for(Object key: set) {

            notList[i] =(String)key;
          int val=-1*(int)map.get(key);
            notList1[i++] =""+val;
        }
        View rootView = inflater.inflate(R.layout.fragment_lent, container, false);
        listView=(ListView)rootView.findViewById(R.id.lv);
        listView1=(ListView)rootView.findViewById(R.id.lv1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.my_textview, R.id.tv, notList);
        listView.setAdapter(adapter);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.my_textview, R.id.tv, notList1);
        listView1.setAdapter(adapter1);

        return rootView;
    }
}

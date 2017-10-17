package com.example.jaishreeupreti.notice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AbtApp extends android.support.v4.app.Fragment {
    private static final String ARG_PARAM2 = "param2";
    private int mParam1;
    private int mParam2;
    public AbtApp() {
        // Required empty public constructor
    }

    public static android.support.v4.app.Fragment newInstance() {
        AbtApp fragment = new AbtApp();
        System.out.println("aaaaaaaaaaaaaaaa");
        Bundle args = new Bundle();
//        args.putInt(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            //  mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getInt(ARG_PARAM2);
        }
    }  @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.activity_abt_app, container, false);
//        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MERCEDES.TTF");
        TextView view = (TextView) v.findViewById(R.id.textView1);
//        view.setTypeface(typeFace);
        view = (TextView) v.findViewById(R.id.textview);
        String str = "Notifier is an app which can be used to view the college notices from anywhere, anytime.\nNow no need to check the notice boards daily because you'll automatically be notified about any new notices from your set preferences.\n\nHave a wonderful experience with your handy notice board!!";
        view.setText(str);

        return v;
    }

}

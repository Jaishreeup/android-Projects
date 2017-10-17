package com.example.jaishreeupreti.notice;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class AbtDev extends android.support.v4.app.Fragment {
    private static final String ARG_PARAM2 = "param2";
    private int mParam1;
    private int mParam2;
    public AbtDev() {
        // Required empty public constructor
    }

    public static android.support.v4.app.Fragment newInstance() {
        AbtDev fragment = new AbtDev();
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
        View v = inflater.inflate(R.layout.activity_abt_dev, container, false);
//        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/MERCEDES.TTF");
        TextView view = (TextView) v.findViewById(R.id.title);
//        view.setTypeface(typeFace);
        view = (TextView) v.findViewById(R.id.texthead);
        String str = "This application is developed by B.Tech Final year students CSE Branch.";
        view.setText(str);
        return v;
    }

}

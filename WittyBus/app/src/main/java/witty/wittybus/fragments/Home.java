package witty.wittybus.fragments;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import witty.wittybus.R;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Home extends Fragment
{
    WebView mWebView;
    public Home() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        mWebView = (WebView) rootView.findViewById(R.id.webView1);
        String text="Our project deals with daily problems of bus travellers i.e. seat availability," +
                " time of the bus and the bus route. The main focus of the application is to " +
                "handle lack of information about the seats in arriving buses." +
                "<br/><br/>Our aim is to make the  lives of  bus passengers easier by providing them a means to have " +
                "the idea of number of vacant seats in the arriving buses as well as expected time of arrival " +
                "and the bus route that can prevent them from getting into unnecessary trouble.";
        String texty = "<html><body>"
                + "<p align=\"justify\">"
                + text
                + "</p> "
                + "</body></html>";

        mWebView.loadData(String.format("%s", texty), "text/html", "utf-8");
        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
package com.example.jaishreeupreti.notice;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.widget.TextView;

import java.io.File;

public class pdfActivity extends Activity {

    TextView tv_loading;
    float per = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tv_loading = new TextView(this);
        setContentView(tv_loading);
        tv_loading.setGravity(Gravity.CENTER);
        tv_loading.setTypeface(null, Typeface.BOLD);
        String name = (String) getIntent().getExtras().get("fname");
        File externalFile = new File(Environment.getExternalStorageDirectory() + "/notifier/", name);
        Uri external = Uri.fromFile(externalFile);
        openPDF(external);
    }

    void openPDF(final Uri path) {
        new Thread(new Runnable() {
            public void run() {
                try {

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, "application/pdf");
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } catch (ActivityNotFoundException e) {
                    tv_loading
                            .setError("PDF Reader application is not installed in your device");
                }
            }
        }).start();

    }

    void setText(final String txt) {
        runOnUiThread(new Runnable() {
            public void run() {
                tv_loading.setText(txt);
            }
        });

    }

}
package com.example.jaishreeupreti.notice;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class downloadPDF {
    String host = "abesec.in";

    void downloadAndOpenPDF(final String download_file_url) {
        new Thread(new Runnable() {
            public void run() {
                System.out.println("inside downloads");
                downloadPdfContent("http://" + host + "/notifier/uploads_pdf/" + download_file_url.replace(" ", "%20"), download_file_url);
            }
        }).start();
    }

    public void downloadPdfContent(String urlToDownload, String name) {

        try {

            String fileName = name;
            URL url = new URL(urlToDownload);
            HttpURLConnection c = (HttpURLConnection) url.openConnection();
            int length = c.getContentLength();
            c.setRequestMethod("GET");
            c.connect();
            String PATH = Environment.getExternalStorageDirectory() + "/notifier/";
            File file = new File(PATH);
            file.mkdirs();

            System.out.println("inside downloads");
            File outputFile = new File(file, fileName);
            FileOutputStream fos = new FileOutputStream(outputFile);

            int status = c.getResponseCode();
            InputStream is = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;
            while ((len1 = is.read(buffer)) != -1) {
                fos.write(buffer, 0, len1);
            }
            fos.close();
            is.close();

            System.out.println("--pdf downloaded--ok--" + urlToDownload);
        } catch (Exception e) {
            System.out.println("--pdf downloaded not ok--" + urlToDownload);

            e.printStackTrace();
        }
    }

}

package com.androidexample.broadcastreceiver;

/**
 * Created by dell on 07-Nov-16.
 */

import android.media.MediaRecorder;
import android.os.CountDownTimer;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

import static java.lang.Math.log10;


public class SoundMeter
{
    private static final String LOG_TAG = "AudioRecordTest";
    private static String mFileName = null;
    private MediaRecorder mRecorder = null;
    private double d;

    public SoundMeter() {
        mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        mFileName += "/audiorecordtest.3gp";
    }

    public double startRecording() {

        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
        getAmplitude();

        //new CountDownTimer (long millisInFuture, long countDownInterval)
        new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                long l = millisUntilFinished / 1000;
                Log.d("seconds remaining",""+l);
            }

            public void onFinish() {
                Log.d("             countDown         ","           stoprecording          ");
                d = stopRecording();
            }
        }.start();

        return d;
    }

    public double stopRecording() {
        double d = getAmplitude();
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
        return d;
    }


    //Override
    public void onPause() {
        //super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
    }

    public double getAmplitude() {
        double amp = 0;
        try {

            if (mRecorder != null) {

                Log.d("----------mRecorder----------", "-----------NOT NULL---------");
                amp = mRecorder.getMaxAmplitude();
                Log.d("-------------amp-----------",""+amp);
            }

        }
        catch (Exception e) {
            Log.d("Exception",""+e);
        }

        amp = 20 * log10(amp / 32767);
        return amp;
    }
}

package com.androidexample.broadcastreceiver;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

import java.util.List;

public class ActivityRecognizedService extends IntentService {

    public ActivityRecognizedService() {
        super("ActivityRecognizedService");
    }

    public ActivityRecognizedService(String name) {
        super(name);
    }
    SharedPreferences sp;

    @Override
    protected void onHandleIntent(Intent intent) {

        if(ActivityRecognitionResult.hasResult(intent)) {
            ActivityRecognitionResult result = ActivityRecognitionResult.extractResult(intent);
            handleDetectedActivities( result.getProbableActivities() );
        }
    }
    private void handleDetectedActivities(List<DetectedActivity> probableActivities) {
        for( DetectedActivity activity : probableActivities ) {

            sp = getSharedPreferences("project", MODE_PRIVATE);
            // writing movement in a sharedpreferences variable

            switch( activity.getType() ) {
                case DetectedActivity.IN_VEHICLE: {
                    Log.d( "ActivityRecognition", "In Vehicle: " + activity.getConfidence() );
                    if( activity.getConfidence() >= 75 ) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText("Are you in vehicle?");
                        builder.setSmallIcon(R.drawable.ic_directions_car_white_48dp);
                        builder.setContentTitle("Your Movemet");
                        NotificationManagerCompat.from(this).notify(0, builder.build());

                        sp.edit().putString("movement","IN_VEHICLE").commit();
                    }
                        break;
                }
                case DetectedActivity.ON_BICYCLE: {
                    Log.d( "ActivityRecognition", "On Bicycle: " + activity.getConfidence() );
                    if( activity.getConfidence() >= 75 ) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText("bicycle?");
                        builder.setSmallIcon(R.drawable.ic_directions_bike_white_48dp);
                        builder.setContentTitle("Your Movemet");
                        NotificationManagerCompat.from(this).notify(0, builder.build());

                        sp.edit().putString("movement","ON_BICYCLE").commit();
                    }
                        break;
                }
                case DetectedActivity.ON_FOOT: {
                    Log.d( "ActivityRecognition", "On Foot: " + activity.getConfidence() );
                    if( activity.getConfidence() >= 75 ) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText("foot?");
                        builder.setSmallIcon(R.drawable.ic_accessibility_white_48dp);
                        builder.setContentTitle("Your Movemet");
                        NotificationManagerCompat.from(this).notify(0, builder.build());

                        sp.edit().putString("movement","ON_FOOT").commit();
                    }
                        break;
                }
                case DetectedActivity.RUNNING: {
                    Log.d( "ActivityRecognition", "Running: " + activity.getConfidence() );
                    if( activity.getConfidence() >= 75 ) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText("running");
                        builder.setSmallIcon(R.drawable.ic_directions_run_white_48dp);
                        builder.setContentTitle("Your Movemet");
                        NotificationManagerCompat.from(this).notify(0, builder.build());

                        sp.edit().putString("movement","RUNNING").commit();
                    }
                        break;
                }
                case DetectedActivity.STILL: {
                    Log.d( "ActivityRecognition", "Still: " + activity.getConfidence() );
                    if( activity.getConfidence() >= 75 ) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText("still?");
                        builder.setSmallIcon(R.drawable.ic_adb_white_48dp);
                        builder.setContentTitle("Your Movemet");
                        NotificationManagerCompat.from(this).notify(0, builder.build());

                        sp.edit().putString("movement","STILL").commit();

                    }
                        break;
                }
                case DetectedActivity.TILTING: {
                    Log.d( "ActivityRecognition", "Tilting: " + activity.getConfidence() );
                    if( activity.getConfidence() >= 75 ) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText("tilting?");
                        builder.setSmallIcon(R.drawable.ic_adb_white_48dp);
                        builder.setContentTitle("Your Movemet");
                        NotificationManagerCompat.from(this).notify(0, builder.build());

                        sp.edit().putString("movement","TILTING").commit();
                    }
                        break;
                }
                case DetectedActivity.WALKING: {
                    Log.d( "ActivityRecognition", "Walking: " + activity.getConfidence() );
                    if( activity.getConfidence() >= 75 ) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText( "Are you walking?" );
                        builder.setSmallIcon( R.drawable.ic_directions_walk_white_48dp );
                        builder.setContentTitle("Your Movemet");
                        NotificationManagerCompat.from(this).notify(0, builder.build());

                        sp.edit().putString("movement","WALKING").commit();
                    }

                    break;
                }
                case DetectedActivity.UNKNOWN: {
                    Log.d( "ActivityRecognition", "Unknown: " + activity.getConfidence() );
                    if( activity.getConfidence() >= 75 ) {
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
                        builder.setContentText( "Status Unknown" );
                        builder.setSmallIcon( R.drawable.ic_help_white_48dp );
                        builder.setContentTitle("Your Movemet");
                        NotificationManagerCompat.from(this).notify(0, builder.build());

                        sp.edit().putString("movement","UNKNOWN").commit();
                    }
                    break;
                }
            }
        }
    }
}
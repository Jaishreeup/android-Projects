package com.example.jaishreeupreti.geofence;
import com.google.android.gms.maps.model.LatLng;

import java.util.HashMap;


public class Constants {

    public static final long GEOFENCE_EXPIRATION_IN_MILLISECONDS = 12 * 60 * 60 * 1000;
    public static final float GEOFENCE_RADIUS_IN_METERS = 5;

    public static final HashMap<String, LatLng> LANDMARKS = new HashMap<String, LatLng>();
    static {
        // San Francisco International Airport.
        LANDMARKS.put("CSE DEPT", new LatLng(22.3177260, 87.3091010));
        LANDMARKS.put("NIVEDITA", new LatLng(22.3166333, 87.3060875));
        LANDMARKS.put("room", new LatLng(22.3165865, 87.3057603));
/*
        // Googleplex.
        LANDMARKS.put("Japantown", new LatLng(37.785281,-122.4296384));

        // Test
        LANDMARKS.put("SFO", new LatLng(37.621313,-122.378955));
  */  }
}
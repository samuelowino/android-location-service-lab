package org.aplusscreators.android_locationservices_lab.utils;

import android.content.Context;
import android.location.Location;
import android.os.Build;
import android.util.Log;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationResult;

public class CustomLocationCallback extends LocationCallback {

    private static final String TAG = CustomLocationCallback.class.getSimpleName();
    private Context context;

    public CustomLocationCallback(Context context) {
        this.context = context;
    }

    @Override
    public void onLocationResult(LocationResult locationResult) {
        super.onLocationResult(locationResult);
        if (locationResult == null)
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            locationResult.getLocations().forEach((Location location) -> {
                Log.w(TAG, "onUpdated: " + location.getLatitude() + ", " + location.getLongitude());
            });
        }
    }
}

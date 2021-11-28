package com.example.map;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.IBinder;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.security.Provider;
import java.util.List;


public class GpsTracker extends Service implements LocationListener {

    Context context;
    boolean isGpsEnabled = false;
    boolean isNetworkEnabled = false;
    boolean canGetLocation = false;
    double latitute, longitute;
    Location location;
    LocationManager locationManager;
    public static final long MIN_TIME_BT_UPDATES = 1000 * 60;
    public static final long MIN_DESTANCE_CHANGE_FOR_UPDATES = 10;

    public GpsTracker(Context context) {

        this.context = context;
        getLocation();

    }


    public Location getLocation() {

        try {
            locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
            isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGpsEnabled && !isNetworkEnabled) {

            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME_BT_UPDATES, MIN_DESTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitute = location.getLatitude();
                            longitute = location.getLongitude();
                        }

                    }

                }

                if (isGpsEnabled) {

                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BT_UPDATES, MIN_DESTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            latitute = location.getLatitude();
                            longitute = location.getLongitude();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return location;
    }


    public double getLatitute() {
        if (location != null) {
            latitute = location.getLatitude();
        }

        return latitute;
    }


    public double getLongitute() {
        if (location != null) {
            longitute = location.getLongitude();
        }
        return longitute;
    }


    public void stopUseingGps() {
        if (locationManager != null) {
            locationManager.removeUpdates(GpsTracker.this);
        }

    }


    public void showAlertDialog(Activity activity) {

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("GPS is Setting");
        alert.setMessage("GPS is not enabled, Do you want to go to settings menu?");
        alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });

        alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });


    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {

    }

    @Override
    public void onFlushComplete(int requestCode) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }
}

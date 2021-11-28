package com.example.map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    double longitute, latitute;

    SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);

        mapFragment.getMapAsync(this);


        GpsTracker gpsTracker = new GpsTracker(getApplicationContext());

        if (gpsTracker.canGetLocation) {
            latitute = gpsTracker.getLatitute();
            longitute = gpsTracker.getLongitute();
        } else {

            gpsTracker.showAlertDialog(this);


        }


    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(latitute , longitute)).title("My Location").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        googleMap.addMarker(markerOptions);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(latitute, longitute)).zoom(16).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));


    googleMap.setMyLocationEnabled(true);

        googleMap.getUiSettings().setZoomControlsEnabled(true);

        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);



    }
}
package com.example.aemacc13.accelerameter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import java.util.Observable;
import android.support.v4.app.ActivityCompat;

/**
 * Created by aemacc13 on 9/14/2016.
 */
public class LocationHandler extends Observable implements LocationListener {

    private LocationManager locationManager = null;
    private Location location;
    private MainActivity act;

    double latitude, longitude;


    public LocationHandler(MainActivity act) {
        this.act = act;
        this.locationManager = (LocationManager) act.getSystemService(this.act.LOCATION_SERVICE);

        if (this.locationManager.isProviderEnabled(locationManager.NETWORK_PROVIDER)) {
            this.location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
            locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER, 1000, 0, this);
        }

        this.latitude= this.location.getLatitude();
        this.longitude= this.location.getLongitude();
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    @Override
    public void onLocationChanged(Location location) {
        this.latitude= location.getLatitude();
        this.longitude= location.getLongitude();

        setChanged();
        notifyObservers(this.latitude);
        notifyObservers(this.longitude);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {}

    @Override
    public void onProviderDisabled(String s) {}
}

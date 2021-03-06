package at.tba.treasurehunt.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import at.tba.treasurehunt.activities.MapsActivity;
import at.tba.treasurehunt.controller.LocationController;

/**
 * Created by dAmihl on 27.04.15.
 */
public class GPSTracker implements LocationListener, GoogleMap.OnMyLocationChangeListener {

    private static final long serialVersionUID = 726472295622776147L;

    private MapsActivity mapsActivity;
    // Flag for GPS status
    boolean isGPSEnabled = false;
    // Flag for network status
    boolean isNetworkEnabled = false;
    // Flag for GPS status
    boolean canGetLocation = false;
    Location location; // Location
    double latitude; // Latitude
    double longitude; // Longitude
    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 30; // 30 meters
    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 20 * 1; // 20 seconds
    // Declaring a Location Manager
    protected LocationManager locationManager;
    private List<LocationListener> locationListeners;

    // Singleton -- ugly but the damned (unnecessary complex) Android API doesn't deserve anything better --
    private static class GPSTrackerHolder{
        private static final GPSTracker INSTANCE = new GPSTracker();
    }

    public static GPSTracker getInstance() {
        return GPSTrackerHolder.INSTANCE;
    }
    private GPSTracker() {
        locationListeners = new ArrayList<>();
    }

    public void init(MapsActivity maps) {
        this.mapsActivity = maps;
    }
    public void registerLocationListener(LocationListener listener) {
        locationListeners.add(listener);
    }
    public void removeLocationListener(LocationListener listener) {
        locationListeners.remove(listener);
    }
    public Location getLocation() {
        try {
            locationManager = (LocationManager) mapsActivity
                    .getSystemService(Context.LOCATION_SERVICE);
// Getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
// Getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (!isGPSEnabled && !isNetworkEnabled) {
                buildAlertMessageNoGps();
// No network provider is enabled
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
// If GPS enabled, get latitude/longitude using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                        locationManager.requestLocationUpdates(
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                        }
                    }
                }else{
                    buildAlertMessageNoGps();
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return location;
    }
    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app.
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }
    }
    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
        // return latitude
        return latitude;
    }
    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
        // return longitude
        return longitude;
    }
    /**
     * Function to check GPS/Wi-Fi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }
    @Override
    public void onLocationChanged(Location location) {
        for(LocationListener listener : locationListeners)
            listener.onLocationChanged(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
        for(LocationListener listener : locationListeners)
            listener.onProviderDisabled(provider);
    }
    @Override
    public void onProviderEnabled(String provider) {
        for(LocationListener listener : locationListeners)
            listener.onProviderDisabled(provider);
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        for(LocationListener listener : locationListeners)
            listener.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onMyLocationChange(Location location) {
        this.location = location;
        latitude = location.getLatitude();
        longitude = location.getLongitude();

    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mapsActivity);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        Intent callGPSSettingIntent = new Intent(
                                android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        mapsActivity.startActivity(callGPSSettingIntent);                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                        mapsActivity.finish();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

}

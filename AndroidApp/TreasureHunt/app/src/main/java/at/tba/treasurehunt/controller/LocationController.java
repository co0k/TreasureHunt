package at.tba.treasurehunt.controller;

/**
 * Created by dAmihl on 27.04.15.
 */

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Circle;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import at.tba.treasurehunt.dataprovider.TreasureChestsProvider;
import at.tba.treasurehunt.datastructures.treasure.Treasure;
import at.tba.treasurehunt.treasures.TreasureChestHolder;
import at.tba.treasurehunt.utils.GPSTracker;

/**
 * Singleton
 * Gets locations.
 *  - Current user location
 */

public class LocationController {

    private static LocationController instance = null;

    private GPSTracker gpsTracker;
    private GoogleMap mMap;

    private Circle myPosCircle;

    public static LocationController getInstance(){
        if (instance == null) instance = new LocationController();
        return instance;
    }

    private LocationController(){

    }

    public LatLng getMyPosition(){
        return new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
    }

    public void setMapAndGps(GoogleMap map, GPSTracker gps){
        this.gpsTracker = gps;
        this.mMap = map;
    }

    public void initialSetLocations(){
        initialMyLocation();
        initialChestLocations();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPosCircle.getCenter(), 40));
    }

    private void initialMyLocation(){
        if (gpsTracker.canGetLocation()) {
            gpsTracker.getLocation();
            myPosCircle = mMap.addCircle(new CircleOptions().center(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude())).radius(2));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude())));
            Log.i("LocCon", "UpdateMyLocation: " + gpsTracker.getLatitude() + "/" + gpsTracker.getLongitude());
        }
    }

    public void updateMyLocation(){
        if (gpsTracker.canGetLocation() && myPosCircle != null) {
            gpsTracker.getLocation();
            myPosCircle.setCenter(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude())));
            Log.i("LocCon", "UpdateMyLocation: " + gpsTracker.getLatitude() + "/" + gpsTracker.getLongitude());
        }
    }

    public void initialChestLocations(){
        TreasureChestHolder.getInstance().updateTreasureList();
        TreasureChestHolder.getInstance().drawChestsOnMap(mMap);
    }


}

package at.tba.treasurehunt.controller;

/**
 * Created by dAmihl on 27.04.15.
 */

import android.graphics.Color;
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

    //private Circle myPosCircle;

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
    }

    private void initialMyLocation(){
        gpsTracker.getLocation();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()), 15));

    }

    public void initialChestLocations(){
        TreasureChestHolder.getInstance().updateTreasureList();
        TreasureChestHolder.getInstance().drawChestsOnMap(mMap);
    }


}

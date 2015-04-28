package at.tba.treasurehunt.controller;

/**
 * Created by dAmihl on 27.04.15.
 */

import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import at.tba.treasurehunt.dataprovider.TreasureChestsProvider;
import at.tba.treasurehunt.datastructures.treasure.Treasure;
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

    public static LocationController getInstance(){
        if (instance == null) instance = new LocationController();
        return instance;
    }

    private LocationController(){
    }

    public void setMapAndGps(GoogleMap map, GPSTracker gps){
        this.gpsTracker = gps;
        this.mMap = map;
    }

    public void initialSetLocations(){
        updateMyLocation();
        updateChestsLocations();
    }

    public void updateMyLocation(){

        gpsTracker.getLocation();
        mMap.addMarker(new MarkerOptions().position(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude())).title("Your Position"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude())));
        Log.i("LocCon", "UpdateMyLocation: "+gpsTracker.getLatitude()+"/"+gpsTracker.getLongitude());
    }

    public void updateChestsLocations(){
        ArrayList<Treasure> treasureChests = TreasureChestsProvider.getInstance().getTreasureChestsList();
        if (treasureChests.size() == 0) return;

        for (Treasure c: treasureChests){
            mMap.addMarker(new MarkerOptions().position(new LatLng(c.getLocation().getLat(), c.getLocation().getLon())).title("TreasureChest"));
        }
    }


}

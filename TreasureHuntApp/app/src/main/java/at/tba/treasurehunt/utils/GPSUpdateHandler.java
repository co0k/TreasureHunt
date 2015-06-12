package at.tba.treasurehunt.utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import at.tba.treasurehunt.activities.MapsActivity;
import at.tba.treasurehunt.controller.LocationController;
import at.tba.treasurehunt.treasures.TreasureChestHolder;

/**
 * Created by dAmihl on 28.04.15.
 *
 * A handler to start, which updates every x ms the current gps location
 *
 */
public class GPSUpdateHandler {


    // 10 seconds per update
    private final static int DELAY_TIME = 1000 * 1;


    private Handler handler;
    private Runnable runnable;
    private MapsActivity mapsActivity;

    public GPSUpdateHandler(MapsActivity c){
        this.mapsActivity = c;
        handler = new Handler();

        runnable = new Runnable(){

            @Override
            public void run() {
                LatLng userPos = LocationController.getInstance().getMyPosition();
                TreasureChestHolder.getInstance().updateTreasuresInRange(userPos);
                mapsActivity.updateHotColdDistance(TreasureChestHolder.getInstance().getNearestTreasureDistance());
                if (TreasureChestHolder.getInstance().isTreasureInRange()){
                    mapsActivity.onTreasureInRange();
                }else{
                    mapsActivity.onNoTreasureInRange();
                }
                handler.postDelayed(this, DELAY_TIME);
            }
        };
    }

    public void startHandler(){
        handler.postDelayed(runnable, 1000);
    }



}

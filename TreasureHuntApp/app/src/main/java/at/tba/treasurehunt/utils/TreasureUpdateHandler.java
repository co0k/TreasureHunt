package at.tba.treasurehunt.utils;

import android.os.Handler;

import com.google.android.gms.maps.model.LatLng;

import at.tba.treasurehunt.activities.MapsActivity;
import at.tba.treasurehunt.controller.LocationController;
import at.tba.treasurehunt.treasures.TreasureChestHolder;

/**
 * Created by dAmihl on 09.06.15.
 */
public class TreasureUpdateHandler {

    private  final static int DELAY_TIME = 60 * 1000;

    private Handler handler;
    private Runnable runnable;
    private MapsActivity mapsActivity;

    public TreasureUpdateHandler(MapsActivity c){
        mapsActivity = c;
        handler = new Handler();

        runnable = new Runnable(){

            @Override
            public void run() {
                mapsActivity.loadTreasures();
                handler.postDelayed(this, DELAY_TIME);
            }
        };
    }

    public void startHandler(){
        handler.postDelayed(runnable, 1000);
    }


}

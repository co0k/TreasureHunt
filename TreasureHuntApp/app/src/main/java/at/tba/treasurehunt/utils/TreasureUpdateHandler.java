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
    private boolean isRunning;

    // Singleton -- ugly but the damned (unnecessary complex) Android API doesn't deserve anything better --
    private static class TreasureUpdateHandlerHolder{
        private static final TreasureUpdateHandler INSTANCE = new TreasureUpdateHandler();
    }

    public static TreasureUpdateHandler getInstance() {
        return TreasureUpdateHandlerHolder.INSTANCE;
    }

    private TreasureUpdateHandler() {}

    public void init(MapsActivity c){
        mapsActivity = c;
        handler = new Handler();
        isRunning = false;
        runnable = new Runnable(){

            @Override
            public void run() {
                mapsActivity.loadTreasures();
                if(isRunning)
                    handler.postDelayed(this, DELAY_TIME);
            }
        };
    }

    public void startHandler(){
        isRunning = true;
        handler.postDelayed(runnable, 1000);
    }
    public void stopHandler(){
        isRunning = false;
    }


}

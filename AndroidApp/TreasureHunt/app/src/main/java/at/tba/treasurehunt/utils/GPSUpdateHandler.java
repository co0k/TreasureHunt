package at.tba.treasurehunt.utils;

import android.os.Handler;

import at.tba.treasurehunt.controller.LocationController;

/**
 * Created by dAmihl on 28.04.15.
 *
 * A handler to start, which updates every x ms the current gps location
 *
 */
public class GPSUpdateHandler extends Handler {


    // 10 seconds per update
    private final static int DELAY_TIME = 1000 * 10;


    private Handler handler;
    private Runnable runnable;

    public GPSUpdateHandler(){

        handler = new Handler();

        runnable = new Runnable(){

            @Override
            public void run() {
                LocationController.getInstance().updateMyLocation();
                handler.postDelayed(this, DELAY_TIME);
            }
        };
    }

    public void startHandler(){
        handler.postDelayed(runnable, DELAY_TIME);
    }



}

package at.tba.treasurehunt.utils;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import at.tba.treasurehunt.controller.LocationController;
import at.tba.treasurehunt.datastructures.treasure.Treasure;
import at.tba.treasurehunt.treasures.TreasureChestHolder;

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
    private Context context;

    public GPSUpdateHandler(Context c){
        this.context = c;
        handler = new Handler();

        runnable = new Runnable(){

            @Override
            public void run() {
                LocationController.getInstance().updateMyLocation();
                Treasure treasureInRange = TreasureChestHolder.getInstance().treasureChestInRange(LocationController.getInstance().getMyPosition());
                if (treasureInRange != null){
                    Log.i("TRSR", "FOUND A TREASURE!!");
                    ShowMessageHelper.showSimpleInfoMessagePopUp("You found a treasure bro!", context);
                }
                handler.postDelayed(this, DELAY_TIME);
            }
        };
    }

    public void startHandler(){
        handler.postDelayed(runnable, DELAY_TIME);
    }



}

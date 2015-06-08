package at.tba.treasurehunt.debug;

import android.util.Log;

/**
 * Created by dAmihl on 11.05.15.
 */
public class Debug {

    private static final String TAG = "TreasureHuntDebug";

    private static boolean DEBUG_MODE = true;

    public static void printDebugMessage(String msg){
        if (!DEBUG_MODE) return;
        Log.i(TAG, msg);
    }

}

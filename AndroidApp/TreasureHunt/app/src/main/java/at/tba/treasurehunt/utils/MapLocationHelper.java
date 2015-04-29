package at.tba.treasurehunt.utils;

import android.location.Location;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by dAmihl on 28.04.15.
 */
public class MapLocationHelper {

    /**
     *
     * @param myLocation users current position
     * @param targetLocation position of a target (for example treasure chest)
     * @param radius radius to check for range
     * @return true, if target is in radius of myLocation
     */

    /*
    Bauingeniur - Hess Haus
    47.2641234 / 11.3451889 -
    47.264097 / 11.343373
     */
    public static boolean isInRange(LatLng myLocation, LatLng targetLocation, int radius){

        /**
         * TODO: implement super nice algorithm
         */
        /*
        compute the real radius by dividing by 1000
        only look at the 0.00x position
         */
        double realRadius = (double) radius / 1000;

        double diffLat = Math.abs((myLocation.latitude - targetLocation.latitude));
        double diffLong = Math.abs((myLocation.longitude - targetLocation.longitude));

        return diffLat <= realRadius &&
               diffLong <= realRadius;

    }




}

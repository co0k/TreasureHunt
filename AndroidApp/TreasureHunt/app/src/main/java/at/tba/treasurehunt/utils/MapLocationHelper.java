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
         * TODO: maybe implement super nice algorithm
         * or just use, like right now, GoogleMapAPI's super nice algorithm
         */
        return distanceBetween(myLocation, targetLocation) <= radius;
    }

    public static double distanceBetween(LatLng pos1, LatLng pos2){
        Location loc1 = new Location("");
        loc1.setLatitude(pos1.latitude);
        loc1.setLongitude(pos1.longitude);

        Location loc2 = new Location("");
        loc2.setLatitude(pos2.latitude);
        loc2.setLongitude(pos2.longitude);
        double distance = loc2.distanceTo(loc1);
        return distance;
    }






}

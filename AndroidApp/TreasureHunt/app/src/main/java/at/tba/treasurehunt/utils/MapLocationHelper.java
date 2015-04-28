package at.tba.treasurehunt.utils;

import android.location.Location;

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
    public static boolean isInRange(Location myLocation, Location targetLocation, int radius){

        /**
         * TODO: implement super nice algorithm
         */
        return Math.abs((myLocation.getLatitude() - targetLocation.getLatitude())) <= radius &&
                Math.abs((myLocation.getLongitude() - targetLocation.getLongitude())) <= radius;

    }


}

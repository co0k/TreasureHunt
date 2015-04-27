package at.tba.treasurehunt.controller;

/**
 * Created by dAmihl on 27.04.15.
 */

/**
 * Singleton
 * Gets locations.
 *  - Current user location
 */

public class LocationController {

    private static LocationController instance = null;

    public static LocationController getInstance(){
        if (instance == null) instance = new LocationController();
        return instance;
    }

    private LocationController(){}

}

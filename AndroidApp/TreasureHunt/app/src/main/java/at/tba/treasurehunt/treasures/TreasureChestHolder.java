package at.tba.treasurehunt.treasures;

import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import at.tba.treasurehunt.dataprovider.TreasureChestsProvider;
import at.tba.treasurehunt.datastructures.treasure.Treasure;
import at.tba.treasurehunt.utils.MapLocationHelper;

/**
 * Created by dAmihl on 28.04.15.
 */
public class TreasureChestHolder {

    private static TreasureChestHolder instance = null;

    public static TreasureChestHolder getInstance(){
        if (instance == null) instance = new TreasureChestHolder();
        return instance;
    }

    private TreasureChestHolder(){
        this.treasureList = new ArrayList<Treasure>();
    }

    private ArrayList<Treasure> treasureList;

    private Treasure currentTreasureInRange = null;


    /**
     * Returns the (first in list) Treasure object which is in range of the UserPos given
     * @param userPos the current users Position
     * @return the Treasure object, which is in range of UserPos
     */
    private Treasure treasureChestInRange(LatLng userPos){
        for (Treasure t: treasureList){
            LatLng treasurePos = new LatLng(t.getLocation().getLat(), t.getLocation().getLon());
            if (MapLocationHelper.isInRange(userPos,treasurePos, 10)){
                return t;
            }
        }
        return null;
    }


    /**
     * checks if a treasure is in range by treasureChestInRange()
     * if true, then save the current treasure in range
     */
    public void updateTreasuresInRange(LatLng userPos){
        currentTreasureInRange = treasureChestInRange(userPos);
    }


    /**
     * Returns bool, if a treasure is in range on the map
     * @return true, if a treasure object is in range of the user
     */
    public boolean isTreasureInRange(){
        return currentTreasureInRange != null;
    }

    /**
     * Updates the treasure list of TreasureChestHolder.
     * Treasures given by TreasureChestProvider
     */
    public void updateTreasureList(){
        ArrayList<Treasure> treasureChests = TreasureChestsProvider.getInstance().getTreasureChestsList();
        treasureList.clear();
        treasureList.addAll(treasureChests);
    }


    /**
     * Draws ever chest in treasureList on the given GoogleMap
     * @param mMap GoogleMap to draw on
     */
    public void drawChestsOnMap(GoogleMap mMap){
        for (Treasure t: treasureList){
            mMap.addCircle(new CircleOptions().center(new LatLng(t.getLocation().getLat(), t.getLocation().getLon())).radius(1));
        }
    }

}

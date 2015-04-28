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


    public Treasure treasureChestInRange(LatLng userPos){
        for (Treasure t: treasureList){
            LatLng treasurePos = new LatLng(t.getLocation().getLat(), t.getLocation().getLon());
            if (MapLocationHelper.isInRange(userPos,treasurePos, 10)){
                return t;
            }
        }
        return null;
    }

    public void updateTreasureList(){
        ArrayList<Treasure> treasureChests = TreasureChestsProvider.getInstance().getTreasureChestsList();
        treasureList.clear();
        treasureList.addAll(treasureChests);
    }

    public Treasure getOneTreasure(){
        return treasureList.get(0);
    }


    public void drawChestsOnMap(GoogleMap mMap){
        for (Treasure t: treasureList){
            mMap.addCircle(new CircleOptions().center(new LatLng(t.getLocation().getLat(), t.getLocation().getLon())).radius(1));
        }
    }

}

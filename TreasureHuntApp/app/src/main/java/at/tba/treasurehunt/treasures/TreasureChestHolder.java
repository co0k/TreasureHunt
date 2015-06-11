package at.tba.treasurehunt.treasures;

import android.location.Location;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

import java.util.ArrayList;
import android.util.Log;


import at.tba.treasurehunt.dataprovider.IOpenTreasureCallback;
import at.tba.treasurehunt.dataprovider.ITreasureLoadedCallback;
import at.tba.treasurehunt.dataprovider.TreasureChestsProvider;
import at.tba.treasurehunt.servercomm.ServerCommunication;
import at.tba.treasurehunt.tasks.IResponseCallback;
import at.tba.treasurehunt.utils.MapLocationHelper;
import communication_controller.json.JsonConstructor;
import data_structures.treasure.Treasure;

/**
 * Created by dAmihl on 28.04.15.
 */
public class TreasureChestHolder implements IResponseCallback{

    private static TreasureChestHolder instance = null;

    private static final int TREASURE_OPEN_RADIUS = 30;
    private static final boolean DEBUG_DRAW_CHESTS = false;

    public static TreasureChestHolder getInstance(){
        if (instance == null) instance = new TreasureChestHolder();
        return instance;
    }

    private TreasureChestHolder(){
        this.treasureList = new ArrayList<Treasure>();
    }

    private ArrayList<Treasure> treasureList;

    private Treasure currentTreasureInRange = null;
    private Treasure nearestTreasure = null;
    private double distanceToNearestTreasure = Double.MAX_VALUE;
    private Treasure currentSelectedTreasure = null;

    private IOpenTreasureCallback openTreasureCallback;


    /**
     * Returns the nearest Treasure object if it is in range.
     * Nearest treasure gets updated by updateNearestTreasure
     * @param userPos the current users Position
     * @return the Treasure object, which is in range of UserPos
     */
    private Treasure treasureChestInRange(LatLng userPos){
        if (nearestTreasure == null) return null;
        LatLng treasurePos = new LatLng(nearestTreasure.getLocation().getLat(), nearestTreasure.getLocation().getLon());
        if (MapLocationHelper.isInRange(userPos,treasurePos, TREASURE_OPEN_RADIUS)){
            return nearestTreasure;
        }
        return null;
    }


    /**
     * Iterates all treasures in lists and saves the nearest treasure with its distance
     * @param userPos the current users Position
     */
    public void updateNearestTreasure(LatLng userPos){
        double dist = -1;
        Treasure temp = null;
        if (treasureList.size() < 1) return;
        else{
            temp = treasureList.get(0);
            LatLng treasurePos = new LatLng(temp.getLocation().getLat(), temp.getLocation().getLon());
            dist = MapLocationHelper.distanceBetween(userPos, treasurePos);
        }

        for (Treasure t: treasureList){
            if (t == temp) continue;
            LatLng treasurePos = new LatLng(t.getLocation().getLat(), t.getLocation().getLon());
            double newDist = MapLocationHelper.distanceBetween(userPos, treasurePos);
            if (newDist <= dist){
                dist = newDist;
                temp = t;
            };
        }
        nearestTreasure = temp;
        distanceToNearestTreasure = dist;
    }



    public double getNearestTreasureDistance(){
        return distanceToNearestTreasure;
    }

    public Treasure getNearestTreasure(){
        return this.nearestTreasure;
    }

    /**
     * checks if a treasure is in range by treasureChestInRange()
     * if true, then save the current treasure in range
     */
    public void updateTreasuresInRange(LatLng userPos){
        updateNearestTreasure(userPos);
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
        if(!DEBUG_DRAW_CHESTS) return;
        
        if (mMap == null){
            Log.d("drawChests", "Map is null!");
            return;
        }
        mMap.clear();
        for (Treasure t: treasureList){
            mMap.addCircle(new CircleOptions().center(new LatLng(t.getLocation().getLat(), t.getLocation().getLon())).radius(1));
        }
    }

    /**
     * Called from QuizActivity when the chest is opened successfully.
     * Treasure will be set inactive
     */
    public void openTreasure(Treasure t, IOpenTreasureCallback callback){
        //TreasureChestsProvider.getInstance().getTreasureChestsList().remove(t);
        this.openTreasureCallback = callback;
        ServerCommunication.getInstance().sendOpenTreasureRequest(t, this);
    }

    public void blockTreasureForUser(Treasure t){
        ServerCommunication.getInstance().sendOpenTreasureWrongAnswerEvent(t,this);
        this.treasureList.remove(t);
        TreasureChestsProvider.getInstance().removeTreasureFromList(t);
    }

    public void treasureRightAnswer(Treasure t){
        ServerCommunication.getInstance().sendOpenTreasureRightAnswerEvent(t, this);
        this.treasureList.remove(t);
        TreasureChestsProvider.getInstance().removeTreasureFromList(t);
    }


    /**
     * The current selected treasure chest will be set when the "Open Treasure" is pressed in MapsActivity
     * It will then be used by TreasureOpenActivity to get the treasure data
     * @param t Treasure to be set as current selected (when selecting "Open Treasure")
     */
    public void setCurrentSelectedTreasure(Treasure t){
        this.currentSelectedTreasure = t;
    }

    public Treasure getCurrentSelectedTreasure(){
        return this.currentSelectedTreasure;
    }


    @Override
    public void onResponseReceived(JSONRPC2Response response) {
        JsonConstructor constr = new JsonConstructor();
        Boolean result = constr.fromJson((String) response.getResult(), Boolean.class);
        if (result == null) return;
        if (result)
            openTreasureCallback.onOpenTreasureSuccess();
        else
            openTreasureCallback.onOpenTreasureFailure();
    }

    @Override
    public void onResponseReceiveError() {
        openTreasureCallback.onOpenTreasureFailure();
    }
}

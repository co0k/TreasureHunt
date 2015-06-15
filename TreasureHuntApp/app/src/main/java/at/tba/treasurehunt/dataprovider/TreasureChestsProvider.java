package at.tba.treasurehunt.dataprovider;

import com.google.android.gms.maps.model.LatLng;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import android.util.Log;

import at.tba.treasurehunt.servercomm.ServerCommunication;
import at.tba.treasurehunt.tasks.IResponseCallback;
import at.tba.treasurehunt.treasures.TreasureChestHolder;
import at.tba.treasurehunt.utils.DummyDataProvider;
import communication_controller.json.JsonConstructor;
import data_structures.treasure.Treasure;

/**
 * Created by dAmihl on 28.04.15.
 */
public class TreasureChestsProvider implements IResponseCallback {

    private static TreasureChestsProvider instance = null;
    private List<ITreasureLoadedCallback> treasureLoadedCallbacks;
    private ServerCommunication communication;

    private static boolean DEBUG_DATA = false;
    private static boolean DEBUG_ALL_TREASURES = false;

    public static TreasureChestsProvider getInstance(){
        if (instance == null) instance = new TreasureChestsProvider(ServerCommunication.getInstance());
        return instance;
    }
    public void registerListener(ITreasureLoadedCallback treasureLoadedCallback) {
        treasureLoadedCallbacks.add(treasureLoadedCallback);
    }

    public void removeListener(ITreasureLoadedCallback treasureLoadedCallback) {
        treasureLoadedCallbacks.add(treasureLoadedCallback);
    }

    private ArrayList<Treasure> treasures = new ArrayList<Treasure>();

    private TreasureChestsProvider(ServerCommunication communication){
        if (DEBUG_DATA) {
            treasures.add(DummyDataProvider.getDummyTreasureData(0));
            treasures.add(DummyDataProvider.getDummyTreasureData(1));
            treasures.add(DummyDataProvider.getDummyTreasureData(2));
            treasures.add(DummyDataProvider.getDummyTreasureData(3));
            treasures.add(DummyDataProvider.getDummyTreasureData(4));
        }
        this.communication = communication;
        this.treasureLoadedCallbacks = new ArrayList<>();
    }

    public void loadTreasures(LatLng pos, ITreasureLoadedCallback callback){
        registerListener(callback);
        if (!DEBUG_DATA) {
            if (DEBUG_ALL_TREASURES)
                ServerCommunication.getInstance().getAllTreasuresFromServer(this);
            else
                ServerCommunication.getInstance().getNearTreasuresFromServer(this, pos.latitude, pos.longitude);
        } else {
            for (ITreasureLoadedCallback treasureLoadedCallback : treasureLoadedCallbacks)
                treasureLoadedCallback.onTreasuresLoadedSuccess(this.treasures);
        }
    }


    /***
     *
     * @return all treasure chests provided by the server in an ArrayList<Treasure> object
     */
    public ArrayList<Treasure> getTreasureChestsList(){

        return treasures;
    }

    /***
     * Returns all treasure chests provided by the server, given that those are in a given range
     * @param radius the range the chest has to be in
     * @return ArrayList with all treasure chests provided in range
     */
    public ArrayList<Treasure> getTreasureChestsListInRange(int radius){
        ArrayList<Treasure> result = new ArrayList<Treasure>();
        return result;
    }


    public void removeTreasureFromList(Treasure t){

        this.treasures.remove(t);
    }

    @Override
    public void onResponseReceived(JSONRPC2Response response) {
        JsonConstructor constr = new JsonConstructor();
        Treasure[] ts = constr.fromJson((String)response.getResult(), Treasure[].class);
        if (ts == null){
            Log.d("TreasureProvider", "Treasure Array is null!");
            return;
        }
        List<Treasure> list = new ArrayList<Treasure>(Arrays.asList(ts));
        treasures.clear();
        treasures.addAll(list);
        for (ITreasureLoadedCallback treasureLoadedCallback : treasureLoadedCallbacks)
            treasureLoadedCallback.onTreasuresLoadedSuccess(treasures);
    }

    @Override
    public void onResponseReceiveError() {
        for (ITreasureLoadedCallback treasureLoadedCallback : treasureLoadedCallbacks)
            treasureLoadedCallback.onTreasureLoadedFailure();
    }
}

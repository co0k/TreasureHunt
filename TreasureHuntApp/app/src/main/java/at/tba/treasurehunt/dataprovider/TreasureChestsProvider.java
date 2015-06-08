package at.tba.treasurehunt.dataprovider;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import at.tba.treasurehunt.servercomm.ServerCommunication;
import at.tba.treasurehunt.tasks.IResponseCallback;
import at.tba.treasurehunt.utils.AlertHelper;
import at.tba.treasurehunt.utils.DummyDataProvider;
import communication_controller.json.JsonConstructor;
import data_structures.treasure.Treasure;

/**
 * Created by dAmihl on 28.04.15.
 */
public class TreasureChestsProvider implements IResponseCallback {

    private static TreasureChestsProvider instance = null;
    private ITreasureLoadedCallback treasureLoadedCallback;

    private static boolean DEBUG_DATA = false;

    public static TreasureChestsProvider getInstance(){
        if (instance == null) instance = new TreasureChestsProvider();
        return instance;
    }

    private ArrayList<Treasure> treasures = new ArrayList<Treasure>();

    private TreasureChestsProvider(){
        if (DEBUG_DATA) {
            treasures.add(DummyDataProvider.getDummyTreasureData(0));
            treasures.add(DummyDataProvider.getDummyTreasureData(1));
            treasures.add(DummyDataProvider.getDummyTreasureData(2));
            treasures.add(DummyDataProvider.getDummyTreasureData(3));
            treasures.add(DummyDataProvider.getDummyTreasureData(4));
        }
        //ServerCommunication.getInstance().getTreasuresFromServer(this);
    }

    public void loadTreasures(ITreasureLoadedCallback callback){
        this.treasureLoadedCallback = callback;
        if (!DEBUG_DATA) {
            ServerCommunication.getInstance().getTreasuresFromServer(this);
        }else{
            treasureLoadedCallback.onTreasuresLoadedSuccess();
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

    @Override
    public void onResponseReceived(JSONRPC2Response response) {
        JsonConstructor constr = new JsonConstructor();
        Treasure[] ts = constr.fromJson((String)response.getResult(), Treasure[].class);
        List<Treasure> list = new ArrayList<Treasure>(Arrays.asList(ts));
        treasures.clear();
        treasures.addAll(list);
        treasureLoadedCallback.onTreasuresLoadedSuccess();
    }

    @Override
    public void onResponseReceiveError() {
        treasureLoadedCallback.onTreasureLoadedFailure();
    }
}

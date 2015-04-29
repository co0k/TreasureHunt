package at.tba.treasurehunt.dataprovider;

import java.util.ArrayList;

import at.tba.treasurehunt.datastructures.treasure.Treasure;
import at.tba.treasurehunt.utils.DummyDataProvider;

/**
 * Created by dAmihl on 28.04.15.
 */
public class TreasureChestsProvider {

    private static TreasureChestsProvider instance = null;

    public static TreasureChestsProvider getInstance(){
        if (instance == null) instance = new TreasureChestsProvider();
        return instance;
    }


    /***
     *
     * @return all treasure chests provided by the server in an ArrayList<Treasure> object
     */
    public ArrayList<Treasure> getTreasureChestsList(){
        ArrayList<Treasure> result = new ArrayList<Treasure>();
        result.add(DummyDataProvider.getDummyTreasureData(0));
        result.add(DummyDataProvider.getDummyTreasureData(1));
        result.add(DummyDataProvider.getDummyTreasureData(2));
        return result;
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
}

package at.tba.treasurehunt.dataprovider;

import java.util.List;

import data_structures.treasure.Treasure;

/**
 * Created by dAmihl on 01.06.15.
 */
public interface ITreasureLoadedCallback {

    void onTreasuresLoadedSuccess(List<Treasure> treasures);

    void onTreasureLoadedFailure();

}

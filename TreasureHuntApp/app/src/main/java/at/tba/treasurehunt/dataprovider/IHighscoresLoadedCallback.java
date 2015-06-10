package at.tba.treasurehunt.dataprovider;

import data_structures.user.HighscoreList;

/**
 * Created by dAmihl on 08.06.15.
 */
public interface IHighscoresLoadedCallback {
    public void onHighscoresLoadedSuccess(HighscoreList list);
    public void onHighscoresLoadedFailure();
}

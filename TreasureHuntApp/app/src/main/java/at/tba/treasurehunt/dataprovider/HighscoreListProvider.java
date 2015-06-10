package at.tba.treasurehunt.dataprovider;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

import at.tba.treasurehunt.servercomm.ServerCommunication;
import at.tba.treasurehunt.tasks.IResponseCallback;
import communication_controller.json.JsonConstructor;
import data_structures.user.HighscoreList;

/**
 * Created by dAmihl on 10.06.15.
 */
public class HighscoreListProvider implements IResponseCallback {

    private static HighscoreListProvider instance = null;

    public static HighscoreListProvider getInstance(){
        if (instance == null) instance = new HighscoreListProvider();
        return instance;
    }

    private IHighscoresLoadedCallback highscoresLoadedCallback;

    public void getHighscoreListFromServer(IHighscoresLoadedCallback callback, Integer low, Integer high){
        this.highscoresLoadedCallback = callback;
        ServerCommunication.getInstance().getHighscoreListInRangeFromServer(this, low, high);
    }


    @Override
    public void onResponseReceived(JSONRPC2Response response) {
        JsonConstructor constr = new JsonConstructor();
        HighscoreList result = constr.fromJson((String) response.getResult(), HighscoreList.class);
        highscoresLoadedCallback.onHighscoresLoadedSuccess(result);
    }

    @Override
    public void onResponseReceiveError() {
        highscoresLoadedCallback.onHighscoresLoadedFailure();
    }
}

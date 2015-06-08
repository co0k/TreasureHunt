package at.tba.treasurehunt.servercomm;

import java.util.ArrayList;

import at.tba.treasurehunt.tasks.IResponseCallback;
import data_structures.treasure.Treasure;
import data_structures.user.HighscoreList;
import data_structures.user.User;


/**
 * Created by dAmihl on 11.05.15.
 */
public interface IServerCommunicationDAO {

    public ArrayList<Treasure> getTreasuresFromServer(IResponseCallback responseCallback);

    public boolean logInToServer(String uName, String pwd, IResponseCallback responseCallback);

    public void registerUserOnServer(String uName, String email, String pwd, IResponseCallback responseCallback);

    public void getUserById(int userId, IResponseCallback responseCallback);

    public void getHighscoreListFromServer(IResponseCallback responseCallback);

    public void getHighscoreListInRangeFromServer(int from, int to, IResponseCallback responseCallback);

    public void sendOpenTreasureRequest(Treasure t, IResponseCallback callback);

    public void sendOpenTreasureWrongAnswerEvent(Treasure t, IResponseCallback callback);

    public void messageReceived(String payload);

}

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

    public ArrayList<Treasure> getAllTreasuresFromServer(IResponseCallback responseCallback);

    public ArrayList<Treasure> getNearTreasuresFromServer(IResponseCallback responseCallback, Double lat, Double lng);

    public boolean logInToServer(String uName, String pwd, IResponseCallback responseCallback);

    public void registerUserOnServer(String uName, String email, String pwd, IResponseCallback responseCallback);

    public void getUserById(int userId, IResponseCallback responseCallback);

    public void getHighscoreListFromServer(IResponseCallback responseCallback);

    public void getHighscoreListInRangeFromServer(IResponseCallback responseCallback,Integer low, Integer hi);

    public void sendOpenTreasureRequest(Treasure t, IResponseCallback callback);

    public void sendOpenTreasureWrongAnswerEvent(Treasure t, IResponseCallback callback);

    public void sendOpenTreasureRightAnswerEvent(Treasure t, IResponseCallback callback);

    public void messageReceived(String payload);

}

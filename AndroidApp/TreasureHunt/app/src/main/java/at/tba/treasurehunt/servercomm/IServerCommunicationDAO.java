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

    public User getUserById(int userId);

    public HighscoreList getHighscoreListFromServer();

    public HighscoreList getHighscoreListInRangeFromServer(int from, int to);

    public void messageReceived(String payload);

}

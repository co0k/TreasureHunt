package at.tba.treasurehunt.servercomm;

import java.util.ArrayList;

import data_structures.treasure.Treasure;
import data_structures.user.HighscoreList;
import data_structures.user.User;


/**
 * Created by dAmihl on 11.05.15.
 */
public interface ServerCommunicationDAO {

    public ArrayList<Treasure> getTreasuresFromServer();

    public boolean logInToServer(String uName, String pwd);

    public User getUserById(int userId);

    public HighscoreList getHighscoreListFromServer();

    public HighscoreList getHighscoreListInRangeFromServer(int from, int to);

}

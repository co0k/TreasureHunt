package at.tba.treasurehunt.servercomm;

import java.util.ArrayList;

import at.tba.treasurehunt.datastructures.treasure.Treasure;
import at.tba.treasurehunt.datastructures.user.HighscoreList;
import at.tba.treasurehunt.datastructures.user.User;

/**
 * Created by dAmihl on 11.05.15.
 */
public interface ServerCommunicationDAO {

    public ArrayList<Treasure> getTreasuresFromServer();

    public boolean logInToServer();

    public User getUserById(int userId);

    public HighscoreList getHighscoreListFromServer();

    public HighscoreList getHighscoreListInRangeFromServer(int from, int to);

}

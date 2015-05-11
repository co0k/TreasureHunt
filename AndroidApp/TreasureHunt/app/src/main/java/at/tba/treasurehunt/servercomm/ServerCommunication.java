package at.tba.treasurehunt.servercomm;

import java.util.ArrayList;

import at.tba.treasurehunt.datastructures.treasure.Treasure;
import at.tba.treasurehunt.datastructures.user.HighscoreList;
import at.tba.treasurehunt.datastructures.user.User;

/**
 * Created by dAmihl on 11.05.15.
 */
public class ServerCommunication implements ServerCommunicationDAO{

    private static ServerCommunication instance = null;

    private ServerConnection serverConn;

    public static ServerCommunication getInstance(){
        if (instance == null) instance = new ServerCommunication();
        return instance;
    }

    private ServerCommunication(){
        this.serverConn = ServerConnection.getInstance();
    }

    @Override
    public ArrayList<Treasure> getTreasuresFromServer() {
        return null;
    }

    @Override
    public boolean logInToServer() {
        return false;
    }

    @Override
    public User getUserById(int userId) {
        return null;
    }

    @Override
    public HighscoreList getHighscoreListFromServer() {
        return null;
    }

    @Override
    public HighscoreList getHighscoreListInRangeFromServer(int from, int to) {
        return null;
    }

}

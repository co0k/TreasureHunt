package at.tba.treasurehunt.servercomm;

import java.util.ArrayList;

import data_structures.treasure.Treasure;
import data_structures.user.HighscoreList;
import data_structures.user.User;


/**
 * Created by dAmihl on 11.05.15.
 */
public class ServerCommunication implements IServerCommunicationDAO{

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
    public boolean logInToServer(String uName, String pwd) {
        return true;
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

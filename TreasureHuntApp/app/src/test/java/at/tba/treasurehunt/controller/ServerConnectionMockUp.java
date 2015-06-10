package at.tba.treasurehunt.controller;
import static org.junit.Assert.*;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

import java.util.List;
import java.util.Map;

import at.tba.treasurehunt.servercomm.IServerConnection;
import at.tba.treasurehunt.servercomm.IServerConnectionCallback;
import at.tba.treasurehunt.servercomm.ServerCommunication;
import communication_controller.json.JsonConstructor;
import data_structures.treasure.Treasure;
import data_structures.user.HighscoreList;
import data_structures.user.User;

/**
 * Created by poorpot on 10.06.15.
 */
public class ServerConnectionMockUp implements IServerConnection{

    List<Treasure> exampleTreasures;
    HighscoreList highscoreList;
    User user;
    int token;
    private ServerCommunication serverCommunication;

    public ServerConnectionMockUp(List<Treasure> exampleTreasures, HighscoreList highscoreList, User user, int token) {
        this.exampleTreasures = exampleTreasures;
        this.highscoreList = highscoreList;
        this.user = user;
        this.token = token;
    }

    public ServerCommunication getServerCommunication() {
        return this.serverCommunication;
    }

    public void setServerCommunication(ServerCommunication serverCommunication) {
        this.serverCommunication = serverCommunication;
    }

    @Override
    public boolean connectServer(IServerConnectionCallback callback) {
        return true;
    }

    @Override
    public boolean disconnectServer() {
        return true;
    }

    @Override
    public boolean sendPackage() {
        return false;
    }

    @Override
    public boolean isConnected() {
        return true;
    }

    @Override
    public boolean sendMessage(String s) {

        return false;
    }

    @Override
    public boolean sendJSONRequest(JSONRPC2Request req) {
        String method = req.getMethod();
        Map<String, Object> paramMap = req.getNamedParams();
        JsonConstructor jsonC = new JsonConstructor();

        switch(method) {
            case "checklogin": {
                assertEquals("number params is wrong", paramMap.size(), 2);
                String username = (String) paramMap.get("username");
                assertNotNull("username is null", username);
                assertTrue("username is empty", username.length() != 0);
                String pwHash = (String) paramMap.get("pwHash");
                assertNotNull("username is null", pwHash);
                assertTrue("username is empty", pwHash.length() != 0);

                JSONRPC2Response response = new JSONRPC2Response(jsonC.toJson(token), req.getID().toString());
                serverCommunication.messageReceived(response.toJSONString());
                break;
            }
            case "registeruser":
                assertEquals("number params is wrong", paramMap.size(), 3);
                break;
            case "getneartreasures":
                assertEquals("number of params is wrong", paramMap.size(), 3);
                break;
            case "getalltreasures":
                assertEquals("number of params is wrong", paramMap.size(), 1);
                break;
            case "getprofiledata":
                assertEquals("number of params is wrong", paramMap.size(), 2);
                break;
            default:
                JSONRPC2Response response = new JSONRPC2Response(new JSONRPC2Error(123,"illegal method"), req.getID());
                serverCommunication.messageReceived(response.toJSONString());
                break;
        }

        return false;
    }
}

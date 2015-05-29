package at.tba.treasurehunt.servercomm;

import android.util.Log;

import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Parser;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import at.tba.treasurehunt.tasks.IResponseCallback;
import data_structures.treasure.Treasure;
import data_structures.user.HighscoreList;
import data_structures.user.User;


/**
 * Created by dAmihl on 11.05.15.
 */
public class ServerCommunication implements IServerCommunicationDAO{

    private static final String TAG = "ServerCommunication";

    private static ServerCommunication instance = null;

    private ServerConnection serverConn;
    private JSONRPC2Parser parser;

    private IResponseCallback responseCallback;

    private boolean newMessage = false;

    public static ServerCommunication getInstance(){
        if (instance == null) instance = new ServerCommunication();
        return instance;
    }

    private ServerCommunication(){
        this.serverConn = ServerConnection.getInstance();
        this.parser = new JSONRPC2Parser();
    }

    @Override
    public ArrayList<Treasure> getTreasuresFromServer() {
        return null;
    }

    @Override
    public boolean logInToServer(String uName, String pwd, IResponseCallback callback) {
        JSONRPC2Request request;
        request = new JSONRPC2Request("checklogin", "0");
        Map<String, Object> params = new HashMap<>();
        params.put("username", uName);
        params.put("pwHash", pwd);
        request.setNamedParams(params);
        sendAndWait(request, callback);
        return true;
    }

    @Override
    public void registerUserOnServer(String uName, String email, String pwd, IResponseCallback callback) {
        JSONRPC2Request request;
        request = new JSONRPC2Request("registeruser", "-1");
        Map<String, Object> params = new HashMap<>();
        params.put("username", uName);
        params.put("email", email);
        params.put("pwHash", pwd);
        request.setNamedParams(params);
        sendAndWait(request, callback);
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

    @Override
    public void messageReceived(String payload) {
        JSONRPC2Response response = null;
        try {
            response = parser.parseJSONRPC2Response(payload);
        } catch (JSONRPC2ParseException e) {
            e.printStackTrace();
            Log.d(TAG, "Message Receive: JSON Parse Error!");
            responseCallback.onResponseReceiveError();
            return;
        }

        Log.d(TAG, "Got message: " + payload);
        Log.d(TAG, "Got JSON: "+response);
        responseCallback.onResponseReceived(response);
    }

    private void sendAndWait(JSONRPC2Request req, IResponseCallback callback){
        this.responseCallback = callback;
        Log.i(TAG, "Sending request. "+req.toJSONString());
        ServerConnection.getInstance().sendJSONRequest(req);
    }

}

package at.tba.treasurehunt.servercomm;

import android.util.Log;

import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Parser;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import at.tba.treasurehunt.controller.AuthenticationController;
import at.tba.treasurehunt.controller.LocationController;
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

    private Integer requestID = 1;

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
    public ArrayList<Treasure> getTreasuresFromServer(IResponseCallback callback) {
        JSONRPC2Request request;
        request = new JSONRPC2Request("getalltreasures", (requestID++).toString());
        Integer token = AuthenticationController.getInstance().getLoggedInUserID();
        Map<String, Object> params = new HashMap<>();
        params.put("token", token.toString());
        request.setNamedParams(params);
        sendAndWait(request, callback);
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
    public void getUserById(int userId, IResponseCallback callback) {
        JSONRPC2Request request;
        request = new JSONRPC2Request("getprofiledata", (requestID++).toString());
        Map<String, Object> params = new HashMap<>();
        Integer token = AuthenticationController.getInstance().getLoggedInUserID();
        params.put("token", token.toString());
        params.put("userid", new Integer(userId).toString());
        request.setNamedParams(params);
        sendAndWait(request, callback);
        return;
    }

    @Override
    public void getHighscoreListFromServer(IResponseCallback callback) {
        return ;
    }

    @Override
    public void getHighscoreListInRangeFromServer(int from, int to, IResponseCallback callback) {
        return ;
    }

    @Override
    public void sendOpenTreasureRequest(Treasure t, IResponseCallback callback) {
        JSONRPC2Request request;
        request = new JSONRPC2Request("eventtreasureopened", (requestID++).toString());
        Map<String, Object> params = new HashMap<>();
        Integer token = AuthenticationController.getInstance().getLoggedInUserID();
        params.put("token", token.toString());
        params.put("treasureid", new Integer(t.getId()).toString());
        request.setNamedParams(params);
        sendAndWait(request, callback);
        return;
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
        if (response.getError() == null) { // no error
            responseCallback.onResponseReceived(response);
        }else{ // JSON RPC error
            responseCallback.onResponseReceiveError();
        }
    }

    private void sendAndWait(JSONRPC2Request req, IResponseCallback callback){
        this.responseCallback = callback;
        Log.i(TAG, "Sending request. "+req.toJSONString());
        ServerConnection.getInstance().sendJSONRequest(req);
    }

}

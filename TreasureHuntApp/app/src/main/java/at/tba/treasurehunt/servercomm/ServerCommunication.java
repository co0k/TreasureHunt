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
import at.tba.treasurehunt.tasks.IResponseCallback;
import data_structures.treasure.Treasure;


/**
 * Created by dAmihl on 11.05.15.
 */
public class ServerCommunication implements IServerCommunicationDAO{

    private static final String TAG = "ServerCommunication";

    private static ServerCommunication instance = null;

    private IServerConnection serverConn;
    private JSONRPC2Parser parser;

    private HashMap<Integer, IResponseCallback> responseIdCallbackMap;

    private IResponseCallback lastResponseCallback;

    private Integer requestID = 1;

    public static ServerCommunication getInstance(){
        if (instance == null) instance = new ServerCommunication(ServerConnection.getInstance());
        return instance;
    }

    private ServerCommunication(IServerConnection connection){
        this.serverConn = connection;
        this.parser = new JSONRPC2Parser();
        this.responseIdCallbackMap = new HashMap<>();
    }

    @Override
    public ArrayList<Treasure> getTreasuresFromServer(IResponseCallback callback) {
        JSONRPC2Request request;
        Integer requestId = requestID++;
        request = new JSONRPC2Request("getalltreasures", requestId.toString());
        Integer token = AuthenticationController.getInstance().getLoggedInUserID();
        Map<String, Object> params = new HashMap<>();
        params.put("token", token.toString());
        request.setNamedParams(params);
        sendAndRegisterCallback(request, callback, requestId);
        return null;
    }

    @Override
    public boolean logInToServer(String uName, String pwd, IResponseCallback callback) {
        JSONRPC2Request request;
        Integer requestId = 0;
        request = new JSONRPC2Request("checklogin", requestId.toString());
        Map<String, Object> params = new HashMap<>();
        params.put("username", uName);
        params.put("pwHash", pwd);
        request.setNamedParams(params);
        sendAndRegisterCallback(request, callback, requestId);
        return true;
    }

    @Override
    public void registerUserOnServer(String uName, String email, String pwd, IResponseCallback callback) {
        JSONRPC2Request request;
        Integer requestId = -1;
        request = new JSONRPC2Request("registeruser", requestId.toString());
        Map<String, Object> params = new HashMap<>();
        params.put("username", uName);
        params.put("email", email);
        params.put("pwHash", pwd);
        request.setNamedParams(params);
        sendAndRegisterCallback(request, callback, requestId);
    }

    @Override
    public void getUserById(int userId, IResponseCallback callback) {
        JSONRPC2Request request;
        Integer requestId = requestID++;
        request = new JSONRPC2Request("getprofiledata", requestId.toString());
        Map<String, Object> params = new HashMap<>();
        Integer token = AuthenticationController.getInstance().getLoggedInUserID();
        params.put("token", token.toString());
        params.put("userid", new Integer(userId).toString());
        request.setNamedParams(params);
        sendAndRegisterCallback(request, callback, requestId);
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
        Integer requestId = requestID++;
        request = new JSONRPC2Request("eventtreasureopen", requestId.toString());
        Map<String, Object> params = new HashMap<>();
        Integer token = AuthenticationController.getInstance().getLoggedInUserID();
        params.put("token", token.toString());
        params.put("treasureid", new Integer(t.getId()).toString());
        request.setNamedParams(params);
        sendAndRegisterCallback(request, callback, requestId);
        return;
    }

    @Override
    public void sendOpenTreasureWrongAnswerEvent(Treasure t, IResponseCallback callback) {
        JSONRPC2Request request;
        Integer requestId = requestID++;
        request = new JSONRPC2Request("eventtreasurewronganswer", requestId.toString());
        Map<String, Object> params = new HashMap<>();
        Integer token = AuthenticationController.getInstance().getLoggedInUserID();
        params.put("token", token.toString());
        params.put("treasureid", new Integer(t.getId()).toString());
        params.put("userid",token.toString());
        request.setNamedParams(params);
        sendAndRegisterCallback(request, callback, requestId);
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
            lastResponseCallback.onResponseReceiveError();
            return;
        }

        Integer requestId = Integer.parseInt((String) response.toJSONObject().get("id"));
        lastResponseCallback = responseIdCallbackMap.get(requestId);

        Log.d(TAG, "Got JSON: "+response);

        if (lastResponseCallback == null){
            Log.d(TAG, "No response handler found in the <requestID/Callback>Map! Returning.. This JSON Response will not be handled!");
            return;
        }

        if (response.getError() == null) { // no error
            lastResponseCallback.onResponseReceived(response);
        }else{ // JSON RPC error
            lastResponseCallback.onResponseReceiveError();
        }
    }

    /**
     *  Sends the request to the server.
     *  Puts the (requestId,Callback) pair into the HashMap
     *  the callback Object will be saved in lastResponseCallback to handle errors over this callback
     *
     * @param req the JSONRPC2 Request object
     * @param callback the IResponseCallback object, to receive message callbacks
     * @param requestId the requestId, to put into Callback/ID Hashmap
     */
    private void sendAndRegisterCallback(JSONRPC2Request req, IResponseCallback callback, Integer requestId){
        this.lastResponseCallback = callback;
        this.responseIdCallbackMap.put(requestId, callback);
        Log.i(TAG, "Sending request. "+req.toJSONString());
        this.serverConn.sendJSONRequest(req);
    }

}

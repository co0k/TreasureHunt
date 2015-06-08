package at.tba.treasurehunt.servercomm;


import android.util.Log;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;

import at.tba.treasurehunt.activities.ActivityManager;
import at.tba.treasurehunt.utils.AlertHelper;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

/**
 * Created by dAmihl on 11.05.15.
 */
public class ServerConnection implements IServerConnection {


    private static ServerConnection instance = null;

    public static ServerConnection getInstance(){
        if (instance == null) instance = new ServerConnection();
        return instance;
    }


    private final static String TAG = "TH-ServerConnection";
    private IServerConnectionCallback lastCallbackClass;

    private final WebSocketConnection mConnection = new WebSocketConnection();
    private static final String SERVER_URI = "ws://philipp-m.de:7666/loot/ServerSocket";
   // private static final String SERVER_URI = "ws://echo.websocket.org";

    private boolean serverConnected = false;


    @Override
    public boolean connectServer(final IServerConnectionCallback callback) {

        if (serverConnected){
            callback.onServerConnected();
            Log.i(TAG, "Already connected !");
            return true;
        }

        if (callback != null)
            lastCallbackClass = callback;

        if (callback == null)
           if (lastCallbackClass == null)
               return false;

        try {
            mConnection.connect(SERVER_URI, new WebSocketHandler() {

                @Override
                public void onOpen() {
                    Log.d(TAG, "Status: Connected to " + SERVER_URI);
                    serverConnected = true;
                    ActivityManager.dismissLoadingSpinner();
                    lastCallbackClass.onServerConnected();
                }

                @Override
                public void onTextMessage(String payload) {

                    ServerCommunication.getInstance().messageReceived(payload);
                }

                @Override
                public void onClose(int code, String reason) {
                    ActivityManager.dismissLoadingSpinner();
                    serverConnected = false;
                    AlertHelper.showConnectionErrorAlert(ActivityManager.getCurrentActivity());
                    lastCallbackClass.onServerNotConnected();
                    Log.d(TAG, "Connection lost.");
                }
            });
        } catch (WebSocketException e) {
            Log.d(TAG, e.toString());
            return false;
        }

        return true;
    }

    @Override
    public boolean disconnectServer() {
        mConnection.disconnect();
        serverConnected = false;
        return true;
    }

    @Override
    public boolean sendPackage() {
        return false;
    }

    public boolean isConnected(){
        return serverConnected;
    }

    public IServerConnectionCallback getLastCallbackClass(){
        return lastCallbackClass;
    }


    public boolean sendJSONRequest(JSONRPC2Request req){
        return sendMessage(req.toJSONString());
    }


    public boolean sendMessage(String s){
        if (mConnection == null || !mConnection.isConnected()) return false;
        mConnection.sendTextMessage(s);
        return true;
    }
}

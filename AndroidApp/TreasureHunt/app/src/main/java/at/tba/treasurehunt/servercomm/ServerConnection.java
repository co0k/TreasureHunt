package at.tba.treasurehunt.servercomm;


import android.util.Log;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import at.tba.treasurehunt.activities.ActivityManager;
import at.tba.treasurehunt.activities.HomeActivity;
import at.tba.treasurehunt.debug.Debug;
import at.tba.treasurehunt.utils.AlertHelper;
import de.tavendo.autobahn.WebSocketConnection;
import de.tavendo.autobahn.WebSocketException;
import de.tavendo.autobahn.WebSocketHandler;

/**
 * Created by dAmihl on 11.05.15.
 */
public class ServerConnection implements ServerComm {


    private static ServerConnection instance = null;

    public static ServerConnection getInstance(){
        if (instance == null) instance = new ServerConnection();
        return instance;
    }


    private final static String TAG = "TH-ServerConnection";
    private ServerConnectionCallback lastCallbackClass;

    private final WebSocketConnection mConnection = new WebSocketConnection();
    private static final String SERVER_URI = "ws://philipp-m.de:8887/stocks";
    //private static final String SERVER_URI = "ws://echo.websocket.org";

    private boolean serverConnected = false;


    @Override
    public boolean connectServer(final ServerConnectionCallback callback) {
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
                    mConnection.sendTextMessage("start");
                }

                @Override
                public void onTextMessage(String payload) {
                    Log.d(TAG, "Got echo: " + payload);
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

    public ServerConnectionCallback getLastCallbackClass(){
        return lastCallbackClass;
    }
}

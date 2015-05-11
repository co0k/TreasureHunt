package at.tba.treasurehunt.servercomm;


import android.util.Log;

import java.net.URI;
import java.util.concurrent.TimeUnit;

import at.tba.treasurehunt.debug.Debug;
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

    private final WebSocketConnection mConnection = new WebSocketConnection();
    private static final String SERVER_URI = "ws://173.25.3.179:8080/stocks";
   // private static final String SERVER_URI = "ws://echo.websocket.org";



    @Override
    public boolean connectServer() {
        try {
            mConnection.connect(SERVER_URI, new WebSocketHandler() {

                @Override
                public void onOpen() {
                    Log.d(TAG, "Status: Connected to " + SERVER_URI);
                    mConnection.sendTextMessage("start");
                }

                @Override
                public void onTextMessage(String payload) {
                    Log.d(TAG, "Got echo: " + payload);
                }

                @Override
                public void onClose(int code, String reason) {
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
        return true;
    }

    @Override
    public boolean sendPackage() {
        return false;
    }
}

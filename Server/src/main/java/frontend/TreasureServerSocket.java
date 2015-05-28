package frontend;


//import org.eclipse.jetty.websocket.api.Session;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import frontend.Requests.RequestDecoder;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;

/**
 * Created by nebios on 18.05.15.
 */
@ServerEndpoint(value="/ServerSocket",
        decoders = { RequestDecoder.class }
)
public class TreasureServerSocket {
    private Session session;

    @OnOpen
    public void handleConnect(Session session) {
        this.session = session;
        System.out.println("Client connected: " + session.toString());
    }


    @OnClose
    public void handleClose(Session session, CloseReason reason) {
        System.out.println("Connection closed! - Reason: " + reason);
    }

    /*
    @OnClose
    public void handleClose(int statusCode, String reason) {
        System.out.println("Connection closed with satusCode="
                + statusCode + ", reason=" + reason);
    }
    */

    /*
    @OnMessage
    public void handleMessage(Session session, String message) {
        send(message);
    }
    */

    @OnMessage
    public void handleMessage( Session session, JSONRPC2Request request) {

    }


    private void send(String message) {
        //for (Session session : sessions) {
            try {
                if (session.isOpen()) {
                    //session.getRemote().sendString(message);
                    session.getBasicRemote().sendText(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        //}
    }

    private void stop(Session session) {
        try {
            session.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

package frontend;


//import org.eclipse.jetty.websocket.api.Session;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by nebios on 18.05.15.
 */
@ServerEndpoint(value="/ServerSocket")
public class TreasureServerSocket {
    private Session session;

    @OnOpen
    public void handleConnect(Session session) {
        this.session = session;
        System.out.println("Client connected: " + session.toString());
    }

    /*
    @OnClose
    public void handleClose(int statusCode, String reason) {
        System.out.println("Connection closed with satusCode="
                + statusCode + ", reason=" + reason);
    }
    */

    @OnMessage
    public void handleMessage(Session session, String message) {
        send(message);
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
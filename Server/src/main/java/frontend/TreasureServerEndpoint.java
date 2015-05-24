package frontend;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Created by nebios on 04.05.15.
 */

@WebSocket
public class TreasureServerEndpoint {
    private Session session;

    @OnWebSocketConnect
    public void handleConnect(Session session) { this.session = session; }

    @OnWebSocketClose
    public void handleClose(int statusCode, String reason) {
        System.out.println("Connection closed with satusCode="
                + statusCode + ", reason=" + reason);
    }

    @OnWebSocketMessage
    public void handleMessage(String message) {
        send(message);
    }

    private void send(String message) {
       // for (Session session : sessions) {
            try {
                if (session.isOpen()) {
                     session.getRemote().sendString(message);
                }
            } catch (IOException e) {
                 e.printStackTrace();
            }
       // }
    }

    private void stop(Session session) {
        try {
            session.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

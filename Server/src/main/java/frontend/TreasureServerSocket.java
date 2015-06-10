package frontend;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import frontend.Requests.RequestDecoder;
import frontend.Requests.RequestHandler;
import frontend.Requests.RequestResolver;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;

/**
 * Created by nebios on 18.05.15.
 */
@ServerEndpoint(value="/ServerSocket",
        decoders = { RequestDecoder.class }/*,
        encoders = { ResponseEncoder.class }*/
)
public class TreasureServerSocket {
    private Session session;
    RequestResolver requestResolver;

    @OnOpen
    public void handleConnect(Session session) {
        this.requestResolver = new RequestHandler();
        this.session = session;
        System.out.println("Client connected: " + session.toString() + "\n\n");
    }

    @OnClose
    public void handleClose(Session session, CloseReason reason) {
        System.out.println("Connection closed! - Reason: " + reason);

    }

    @OnMessage
    public void handleMessage( Session session, JSONRPC2Request request) {
        System.err.println("\n\nGot the following request: " + request + "\n\n");
        String response;
        response = requestResolver.handleRequest(request).toString();
        send(response);
    }

    private void send(String message) {
        try {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(message);
                    System.err.println("\n\nSend the following reponse to Client: " + message + "\n\n");
                }
            } catch (IOException e) {
                System.err.println("\n\n Error in Sending Message to Client \n\n");
                e.printStackTrace();
        }
    }

}

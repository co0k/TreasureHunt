package frontend;

import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import frontend.Requests.RequestDecoder;
import frontend.Requests.RequestHandler;
import frontend.Requests.RequestResolver;
import frontend.Requests.ResponsEncoder;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import java.io.IOException;

/**
 * Created by nebios on 18.05.15.
 */
@ServerEndpoint(value="/ServerSocket"/*,
        decoders = { RequestDecoder.class },
        encoders = { ResponsEncoder.class }*/
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

    /*
    @OnClose
    public void handleClose(int statusCode, String reason) {
        System.out.println("Connection closed with satusCode="
                + statusCode + ", reason=" + reason);
    }
    */

    @OnMessage
    public void handleMessage(Session session, String request) {
        JSONRPC2Request jRequest = null;

        try {
            jRequest = JSONRPC2Request.parse(request);
        } catch (JSONRPC2ParseException e) {
            System.err.println("\n\nError parsing request: " + request + "\n\n");
        }

        if (jRequest != null) {
            String response;
            response = requestResolver.handleRequest(jRequest).toString();
            send(response);
        }
    }

    /*
    @OnMessage
    public void handleMessage( Session session, JSONRPC2Request request) {
        String response;
        response = requestResolver.handleRequest(request).toString();
        send(response);
    }
    */

    private void send(String message) {
        try {
                if (session.isOpen()) {
                    session.getBasicRemote().sendText(message);
                }
            } catch (IOException e) {
                System.err.println("\n\n Error in Sending Message to Client \n\n");
                e.printStackTrace();
        }
    }

}

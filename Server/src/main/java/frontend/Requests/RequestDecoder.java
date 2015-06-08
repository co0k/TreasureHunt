package frontend.Requests;

import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Created by nebios on 28.05.15.
 */
public class RequestDecoder implements Decoder.Text<JSONRPC2Request> {

    private JSONRPC2Request requestIn = null;

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public JSONRPC2Request decode(String recieved) throws DecodeException {
        try {
            requestIn = JSONRPC2Request.parse(recieved);
        } catch (JSONRPC2ParseException pex) {
            System.err.println("\n\n Could not parse Request: " + recieved + " \n\n");
        }
        return requestIn;
    }

    @Override
    public boolean willDecode(String recieved) {
        try {
            requestIn = JSONRPC2Request.parse(recieved);
        } catch (JSONRPC2ParseException pex) {
            System.err.println("\n\n Could not parse Request: " + recieved + " \n\n");
            return false;
        }

        return (requestIn != null);
    }
}

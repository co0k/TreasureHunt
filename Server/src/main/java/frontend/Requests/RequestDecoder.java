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

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public JSONRPC2Request decode(String recieved) throws DecodeException {
        //JsonObject jsonObject;
        //jsonObject = Json.createReader(new StringReader(recieved)).readObject();
        //Request request = new Request(jsonObject);
        //return request;
        JSONRPC2Request requestIn = null;
        try {
            requestIn = JSONRPC2Request.parse(recieved);
        } catch (JSONRPC2ParseException pex) {
            System.err.println("Failed to parse the String: " + recieved);
        }

        return requestIn; // for now - ToDo: finish this method
    }

    @Override
    public boolean willDecode(String s) {
        return (s != null);
    }
}

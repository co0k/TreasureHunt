package frontend.Requests;

import com.thetransactioncompany.jsonrpc2.JSONRPC2ParseException;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

/**
 * Created by nebios on 28.05.15.
 */
public class ResponseDecoder implements Decoder.Text<JSONRPC2Response> {

    private JSONRPC2Response response = null;

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }

    @Override
    public JSONRPC2Response decode(String recieved) throws DecodeException { return response; }

    @Override
    public boolean willDecode(String recieved) {
        try {
            response = JSONRPC2Response.parse(recieved);
        } catch (JSONRPC2ParseException e) {
            return false;
        }
        return (response != null);
    }

}

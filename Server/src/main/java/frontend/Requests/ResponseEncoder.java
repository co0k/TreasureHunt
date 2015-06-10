package frontend.Requests;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * Created by nebios on 28.05.15.
 */
public class ResponseEncoder implements Encoder.Text<JSONRPC2Response> {
    @Override
    public String encode(JSONRPC2Response object) throws EncodeException {
        return object.toString();
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}

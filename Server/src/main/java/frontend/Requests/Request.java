package frontend.Requests;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;

import javax.json.Json;
import javax.json.JsonObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by nebios on 28.05.15.
 */
public class Request {
    private String methodName;
    private Map<String, Object> parameters;
    private String id;

    public Request (JSONRPC2Request request) {
        methodName = request.getMethod();
        parameters = request.getNamedParams();
        id = (String) request.getID();
    }

    public String getMethodName() {
        return methodName;
    }

    public Map<String, Object> getParameters() {
        return parameters;
    }

}

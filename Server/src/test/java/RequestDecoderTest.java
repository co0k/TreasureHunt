/**
 * Created by nebios on 28.05.15.
 */
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import communication_controller.json.JsonConstructor;
import data_structures.treasure.Quiz;
import data_structures.treasure.Treasure;
import frontend.Requests.RequestDecoder;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.websocket.DecodeException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class RequestDecoderTest {
    private static RequestDecoder requestDecoder;
    private static JsonConstructor jsonC;

    // the methodname of our test request
    String methodName = "TestMethod";

    HashMap<String, Object> parameters;
    Long  expLong;
    String expString;
    Integer expInt;
    Double expDouble;
    Boolean expBoolean;
    Treasure expTreasure;
    Quiz expQuiz;

    String expID;

    String serializedRequest;



    @Before
    public void setUp() {
        requestDecoder = new RequestDecoder();
        jsonC = new JsonConstructor();

        // standard objects
        expLong = new Long("420000000000000000");
        expString = "theStringParam";
        expInt= 42;
        expDouble = 4.2;
        expBoolean = true;
        // project relevant objects
        expTreasure =  new Treasure(null, null, null, null);
        expQuiz = new Quiz(10, "Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", "Konzerthaus", null, null);

        //set up the JSONRPC2Request
        parameters = new HashMap<String,Object>();
        parameters.put("theString", jsonC.toJson(expString));
        parameters.put("theInteger", jsonC.toJson(expInt));
        parameters.put("theDouble", jsonC.toJson(expDouble));
        parameters.put("theBoolean", jsonC.toJson(expBoolean));
        parameters.put("theLong", jsonC.toJson(expLong));
        parameters.put("theTreasure", jsonC.toJson(expTreasure));
        parameters.put("Quiz", jsonC.toJson(expQuiz));

        // the request id
        expID  = "id-0";

        JSONRPC2Request request = new JSONRPC2Request(methodName, parameters, expID);
        serializedRequest = request.toJSONString();
        //System.err.println(serializedRequest);
    }

    @Test
    public void testMinimalRequest() {
        JSONRPC2Request minimal = new JSONRPC2Request(methodName, expID);
        serializedRequest = minimal.toJSONString();
        JSONRPC2Request recievedRequest = null;
        try {
            recievedRequest = requestDecoder.decode(serializedRequest);
        } catch (DecodeException e) {
            fail();
        }
        String decodedMethodName = recievedRequest.getMethod();
        assertEquals("Parsing methodName went wrong", methodName, decodedMethodName);
    }

    @Test
    public void testMethodname() {
        JSONRPC2Request recievedRequest = null;
        try {
            recievedRequest = requestDecoder.decode(serializedRequest);
        } catch (DecodeException e) {
            fail();
        }
        String decodedMethodName = recievedRequest.getMethod();
        assertEquals("Parsing methodName went wrong", methodName, decodedMethodName);
    }

    @Test
    public void testParams() {
        JSONRPC2Request recievedRequest = null;
        try {
            recievedRequest = requestDecoder.decode(serializedRequest);
        } catch (DecodeException e) {
            fail();
        }

        Map<String, Object> decodedParams = recievedRequest.getNamedParams();
        String RecievedString = jsonC.fromJson((String)decodedParams.get("theString"), String.class );
        Integer RecievedInteger = jsonC.fromJson((String)decodedParams.get("theInteger"), Integer.class);
        Double RecievedDouble = jsonC.fromJson((String)decodedParams.get("theDouble"), Double.class);
        Boolean RecievedBoolean = jsonC.fromJson((String)decodedParams.get("theBoolean"), Boolean.class);
        Treasure RecievedTreasure = jsonC.fromJson((String)decodedParams.get("theTreasure"), Treasure.class);
        Long RecievedLong = jsonC.fromJson((String)decodedParams.get("theLong"), Long.class);
        Quiz recievedQuiz = jsonC.fromJson((String)decodedParams.get("Quiz"), Quiz.class);

        assertEquals("Parsing String param went wrong", expString, RecievedString);
        assertEquals("Parsing Integer param went wrong", expInt, RecievedInteger);
        assertEquals("Parsing Double param went wrong", expDouble, RecievedDouble);
        assertEquals("Parsing Boolean param went wrong", expBoolean, RecievedBoolean);
        assertEquals("Parsing Long param went wrong", expLong, RecievedLong);

        assertEquals("Parsing Treasure went wrong", expTreasure, RecievedTreasure);
        assertEquals("Parsing Quiz went wrong", expQuiz, recievedQuiz);
    }

    @Test
    public void testID() {
        JSONRPC2Request recievedRequest = null;
        try {
            recievedRequest = requestDecoder.decode(serializedRequest);
        } catch (DecodeException e) {
            fail();
        }
        String recievedID = (String) recievedRequest.getID();
        assertEquals("Parsing id went wrong", expID, recievedID);
    }
}

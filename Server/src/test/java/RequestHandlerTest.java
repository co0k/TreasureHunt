import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import communication_controller.json.JsonConstructor;
import data_structures.treasure.Quiz;
import data_structures.treasure.Treasure;
import frontend.Requests.RequestHandler;
import org.junit.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by nebios on 05.06.15.
 */
public class RequestHandlerTest {
    RequestHandler handler;
    JsonConstructor jsonC;

    @Before
    public void init() {
        handler = new RequestHandler();
        jsonC = new JsonConstructor();
    }

    @Test
    public void checkLogInTest() {
        String method = "checkLogIn";
        String username = "tester";
        String pwHash = "unsavepassword";
        String reqID = "id-0-chl";
        Map<String, Object> params = new HashMap<>();
        params.put("userName", username);
        params.put("pwHash", pwHash);
        JSONRPC2Request request = new JSONRPC2Request(method, params, reqID);

        Integer result;
        JSONRPC2Response response = handler.handleRequest(request);
        result = jsonC.fromJson((String)response.getResult(), Integer.class);

        assertNull(result); // this user should not be in the database
    }

    @Test
    public void registerUserTest() {
        String method = "registerUser";
        String reqID = "id-1-ru";
        Map<String, Object> params = new HashMap<>();
        String email = "tester@example.com";
        String username = "tester";
        String pwHash = "unsavepassword";
        params.put("email", jsonC.toJson(email));
        params.put("username", jsonC.toJson(username));
        params.put("pwHash", jsonC.toJson(pwHash));
        JSONRPC2Request request = new JSONRPC2Request(method, params, reqID);
        JSONRPC2Response response = handler.handleRequest(request);
    }

    @Test
    public void getTestTreasureTest() {
        Quiz quiz1 = new Quiz(10, "Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", "Konzerthaus", null, null);
        Treasure.Location testLocation = new Treasure.Location(1, 10, 47.2641234, 11.3451889 );
        Treasure expected  = new Treasure(1, testLocation, quiz1, new Treasure.Size(-1, 20, 1), null);
        String method = "getTestTreasure";
        String reqID = "id-10-tt";
        JSONRPC2Request request = new JSONRPC2Request(method, reqID);
        JSONRPC2Response response = handler.handleRequest(request);
        Treasure result = jsonC.fromJson((String)response.getResult(), Treasure.class);
        assertNotNull(result);
        assertEquals("Treasure is not the expacted one", expected, result);
    }
 }

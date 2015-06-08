import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import communication_controller.json.JsonConstructor;
import data_structures.treasure.Coupon;
import data_structures.treasure.Quiz;
import data_structures.treasure.Treasure;
import db.DatabaseController;
import frontend.Requests.RequestHandler;
import org.junit.*;

import java.lang.reflect.Type;
import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by nebios on 05.06.15.
 */
public class RequestHandlerTest {
    RequestHandler handler;
    JsonConstructor jsonC;
    List<Treasure> exampleTreasures;
    List<Integer> exampleTreasuresID;

    @Before
    public void init() {
        handler = new RequestHandler();
        jsonC = new JsonConstructor();

        String method = "registerUser";
        String reqID = "id-00-init";
        Map<String, Object> params = new HashMap<>();
        String email = "junit@example.com";
        String username = "junit";
        String pwHash = "passwd";
        params.put("email", jsonC.toJson(email));
        params.put("username", jsonC.toJson(username));
        params.put("pwHash", jsonC.toJson(pwHash));
        JSONRPC2Request request = new JSONRPC2Request(method, params, reqID);
        handler.handleRequest(request);

        Quiz quiz1 = new Quiz(10,"Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", "Konzerthaus", null, null);
        Quiz quiz2 = new Quiz(10,"Wo ist der Rechnerraum 15?", "Uni Innsbruck", "dein zuhause", "Bank", "Konzerthaus", null, null);
        Quiz quiz3 = new Quiz(10,"In welchen Gebäude befindet sich Frau Webber?", "ICT Gebäude", "Rathaus", "Bauingenieurgebäude", "Mensa", "Bei dir zuhause", null);
        Quiz quiz4 = new Quiz(10,"Wo bekommt man den besten Kaffee am Campus?", "Jollys", "ICT Gebäude", "Bauingenieurgebäude", "Mensa", null, null);
        Quiz quiz5 = new Quiz(25,"Wofür ist der Alpenzoo bekannt?", "höchstgelegener Zoo Europas", "artenreichster Zoo Europas", "sauberster Zoo Europas", "was ist ein Zoo?", null, null);
        Quiz quiz6 = new Quiz(20,"Welches bekannte Snowboardevent findet alljährlich in Innsbruck statt?", "Air+Style", "PipetoPipe", "AlpinFreeze", "Rail Jam", null, null);
        Quiz quiz7 = new Quiz(15,"Wie viele vergoldete Kupferschindeln wurden beim goldenen Dachl verlegt?", "2.657", "1.529", "403", "86", null, null);
        Quiz quiz8 = new Quiz(30,"Wie viele Bäcker Ruetz gibt es in Innsbruck?", "16", "7", "pro Einwohner einen", "ist schon schlimmer als McDonalds", null, null);


        exampleTreasuresID = new ArrayList<Integer>();
        exampleTreasures = new ArrayList<Treasure>();

        exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.26952, 11.39570), quiz1, new Treasure.Size(-1, 20, 1), new Coupon(10, "SuperDuperMarket", 10.50)));
        exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.263372, 11.345269), quiz2, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.263567, 11.345916), quiz3, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.264659,11.3445717), quiz4, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(25, 47.263567, 11.345916), quiz5, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(20, 47.249058,11.399484), quiz6, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2686516, 11.393286), quiz7, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2675584, 11.3923194), quiz7, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.267785, 11.390727), quiz7, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(30, 47.2635802, 11.3945087), quiz8, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(30, 47.2654258,11.3936075), quiz8, new Treasure.Size(-1, 20, 1), null));


        Collections.sort(exampleTreasures);
        // add all the treasures
        for (Treasure t : exampleTreasures)
            exampleTreasuresID.add(DatabaseController.getInstance().saveTreasure(t));
        for (Integer tmp : exampleTreasuresID) {
			DatabaseController.getInstance().activateTreasure(tmp);
		}
    }

    @After
    public void finish() {
        // clean the database
        for (Integer tmp : exampleTreasuresID) {
            DatabaseController.getInstance().deactivateTreasure(tmp);
        }
        DatabaseController.getInstance().deleteAll();
    }

    @Test
    public void checkLogInTest() {
        String method = "checkLogIn";
        String username = "notexisting";
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
    public void checkLogInTest2() {
        Map<String, Object> params = new HashMap<>();
        String email = "junit@example.com";
        String username = "junit";
        String pwHash = "passwd";
        params.put("email", jsonC.toJson(email));
        params.put("username", jsonC.toJson(username));
        params.put("pwHash", jsonC.toJson(pwHash));
        String method = "checkLogIn";
        String reqID = "id-1-chl";
        JSONRPC2Request request = new JSONRPC2Request(method, params, reqID);
        JSONRPC2Response response = handler.handleRequest(request);
        Integer result = jsonC.fromJson((String)response.getResult(), Integer.class);
        assertNotNull(result); // this user should be in the database;
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
        assertNotNull("response is null", response);
        //System.err.println(response);
        Boolean result = jsonC.fromJson((String)response.getResult(),Boolean.class);
        assertNotNull("token is null", result);
        assertTrue("registration failed", result);
    }

    @Test
    public void getAllTreasuresTest() {
        Gson gson = new Gson();
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> paramsL = new HashMap<>();

        String email = "junit@example.com";
        String username = "junit";
        String pwHash = "passwd";
        paramsL.put("email", jsonC.toJson(email));
        paramsL.put("username", jsonC.toJson(username));
        paramsL.put("pwHash", jsonC.toJson(pwHash));
        JSONRPC2Request requestL = new JSONRPC2Request("checkLogIn", paramsL, "id-2-chl");
        JSONRPC2Response responseL = handler.handleRequest(requestL);
        assertNotNull("response after login is null", responseL);
        Integer token = jsonC.fromJson((String) responseL.getResult(), Integer.class);
        assertNotNull("token null", token);

        params.put("token", jsonC.toJson(token));
        JSONRPC2Request request = new JSONRPC2Request("getAllTreasures", params, "id-2-at");
        JSONRPC2Response response = handler.handleRequest(request);
        // TODO: fix Json parsing
        Treasure[] treasures = jsonC.fromJson((String) response.getResult(), Treasure[].class);
        List<Treasure> tList = new ArrayList<>(Arrays.asList(treasures));
        assertNotNull("treasure null!", treasures);
        Collections.sort(tList);
        assertEquals(exampleTreasures.size(), tList.size());
        Collections.sort(tList);
        for(int i = 0; i < exampleTreasures.size(); i++)
            assertTrue("the treasures aren't equal", exampleTreasures.get(i).equalsWithoutId(tList.get(i)));
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

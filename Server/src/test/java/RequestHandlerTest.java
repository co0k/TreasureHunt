import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import communication_controller.json.JsonConstructor;
import core.CoreModel;
import data_structures.treasure.Coupon;
import data_structures.treasure.Quiz;
import data_structures.treasure.Treasure;
import data_structures.user.HighscoreList;
import data_structures.user.User;
import db.DatabaseController;
import frontend.Requests.RequestHandler;
import org.junit.*;

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
        params.put("email", (email));
        params.put("username", (username));
        params.put("pwHash", (pwHash));
        JSONRPC2Request request = new JSONRPC2Request(method, params, reqID);
        handler.handleRequest(request);


        Quiz quiz1 = new Quiz(10, "Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", "Konzerthaus", null, null);
        Quiz quiz2 = new Quiz(10, "Wo ist der Rechnerraum 15?", "Uni Innsbruck", "dein zuhause", "Bank", "Konzerthaus", null, null);
        Quiz quiz3 = new Quiz(10, "In welchen Gebäude befindet sich Frau Webber?", "ICT Gebäude", "Rathaus", "Bauingenieurgebäude", "Mensa", "Bei dir zuhause", null);
        Quiz quiz4 = new Quiz(10, "Wo bekommt man den besten Kaffee am Campus?", "Jollys", "ICT Gebäude", "Bauingenieurgebäude", "Mensa", null, null);
        Quiz quiz5 = new Quiz(25, "Wofür ist der Alpenzoo bekannt?", "höchstgelegener Zoo Europas", "artenreichster Zoo Europas", "sauberster Zoo Europas", "was ist ein Zoo?", null, null);
        Quiz quiz6 = new Quiz(20, "Welches bekannte Snowboardevent findet alljährlich in Innsbruck statt?", "Air+Style", "PipetoPipe", "AlpinFreeze", "Rail Jam", null, null);
        Quiz quiz7 = new Quiz(15, "Wie viele vergoldete Kupferschindeln wurden beim goldenen Dachl verlegt?", "2.657", "1.529", "403", "86", null, null);
        Quiz quiz8 = new Quiz(30, "Wie viele Bäcker Ruetz gibt es in Innsbruck?", "16", "7", "pro Einwohner einen", "ist schon schlimmer als McDonalds", null, null);


        exampleTreasuresID = new ArrayList<Integer>();
        exampleTreasures = new ArrayList<Treasure>();

        exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.26952, 11.39570), quiz1, new Treasure.Size(-1, 20, 1), new Coupon(10, "SuperDuperMarket", 10.50)));
        exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.263372, 11.345269), quiz2, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.263567, 11.345916), quiz3, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.264659, 11.3445717), quiz4, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(25, 47.263567, 11.345916), quiz5, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(20, 47.249058, 11.399484), quiz6, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2686516, 11.393286), quiz7, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2675584, 11.3923194), quiz7, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.267785, 11.390727), quiz7, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(30, 47.2635802, 11.3945087), quiz8, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(30, 47.2654258, 11.3936075), quiz8, new Treasure.Size(-1, 20, 1), null));

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
        DatabaseController.getInstance().deleteAll();
        // reset the core model
        CoreModel.getInstance().reset();
    }

    /**
     * Checks if the login of an unregistered user fails
     * tested method: checkLogIn()
     */
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
        result = jsonC.fromJson((String) response.getResult(), Integer.class);

        assertNull(result); // this user should not be in the database
    }

    /**
     * Checks if the login of a registered user succeeds
     * tested method: checkLogIn()
     */
    @Test
    public void checkLogInTest2() {
        Map<String, Object> params = new HashMap<>();
        String email = "junit@example.com";
        String username = "junit";
        String pwHash = "passwd";
        params.put("email", (email));
        params.put("username", (username));
        params.put("pwHash", (pwHash));
        String method = "checkLogIn";
        String reqID = "id-1-chl";
        JSONRPC2Request request = new JSONRPC2Request(method, params, reqID);
        JSONRPC2Response response = handler.handleRequest(request);
        Integer result = jsonC.fromJson((String) response.getResult(), Integer.class);
        assertNotNull(result); // this user should be in the database;
    }

    /**
     * Checks if it is possible to register a new user
     * tested method: registerUser()
     */
    @Test
    public void registerUserTest() {
        String method = "registerUser";
        String reqID = "id-1-ru";
        Map<String, Object> params = new HashMap<>();
        String email = "tester@example.com";
        String username = "tester";
        String pwHash = "unsavepassword";
        params.put("email", (email));
        params.put("username", (username));
        params.put("pwHash", (pwHash));
        JSONRPC2Request request = new JSONRPC2Request(method, params, reqID);
        JSONRPC2Response response = handler.handleRequest(request);
        assertNotNull("response is null", response);
        Boolean result = jsonC.fromJson((String) response.getResult(), Boolean.class);
        assertNotNull("token is null", result);
        assertTrue("registration failed", result);
    }

    /**
     * Checks if the user is able to edit his/her password
     * tested method: editUser()
     */
    @Test
    public void editUserPasswordTest() {
        // user needs to login
        // before he/she can delete his/her account
        Map<String, Object> params = new HashMap<>();
        String email = "junit@example.com";
        String username = "junit";
        String pwHash = "passwd";
        params.put("email", (email));
        params.put("username", (username));
        params.put("pwHash", (pwHash));
        String method = "checkLogIn";
        String reqID = "id-1-chl";
        JSONRPC2Request request = new JSONRPC2Request(method, params, reqID);
        JSONRPC2Response response = handler.handleRequest(request);
        Integer token = jsonC.fromJson((String) response.getResult(), Integer.class);
        assertNotNull(token);
        // send request to edit the account
        Map<String, Object> paramEdit = new HashMap<>();
        paramEdit.put("token", jsonC.toJson(token));
        paramEdit.put("email", email);
        paramEdit.put("username", username);
        paramEdit.put("pwHash", "newpasswd");
        JSONRPC2Request requestEdit = new JSONRPC2Request("editUser", paramEdit, "id-2-du");
        JSONRPC2Response responseEdit = handler.handleRequest(requestEdit);
        assertNotNull("response after Edit is null", responseEdit);
        Boolean success = jsonC.fromJson((String) responseEdit.getResult(), Boolean.class);
        assertNotNull(success);
        assertTrue("Edit did not succeed", success);
        // try to login with old password
        response = handler.handleRequest(request);
        assertNotNull("Response after 2nd login null", response);
        token = jsonC.fromJson((String) response.getResult(), Integer.class);
        assertNull("User was able to login with old password", token);
        // try to login with new password
        request = new JSONRPC2Request(method, paramEdit, reqID);
        response = handler.handleRequest(request);
        token = jsonC.fromJson((String) response.getResult(), Integer.class);
        assertNotNull("Response after 3nd login null", response);
        assertNotNull("User was not able to login with new password", token);
    }

    /**
     * Checks if the user is able to edit his/her email address
     * tested method: editUser()
     */
    @Test
    public void editUserEmailTest() {
        // user needs to login
        // before he/she can delete his/her account
        Map<String, Object> params = new HashMap<>();
        String email = "junit@example.com";
        String username = "junit";
        String pwHash = "passwd";
        params.put("email", (email));
        params.put("username", (username));
        params.put("pwHash", (pwHash));
        String method = "checkLogIn";
        String reqID = "id-1-chl";
        JSONRPC2Request request = new JSONRPC2Request(method, params, reqID);
        JSONRPC2Response response = handler.handleRequest(request);
        Integer token = jsonC.fromJson((String) response.getResult(), Integer.class);
        assertNotNull(token);
        // send request to edit the account
        Map<String, Object> paramEdit = new HashMap<>();
        String newMail = "junitsnewmail@example.com";
        paramEdit.put("token", jsonC.toJson(token));
        paramEdit.put("email", newMail);
        paramEdit.put("username", username);
        paramEdit.put("pwHash", pwHash);
        JSONRPC2Request requestDel = new JSONRPC2Request("editUser", paramEdit, "id-2-du");
        JSONRPC2Response responseDel = handler.handleRequest(requestDel);
        assertNotNull("response after Edit is null", responseDel);
        Boolean success = jsonC.fromJson((String) responseDel.getResult(), Boolean.class);
        assertNotNull(success);
        assertTrue("Edit did not succeed", success);
        // try to login again
        response = handler.handleRequest(request);
        assertNotNull("Response after 2nd login null", response);
        token = jsonC.fromJson((String) response.getResult(), Integer.class);
        assertNotNull("User was not able to login after Edit", token);
        // get new profile
        Map<String, Object> paramProfile = new HashMap<>();
        paramProfile.put("token", jsonC.toJson(token));
        request = new JSONRPC2Request("getProfileData", paramProfile, "id-2-ubn");
        response = handler.handleRequest(request);
        // check the new profile
        User user = jsonC.fromJson((String) response.getResult(), User.class);
        assertNotNull("no user returned", user);
        assertEquals("Server returned wrong user", username, user.getName());
        assertEquals("Server returned wrong user", newMail, user.getEmail());
    }

    /**
     * Checks if a user is _not_ able to login again after deleting the account
     * tested method: deleteUser()
     */
    @Test
    public void deleteUserTest() {
        // user needs to login
        // before he/she can delete his/her account
        Map<String, Object> params = new HashMap<>();
        String email = "junit@example.com";
        String username = "junit";
        String pwHash = "passwd";
        params.put("email", (email));
        params.put("username", (username));
        params.put("pwHash", (pwHash));
        String method = "checkLogIn";
        String reqID = "id-1-chl";
        JSONRPC2Request request = new JSONRPC2Request(method, params, reqID);
        JSONRPC2Response response = handler.handleRequest(request);
        Integer token = jsonC.fromJson((String) response.getResult(), Integer.class);
        assertNotNull(token);

        // send request to delete the account
        Map<String, Object> paramDel = new HashMap<>();
        paramDel.put("token", jsonC.toJson(token));
        JSONRPC2Request requestDel = new JSONRPC2Request("deleteUser", paramDel, "id-2-du");
        JSONRPC2Response responseDel = handler.handleRequest(requestDel);
        assertNotNull("response after Delete is null", responseDel);
        Boolean success = jsonC.fromJson((String) responseDel.getResult(), Boolean.class);
        assertNotNull(success);
        assertTrue("Deletion did not succeed", success);

        // try to login into the old deleted account
        response = handler.handleRequest(request);
        assertNotNull("Response after 2nd login null", response);
        token = jsonC.fromJson((String) response.getResult(), Integer.class);
        assertNull("Deleted user was able to login", token);
    }

    /**
     * Checks if it is possible to get the users profile by his/her uid
     * method tested: getUserProfile()
     */
    @Test
    public void getUserProfileTest() {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> paramsL = new HashMap<>();

        String email = "junit@example.com";
        String username = "junit";
        String pwHash = "passwd";
        paramsL.put("email", (email));
        paramsL.put("username", (username));
        paramsL.put("pwHash", (pwHash));
        JSONRPC2Request requestL = new JSONRPC2Request("checkLogIn", paramsL, "id-2-chl");
        JSONRPC2Response responseL = handler.handleRequest(requestL);
        assertNotNull("response after login is null", responseL);
        Integer token = jsonC.fromJson((String) responseL.getResult(), Integer.class);
        assertNotNull("token null", token);

        params.put("token", jsonC.toJson(token));
        JSONRPC2Request request = new JSONRPC2Request("getProfileData", params, "id-2-ubn");
        JSONRPC2Response response = handler.handleRequest(request);
        assertNotNull("response after login is null", responseL);

        User user = jsonC.fromJson((String) response.getResult(), User.class);
        assertNotNull("no user returned", user);
        assertEquals("Server returned wrong user", username, user.getName());
        assertEquals("Server returned wrong user", email, user.getEmail());
    }

    /**
     * Checks if the RequestHandler returns _atleast_ the treasures added
     * by the @Before initialization method
     * tested method: getAllTreasures()
     */
    @Test
    public void getAllTreasuresTest() {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> paramsL = new HashMap<>();

        String email = "junit@example.com";
        String username = "junit";
        String pwHash = "passwd";
        paramsL.put("email", (email));
        paramsL.put("username", (username));
        paramsL.put("pwHash", (pwHash));
        JSONRPC2Request requestL = new JSONRPC2Request("checkLogIn", paramsL, "id-2-chl");
        JSONRPC2Response responseL = handler.handleRequest(requestL);
        assertNotNull("response after login is null", responseL);
        Integer token = jsonC.fromJson((String) responseL.getResult(), Integer.class);
        assertNotNull("token null", token);

        params.put("token", jsonC.toJson(token));
        JSONRPC2Request request = new JSONRPC2Request("getAllTreasures", params, "id-2-at");
        JSONRPC2Response response = handler.handleRequest(request);

        Treasure[] treasures = jsonC.fromJson((String) response.getResult(), Treasure[].class);
        List<Treasure> tList = new ArrayList<>(Arrays.asList(treasures));
        assertNotNull("treasure null!", treasures);
        Collections.sort(tList);
        assertEquals(exampleTreasures.size(), tList.size());
        Collections.sort(tList);
        for (int i = 0; i < exampleTreasures.size(); i++)
            assertTrue("the treasures aren't equal", exampleTreasures.get(i).equalsWithoutId(tList.get(i)));
    }

    /**
     * Checks if the RequestHandler returns _atleast_ one close by treasure.
     * This must be the case, as the treasures inserted by the init()
     * and the GeoLeocation are chosen that way
     * tested method: getNearTreasures()
     */
    @Test
    public void getNearestTreasureTest() {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> paramsL = new HashMap<>();

        String email = "junit@example.com";
        String username = "junit";
        String pwHash = "passwd";

        paramsL.put("email", (email));
        paramsL.put("username", (username));
        paramsL.put("pwHash", (pwHash));

        JSONRPC2Request requestL = new JSONRPC2Request("checkLogIn", paramsL, "id-3-chl");
        JSONRPC2Response responseL = handler.handleRequest(requestL);
        assertNotNull("response after login is null", responseL);

        Integer token = jsonC.fromJson((String) responseL.getResult(), Integer.class);
        assertNotNull("token null", token);

        // 47.26952, 11.39570 coords of the first treasure
        Double latitude = 47.26952;
        Double longitude = 11.39570;
        params.put("token", jsonC.toJson(token));
        params.put("longitude", jsonC.toJson(longitude));
        params.put("latitude", jsonC.toJson(latitude));
        JSONRPC2Request request = new JSONRPC2Request("getNearTreasures", params, "id-3-at");
        JSONRPC2Response response = handler.handleRequest(request);
        assertNotNull(response);
        Treasure[] treasures = jsonC.fromJson((String) response.getResult(), Treasure[].class);
        assertNotNull(treasures);
    }

    /**
     * Checks if the RequestHandler returns _atleast_ one treasure inside a certain radius.
     * This must be the case, as the treasures inserted by the init()
     * and the GeoLeocation are chosen that way
     * tested method: getNearTreasures()
     */
    @Test
    public void getNearTreasureTestWithRadius() {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> paramsL = new HashMap<>();

        // user has to login first
        String email = "junit@example.com";
        String username = "junit";
        String pwHash = "passwd";
        paramsL.put("email", (email));
        paramsL.put("username", (username));
        paramsL.put("pwHash", (pwHash));

        JSONRPC2Request requestL = new JSONRPC2Request("checkLogIn", paramsL, "id-3-chl");
        JSONRPC2Response responseL = handler.handleRequest(requestL);
        assertNotNull("response after login is null", responseL);

        Integer token = jsonC.fromJson((String) responseL.getResult(), Integer.class);
        assertNotNull("token null", token);

        // 47.26952, 11.39570 coords of the first treasure
        Double latitude = 47.26952;
        Double longitude = 11.39570;
        Double radius = 50d;
        params.put("token", jsonC.toJson(token));
        params.put("longitude", jsonC.toJson(longitude));
        params.put("latitude", jsonC.toJson(latitude));
        params.put("radius", jsonC.toJson(radius));
        JSONRPC2Request request = new JSONRPC2Request("getNearTreasures", params, "id-3-at");

        JSONRPC2Response response = handler.handleRequest(request);
        assertNotNull(response);

        Treasure[] treasures = jsonC.fromJson((String) response.getResult(), Treasure[].class);
        assertNotNull(treasures);
    }

    @Test
    public void eventTreasureOpenTest() {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> paramsL = new HashMap<>();
        // user has to login first
        String email = "junit@example.com";
        String username = "junit";
        String pwHash = "passwd";
        paramsL.put("email", (email));
        paramsL.put("username", (username));
        paramsL.put("pwHash", (pwHash));
        // send login request
        JSONRPC2Request requestL = new JSONRPC2Request("checkLogIn", paramsL, "id-3-chl");
        JSONRPC2Response responseL = handler.handleRequest(requestL);
        assertNotNull("response after login is null", responseL);
        Integer token = jsonC.fromJson((String) responseL.getResult(), Integer.class);
        assertNotNull("token null", token);
        // send request to open open a treasure chest
        params.put("token", jsonC.toJson(token));
        assertNotNull(exampleTreasuresID);
        assertNotNull(exampleTreasuresID.get(0));
        params.put("treasureID", jsonC.toJson(exampleTreasuresID.get(0)));
        JSONRPC2Request request = new JSONRPC2Request("eventTreasureOpen", params, "id-3-at");
        JSONRPC2Response response = handler.handleRequest(request);
        // check response
        assertNotNull(response);
        Boolean result = jsonC.fromJson((String) response.getResult(), Boolean.class);
        assertNotNull(result);
        assertTrue("treasure reservation failed", result);
    }

    @Test
    public void getHighscoreListTest() {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> paramsL = new HashMap<>();
        // user has to login first
        String email = "junit@example.com";
        String username = "junit";
        String pwHash = "passwd";
        paramsL.put("email", (email));
        paramsL.put("username", (username));
        paramsL.put("pwHash", (pwHash));
        // send login request
        JSONRPC2Request requestL = new JSONRPC2Request("checkLogIn", paramsL, "id-3-chl");
        JSONRPC2Response responseL = handler.handleRequest(requestL);
        assertNotNull("response after login is null", responseL);
        Integer token = jsonC.fromJson((String) responseL.getResult(), Integer.class);
        assertNotNull("token null", token);
        // send request for highscore list
        params.put("token", jsonC.toJson(token));
        params.put("low", jsonC.toJson(1));
        params.put("high", jsonC.toJson(10));
        JSONRPC2Request request = new JSONRPC2Request("getHighscoreList", params, "id-3-ghl");
        JSONRPC2Response response = handler.handleRequest(request);
        // check the response
        assertNotNull("Response after getHighscore is null", response);
        HighscoreList result = jsonC.fromJson((String) response.getResult(), HighscoreList.class);
        assertNotNull("HighscoreList is null", result);

    }

    /**
     * Checks if the RequestHandler returns _exactly_ one specified test treasure
     * tested method: getTestTreasure()
     */
    @Test
    public void getTestTreasureTest() {
        Quiz quiz1 = new Quiz(10, "Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", "Konzerthaus", null, null);
        Treasure.Location testLocation = new Treasure.Location(1, 10, 47.2641234, 11.3451889);
        Treasure expected = new Treasure(1, testLocation, quiz1, new Treasure.Size(-1, 20, 1), null);
        String method = "getTestTreasure";
        String reqID = "id-10-tt";
        JSONRPC2Request request = new JSONRPC2Request(method, reqID);
        JSONRPC2Response response = handler.handleRequest(request);
        Treasure result = jsonC.fromJson((String) response.getResult(), Treasure.class);
        assertNotNull(result);
        assertEquals("Treasure is not the expacted one", expected, result);
    }
}
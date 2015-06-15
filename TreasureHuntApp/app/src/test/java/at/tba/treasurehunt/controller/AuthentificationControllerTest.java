package at.tba.treasurehunt.controller;

import at.tba.treasurehunt.servercomm.ServerCommunication;
import at.tba.treasurehunt.tasks.IResponseCallback;
import communication_controller.json.JsonConstructor;
import data_structures.treasure.Coupon;
import data_structures.treasure.Quiz;
import data_structures.treasure.Treasure;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import org.junit.*;
import static org.junit.Assert.*;

import java.lang.InterruptedException;
import java.util.ArrayList;
import java.util.List;

public class AuthentificationControllerTest {

    private ServerConnectionMockUp serverConnectionMockUp;
    private List<Treasure> exampleTreasures;
    private ServerCommunication serverCommunication;
    @Before
    public void initialize() {
        Quiz quiz1 = new Quiz(10,"Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", "Konzerthaus", null, null);
        Quiz quiz2 = new Quiz(10,"Wo ist der Rechnerraum 15?", "Uni Innsbruck", "dein zuhause", "Bank", "Konzerthaus", null, null);
        Quiz quiz3 = new Quiz(10,"In welchen Gebäude befindet sich Frau Webber?", "ICT Gebäude", "Rathaus", "Bauingenieurgebäude", "Mensa", "Bei dir zuhause", null);
        Quiz quiz4 = new Quiz(10,"Wo bekommt man den besten Kaffee am Campus?", "Jollys", "ICT Gebäude", "Bauingenieurgebäude", "Mensa", null, null);
        Quiz quiz5 = new Quiz(25,"Wofür ist der Alpenzoo bekannt?", "höchstgelegener Zoo Europas", "artenreichster Zoo Europas", "sauberster Zoo Europas", "was ist ein Zoo?", null, null);
        Quiz quiz6 = new Quiz(20,"Welches bekannte Snowboardevent findet alljährlich in Innsbruck statt?", "Air+Style", "PipetoPipe", "AlpinFreeze", "Rail Jam", null, null);
        Quiz quiz7 = new Quiz(15,"Wie viele vergoldete Kupferschindeln wurden beim goldenen Dachl verlegt?", "2.657", "1.529", "403", "86", null, null);

        exampleTreasures = new ArrayList<Treasure>();

        exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.26952, 11.39570), quiz1, new Treasure.Size(-1, 20, 1), new Coupon(10, "SuperDuperMarket", 10.50)));
        exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.263372, 11.345269), quiz2, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.263567, 11.345916), quiz3, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.264659,11.3445717), quiz4, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(25, 47.263567, 11.345916), quiz5, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(20, 47.249058,11.399484), quiz6, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2686516, 11.393286), quiz7, new Treasure.Size(-1, 20, 1), null));
        exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2675584, 11.3923194), quiz7, new Treasure.Size(-1, 20, 1), null));
    }

    @Test
    public void checkUserLoginTest() {
        serverConnectionMockUp = new ServerConnectionMockUp(null, null, null, 95);
        serverCommunication = new ServerCommunication(serverConnectionMockUp);
        serverConnectionMockUp.setServerCommunication(serverCommunication);
        final ObjectWrapper<Boolean> isFinished = new ObjectWrapper<Boolean>(false);
        IResponseCallback callback = new IResponseCallback() {
            @Override
            public void onResponseReceived(JSONRPC2Response response) {
                assertEquals("id is not equal to the checklogin id", response.getID(), "0");
                assertEquals("token is not equal to the checklogin token", response.getResult(), "95");
                isFinished.setObject(true);
            }

            @Override
            public void onResponseReceiveError() {
                fail("error");
            }
        };
        serverCommunication.logInToServer("user", "asdfwefavw", callback);

        waitUntilFinished(isFinished);
    }

    @Test
    public void registerUserTest() {
        serverConnectionMockUp = new ServerConnectionMockUp(null, null, null, 95);
        serverCommunication = new ServerCommunication(serverConnectionMockUp);
        serverConnectionMockUp.setServerCommunication(serverCommunication);
        final ObjectWrapper<Boolean> isFinished = new ObjectWrapper<Boolean>(false);
        IResponseCallback callback = new IResponseCallback() {
            @Override
            public void onResponseReceived(JSONRPC2Response response) {
                assertEquals("id is not equal to the registerUser id", response.getID(), "-1");
                assertEquals("token is not equal to the registerUser token", response.getResult(), "95");
                isFinished.setObject(true);
            }

            @Override
            public void onResponseReceiveError() {
                fail("error");
            }
        };
        serverCommunication.registerUserOnServer("user", "email", "pwdHash", callback);

        waitUntilFinished(isFinished);
    }

    @Test
    public void getNearestTreasureTest(){
        serverConnectionMockUp = new ServerConnectionMockUp(exampleTreasures, null, null, 95);
        serverCommunication = new ServerCommunication(serverConnectionMockUp);
        serverConnectionMockUp.setServerCommunication(serverCommunication);
        final ObjectWrapper<Boolean> isFinished = new ObjectWrapper<Boolean>(false);
        IResponseCallback callback = new IResponseCallback() {
            @Override
            public void onResponseReceived(JSONRPC2Response response) {
                JsonConstructor jsonC = new JsonConstructor();
                assertEquals("id is not equal to the registerUser id", response.getID(), "1");
                assertEquals("token is not equal to the registerUser token", response.getResult(), jsonC.toJson(exampleTreasures));
                isFinished.setObject(true);
            }

            @Override
            public void onResponseReceiveError() {
                fail("error");
            }
        };
        serverCommunication.getNearTreasuresFromServer(callback, 47.26952, 11.39570);
        waitUntilFinished(isFinished);
    }

    @Test
    public void getAllTreasuresTest(){
        serverConnectionMockUp = new ServerConnectionMockUp(exampleTreasures, null, null, 95);
        serverCommunication = new ServerCommunication(serverConnectionMockUp);
        serverConnectionMockUp.setServerCommunication(serverCommunication);
        final ObjectWrapper<Boolean> isFinished = new ObjectWrapper<Boolean>(false);
        IResponseCallback callback = new IResponseCallback() {
            @Override
            public void onResponseReceived(JSONRPC2Response response) {
                JsonConstructor jsonC = new JsonConstructor();
                assertEquals("id is not equal to the registerUser id", response.getID(), "1");
                assertEquals("token is not equal to the registerUser token", response.getResult(), jsonC.toJson(exampleTreasures));
                isFinished.setObject(true);
            }

            @Override
            public void onResponseReceiveError() {
                fail("error");
            }
        };
        serverCommunication.getAllTreasuresFromServer(callback);
        waitUntilFinished(isFinished);
    }

    @Test
    public void getUserByIdTest(){
        serverConnectionMockUp = new ServerConnectionMockUp(null, null, null, 95);
        serverCommunication = new ServerCommunication(serverConnectionMockUp);
        serverConnectionMockUp.setServerCommunication(serverCommunication);
        final ObjectWrapper<Boolean> isFinished = new ObjectWrapper<Boolean>(false);
        IResponseCallback callback = new IResponseCallback() {
            @Override
            public void onResponseReceived(JSONRPC2Response response) {
                assertEquals("id is not equal to the registerUser id", response.getID(), "1");
                assertEquals("token is not equal to the registerUser token", response.getResult(), "95");
                isFinished.setObject(true);
            }

            @Override
            public void onResponseReceiveError() {
                fail("error");
            }
        };
        serverCommunication.getUserById(1, callback);
        waitUntilFinished(isFinished);
    }

    @Test
    public void getHighscoreFromServerTest() {
        serverConnectionMockUp = new ServerConnectionMockUp(null, null, null, 95);
        serverCommunication = new ServerCommunication(serverConnectionMockUp);
        serverConnectionMockUp.setServerCommunication(serverCommunication);
        final ObjectWrapper<Boolean> isFinished = new ObjectWrapper<Boolean>(false);
        IResponseCallback callback = new IResponseCallback() {
            @Override
            public void onResponseReceived(JSONRPC2Response response) {
                assertEquals("id is not equal to the registerUser id", response.getID(), "1");
                assertEquals("token is not equal to the registerUser token", response.getResult(), "95");
                isFinished.setObject(true);
            }

            @Override
            public void onResponseReceiveError() {
                fail("error");
            }
        };
        serverCommunication.getHighscoreListFromServer(callback);
        waitUntilFinished(isFinished);
    }

    @Test
    public void sendOpenTreasureRequestTest() {
        Quiz testQuiz = new Quiz(10,"Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", "Konzerthaus", null, null);
        Treasure t = new Treasure(new Treasure.Location(10, 47.26952, 11.39570), testQuiz, new Treasure.Size(-1, 20, 1), new Coupon(10, "SuperDuperMarket", 10.50));
        final List<Treasure> openTest = new ArrayList<Treasure>();
        openTest.add(t);
        serverConnectionMockUp = new ServerConnectionMockUp(openTest, null, null, 95);
        serverCommunication = new ServerCommunication(serverConnectionMockUp);
        serverConnectionMockUp.setServerCommunication(serverCommunication);
        final ObjectWrapper<Boolean> isFinished = new ObjectWrapper<Boolean>(false);
        IResponseCallback callback = new IResponseCallback() {
            @Override
            public void onResponseReceived(JSONRPC2Response response) {
                JsonConstructor jsonC = new JsonConstructor();
                assertEquals("id is not equal to the registerUser id", response.getID(), "1");
                assertEquals("token is not equal to the registerUser token", response.getResult(), jsonC.toJson(openTest));
                isFinished.setObject(true);
            }

            @Override
            public void onResponseReceiveError() {
                fail("error");
            }
        };
        serverCommunication.sendOpenTreasureRequest(t, callback);
        waitUntilFinished(isFinished);
    }

    @Test
    public void getHighscoreListInRangeFromServerTest() {
        serverConnectionMockUp = new ServerConnectionMockUp(exampleTreasures, null, null, 95);
        serverCommunication = new ServerCommunication(serverConnectionMockUp);
        serverConnectionMockUp.setServerCommunication(serverCommunication);
        final ObjectWrapper<Boolean> isFinished = new ObjectWrapper<Boolean>(false);
        IResponseCallback callback = new IResponseCallback() {
            @Override
            public void onResponseReceived(JSONRPC2Response response) {
                assertEquals("id is not equal to the registerUser id", response.getID(), "1");
                assertEquals("token is not equal to the registerUser token", response.getResult(), "95");
                isFinished.setObject(true);
            }

            @Override
            public void onResponseReceiveError() {
                fail("error");
            }
        };
        serverCommunication.getHighscoreListFromServer(callback);
        waitUntilFinished(isFinished);
    }

    @Test
    public void sendOpenTreasureWrongAnswerEventTest() {
        serverConnectionMockUp = new ServerConnectionMockUp(exampleTreasures, null, null, 95);
        serverCommunication = new ServerCommunication(serverConnectionMockUp);
        serverConnectionMockUp.setServerCommunication(serverCommunication);
        final ObjectWrapper<Boolean> isFinished = new ObjectWrapper<Boolean>(false);
        IResponseCallback callback = new IResponseCallback() {
            @Override
            public void onResponseReceived(JSONRPC2Response response) {
                assertEquals("id is not equal to the registerUser id", response.getID(), "1");
                assertEquals("token is not equal to the registerUser token", response.getResult(), "95");
                isFinished.setObject(true);
            }

            @Override
            public void onResponseReceiveError() {
                fail("error");
            }
        };
        Quiz testQuiz = new Quiz(10,"Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", "Konzerthaus", null, null);
        Treasure t = new Treasure(new Treasure.Location(10, 47.26952, 11.39570), testQuiz, new Treasure.Size(-1, 20, 1), new Coupon(10, "SuperDuperMarket", 10.50));
        serverCommunication.sendOpenTreasureWrongAnswerEvent(t, callback);
        waitUntilFinished(isFinished);
    }

private class ObjectWrapper<T> {
    private T object;

    public ObjectWrapper(T object) {
        this.object = object;
    }

    public T getObject() {
        return this.object;
    }

    public void setObject(T object) {
        this.object = object;
    }
}

    public void waitUntilFinished(ObjectWrapper<Boolean> isFinished) {
        for (int i = 0; i < 100; i++) {
            if (isFinished.getObject())
                return;
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
            }
        }
        fail("timeout");
    }
}

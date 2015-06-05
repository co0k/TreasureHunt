package frontend.Requests;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Error;
import com.thetransactioncompany.jsonrpc2.JSONRPC2ParamsType;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import communication_controller.json.JsonConstructor;
import core.CoreModel;
import core.commands.*;
import data_structures.treasure.GeoLocation;
import data_structures.treasure.Quiz;
import data_structures.treasure.Treasure;
import data_structures.user.HighscoreList;
import data_structures.user.User;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by nebios on 28.05.15.
 */
public class RequestHandler implements RequestResolver {

    /**
     * this Method parses the request
     * @param request
     * @return
     */
    @Override
    public JSONRPC2Response handleRequest(JSONRPC2Request request) {
        String methodName;
        Map<String, Object> parameters = null;
        String id;
        int argc = 0;

        id = (String) request.getID();

        JsonConstructor jsonC = new JsonConstructor();

        Object response = null;

        // extract the necessary informations from the request
        methodName = request.getMethod();

        JSONRPC2ParamsType type = request.getParamsType();
        if (type == JSONRPC2ParamsType.OBJECT ) {
            parameters = request.getNamedParams();
             argc = parameters.size();
        }

        /*
        checks which method the client wants to invoke
         */
        switch(methodName.toLowerCase()) {
            case "checklogin":
                assert(parameters != null);
                response = checkLogIn((String) parameters.get("username"),
                        (String) parameters.get("pwHash"));
                break;

            case "registeruser":
                assert(parameters != null);
                response = registerUser((String) parameters.get("email"),
                                              (String)parameters.get("username"),
                                              (String) parameters.get("pwHash"));
                break;

            case "getalltreasures":
                assert(parameters != null);
                Integer token = jsonC.fromJson((String)parameters.get("token"), Integer.class);
                assert( isTokenActive(token) );
                response = getAllTreasures();
                break;

            case "getneartreasures":
                assert(parameters != null);
                token = jsonC.fromJson((String) parameters.get("token"), Integer.class);
                Double lat = jsonC.fromJson((String)parameters.get("latitude"), Double.class);
                Double longitude = jsonC.fromJson((String)parameters.get("longitude"), Double.class);
                switch(argc) {
                    case 3: response = getNearTreasures(token, longitude, lat); break;
                    case 4: response = getNearTreasures(token, longitude, lat, jsonC.fromJson((String)parameters.get("radius"),Double.class));
                }
                break;

            case "eventtreasureopen":
                assert(parameters != null);
                response = eventTreasureOpened((Integer) parameters.get("token"),
                                    (Integer)parameters.get("treauserID"),
                                    (Integer)parameters.get("userID"));
                break;

            case "eventtreasurewronganswer":
                assert(parameters != null);
                eventTreasureWrongAnswer((Integer) parameters.get("token"),
                                         (Integer) parameters.get("treauserID"),
                                         (Integer) parameters.get("userID"));

                break;

            case "gethighscorelist":
                assert(parameters != null);
                response = getHighscoreList((Integer) parameters.get("token"),
                                 (Integer) parameters.get("low"),
                                 (Integer) parameters.get("high"));
                break;

            case "gettesttreasure":
                response = getTestTreasure();
                break;

            default:
                String errorMsg = "Could not parse your request";
                JSONRPC2Error error = JSONRPC2Error.PARSE_ERROR;
                return new JSONRPC2Response(error, errorMsg);
        }

        return new JSONRPC2Response(jsonC.toJson(response), request.getID());
    }

    @Override
    public Integer checkLogIn(String username, String pwHash) {
        Integer result = 0;
        try {
            Future<Integer> future =  CoreModel.getInstance().addCommand(new CheckUserLoginCommand(new User(username,pwHash,null,0,0,null)));
            //System.err.println("\nIn checkLogIn \n");
            result = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //System.err.println("\n\n checkLogIn got a result \n\n");
        return result;
    }

    @Override
    public Boolean registerUser(String email, String username, String pwHash) {
        Future<Integer> future = CoreModel.getInstance().addCommand(new AddUserCommand(email, username, pwHash));
        try {
            return (future.get() != null);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //System.err.println("\n\n registerUser got a result \n\n");
        return false;
    }

    @Override
    public List<Treasure> getAllTreasures() {
        Future<List<Treasure>> future = CoreModel.getInstance().addCommand(new GetAllTreasuresCommand(true));
        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Treasure> getNearTreasures(Integer token, Double longitude, Double latitude) {
        assert ( isTokenActive(token) );
        return getNearTreasures(token, longitude, latitude, 1000d);
    }

    @Override
    public List<Treasure> getNearTreasures(Integer token, Double longitude, Double latitude, Double radius) {
        assert ( isTokenActive(token) );
        GeoLocation location = new GeoLocation(latitude, longitude);
        Future<List<Treasure>> future = CoreModel.getInstance().addCommand(new GetTreasuresAroundCommand(location, radius));
        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Boolean eventTreasureOpened(Integer token, Integer treasureID, Integer userID) {
        assert ( isTokenActive(token) );
        Future<Boolean> future = CoreModel.getInstance().addCommand(new OpenTreasureCommand(treasureID, userID));
        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void eventTreasureWrongAnswer(Integer token, Integer treasureID, Integer userID) {

    }

    @Override
    public HighscoreList getHighscoreList(Integer token, Integer low, Integer high) {
        assert ( isTokenActive(token) );
        try {
            return CoreModel.getInstance().addCommand(new GetHighscoresAroundCommand(token, low, high)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Treasure getTestTreasure() {
        Quiz quiz1 = new Quiz(10, "Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", "Konzerthaus", null, null);
        Treasure.Location testLocation = new Treasure.Location(1, 10, 47.2641234, 11.3451889 );
        Treasure t = new Treasure(1, testLocation, quiz1, new Treasure.Size(-1, 20, 1), null);
        return t;
    }

    private Boolean isTokenActive(Integer token){
        assert( token != null );
        try {
            return ( CoreModel.getInstance().addCommand(new IsActiveTokenCommand(token)).get() );
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return false;
    }
}

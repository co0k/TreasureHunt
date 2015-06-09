package frontend.Requests;

import com.google.gson.Gson;
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

import java.util.*;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created by nebios on 28.05.15.
 */
public class RequestHandler implements RequestResolver {

    private String[] tokenFree = {"checklogin","registeruser","gettesttreasure"};
    private Integer token;
    private Gson gson = new Gson();
    /**
     * this Method parses the request
     * @param request
     * @return
     *
     */
    @Override
    public JSONRPC2Response handleRequest(JSONRPC2Request request) {
        String methodName;
        Map<String, Object> parameters = null;
        String id;
        int argc = 0;

        JsonConstructor jsonC = new JsonConstructor();

        Object response = null;

        // extract the necessary informations from the request
        methodName = request.getMethod().toLowerCase();
        id = (String) request.getID();

        JSONRPC2ParamsType type = request.getParamsType();
        if (type == JSONRPC2ParamsType.OBJECT ) {
            parameters = request.getNamedParams();
            argc = parameters.size();
        }

        // checks if the requested method is token free
        // i.e it is not required that the Client sends
        // a token together with his/her request
        if( !isTokenFree(methodName) ) {
            try {
                Integer token = jsonC.fromJson((String)parameters.get("token"), Integer.class);
                if( !isTokenActive(token) ) {
                    return new JSONRPC2Response(JSONRPC2Error.INVALID_PARAMS,id);
                }
            } catch( IllegalArgumentException e) {
                return new JSONRPC2Response(JSONRPC2Error.INVALID_PARAMS,id);
            }
        }

        // every other method than getTestTreasure requires parameters
        // so a we test this right here
        if( !methodName.equals("gettesttreasure") && parameters == null ) {
            return new JSONRPC2Response(JSONRPC2Error.INVALID_PARAMS, id);
        }

        /*
        checks which method the client wants to invoke
         */
        switch(methodName) {
             case "checklogin":
                //if(parameters == null) {
                    //response = "illegal arguments";
                    //break;
                //}
                //else {
                    response = checkLogIn((String) parameters.get("username"),
                            (String) parameters.get("pwHash"));
                //}
                break;

            case "registeruser":
                //if(parameters == null) {
                    //response = "illegal arguments";
                    //break;
                //}
                //else {
                    response = registerUser((String) parameters.get("email"),
                            (String) parameters.get("username"),
                            (String) parameters.get("pwHash"));
                //}
                break;

            case "getuserbyname":
                String username = (String) parameters.get("username");
                response = getUserByName(username);
                break;

            case "getprofiledata":
                response = getProfileData();
                break;

            case "getalltreasures":
                //if(parameters == null) {
                    //response = "illegal arguments";
                //}
                //else {
                    //token = jsonC.fromJson((String) parameters.get("token"), Integer.class);
                    //if (isTokenActive(token)) {
                        response = getAllTreasures();
                    //} else {
                        //response = "illegal operation";
                    //}
                //}
                break;

            case "getneartreasures":
                //if(parameters == null) {
                    //response = "illegal arguments";
                    //break;
                //}
                token = jsonC.fromJson((String)parameters.get("token"), Integer.class);
                //if(isTokenActive(token)) {
                    Double lat = jsonC.fromJson((String)parameters.get("latitude"), Double.class);
                    Double longitude = jsonC.fromJson((String)parameters.get("longitude"), Double.class);
                    switch(argc) {
                        case 3: response = getNearTreasures(/*token,*/ longitude, lat); break;
                        case 4: response = getNearTreasures(/*token,*/ longitude, lat, jsonC.fromJson((String)parameters.get("radius"),Double.class)); break;
                        default: return new JSONRPC2Response(JSONRPC2Error.INVALID_PARAMS,id); //response = "illegal arguments";
                    }
                //}
                //else {
                    //response = "illegal operation";
                //}
                break;

            case "eventtreasureopen":
                //if(parameters == null) {
                    //response = "illegal arguments";
                    //break;
                //}
                token = jsonC.fromJson((String)parameters.get("token"), Integer.class);
                //if(isTokenActive(token)) {
                    Integer treasureID = jsonC.fromJson((String) parameters.get("treasureID"), Integer.class);
                    Integer userID = jsonC.fromJson((String) parameters.get("userID"), Integer.class);
                    response = eventTreasureOpened( /*token,*/ treasureID, userID);
                //}
                //else {
                    //response = "illegal operation";
                //}
                break;

            case "eventtreasurewronganswer":
                //if(parameters == null) {
                    //response = "illegal arguments";
                    //break;
                //}
                //token = jsonC.fromJson((String)parameters.get("token"), Integer.class);
                //if(isTokenActive(token)) {
                    eventTreasureWrongAnswer(/*(Integer) parameters.get("token"),*/
                            (Integer) parameters.get("treauserID"),
                            (Integer) parameters.get("userID"));
                //}
                //else {
                    //response = "illegal operation";
                //}

                break;

            case "gethighscorelist":
                //if(parameters == null) {
                    //response = "illegal arguments";
                    //break;
                //}
                token = jsonC.fromJson((String)parameters.get("token"), Integer.class);
                //if(isTokenActive(token)) {
                    response = getHighscoreList(/*(Integer) parameters.get("token"),*/
                            (Integer) parameters.get("low"),
                            (Integer) parameters.get("high"));
                //}
                //else {
                    //response = "illegal operation";
                //}
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
            result = future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
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
        return false;
    }

    @Override
    public List<Treasure> getAllTreasures() {
        //Future<List<Treasure>> future = CoreModel.getInstance().addCommand(new GetAllTreasuresCommand(true));
        // this version is used FOR DEBUGGING only
        Future<List<Treasure>> future = CoreModel.getInstance().addCommand(new GetAllTreasuresCommand(false));
        try {
            return future.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.err.println(e);
        } catch (ExecutionException e) {
            e.printStackTrace();
            System.err.println(e);
        }
        return null;
    }

    @Override
    public List<Treasure> getNearTreasures(/*Integer token,*/ Double longitude, Double latitude) {
        return getNearTreasures(/*token,*/ longitude, latitude, 1000d);
    }

    @Override
    public List<Treasure> getNearTreasures(/*Integer token,*/ Double longitude, Double latitude, Double radius) {
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
    public User getUserByName(String username) {
        return null;
    }

    @Override
    public User getProfileData() {
        return null;
    }

    @Override
    public Boolean eventTreasureOpened(/*Integer token,*/ Integer treasureID, Integer userID) {
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
    public void eventTreasureWrongAnswer(/*Integer token,*/ Integer treasureID, Integer userID) {
        try {
            CoreModel.getInstance().addCommand( new WrongQuizAnswerCommand(treasureID, userID)).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @Override
    public HighscoreList getHighscoreList(/*Integer token,*/ Integer low, Integer high) {
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

    private Boolean isTokenFree (String methodName ) {
        for( String tokenFreeMethod : tokenFree ) {
            if( tokenFreeMethod.equalsIgnoreCase(methodName) )
                    return true;
        }
        return false;
    }

    private Boolean isTokenActive(Integer token) throws IllegalArgumentException {
        if ( token == null ) throw new IllegalArgumentException();
        try {
            return ( CoreModel.getInstance().addCommand(new IsActiveTokenCommand(token)).get() );
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (CancellationException e) {
            e.printStackTrace();
        }
        return false;
    }
}

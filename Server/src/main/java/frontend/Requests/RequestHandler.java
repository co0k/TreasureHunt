package frontend.Requests;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import data_structures.treasure.Treasure;
import data_structures.user.HighscoreList;

import java.util.List;
import java.util.Map;

/**
 * Created by nebios on 28.05.15.
 */
public class RequestHandler implements RequestResolver {


    @Override
    public JSONRPC2Response handleRequest(JSONRPC2Request request) {
        String methodName;
        Map<String, Object> parameters;
        String id;

        methodName = request.getMethod();
        parameters = request.getNamedParams();
        id = (String) request.getID();

        String response = "response";

        switch(methodName.toLowerCase()) {
            case "checklogin":          checkLogIn((String) parameters.get("username"),
                                                (String) parameters.get("pwHash"));
                                        break;

            case "registeruser":        registerUser((String) parameters.get("email"),
                                                (String)parameters.get("username"),
                                                (String) parameters.get("pwHash"));
                                        break;

            case "getalltreasures":     getAllTreasures((Integer) parameters.get("token")); break;
            case "getneartreasures":    break;
            case "eventtreasureopen":   eventTreasureOpened((Integer) parameters.get("token"),
                                                (Integer)parameters.get("treauserID"),
                                                (Integer)parameters.get("userID"));
                                        break;

            case "eventtreasurewronganswer":    eventTreasureWrongAnswer((Integer) parameters.get("token"),
                    (Integer) parameters.get("treauserID"),
                    (Integer) parameters.get("userID"));
                                        break;

            case "gethighscorelist":    getHighscoreList((Integer) parameters.get("token"),
                                                (Integer) parameters.get("low"),
                                                (Integer) parameters.get("high"));
                                        break;
        }

        return new JSONRPC2Response(response, request.getID());
    }

    @Override
    public Integer checkLogIn(String username, String pwHash) {
        return null;
    }

    @Override
    public boolean registerUser(String email, String username, String pwHash) {
        return false;
    }

    @Override
    public List<Treasure> getAllTreasures(Integer token) {
        return null;
    }

    @Override
    public List<Treasure> getNearTreasures(Integer token, Double longitude, Double latitude) {
        return null;
    }

    @Override
    public List<Treasure> getNearTreasures(Integer token, Double longitude, Double latitude, Double radius) {
        return null;
    }

    @Override
    public boolean eventTreasureOpened(Integer token, Integer treasureID, Integer userID) {
        return false;
    }

    @Override
    public void eventTreasureWrongAnswer(Integer token, Integer treasureID, Integer userID) {

    }

    @Override
    public HighscoreList getHighscoreList(Integer token, Integer low, Integer high) {
        return null;
    }

}

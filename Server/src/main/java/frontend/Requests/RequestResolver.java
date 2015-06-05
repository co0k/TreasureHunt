package frontend.Requests;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import data_structures.treasure.Treasure;
import data_structures.user.HighscoreList;

import java.util.List;

/**
 * Created by nebios on 28.05.15.
 *  <br />
 *  This interface shows all methods which an client can remotely invoke <br />
 *  <br />
 *  checkLogIn(username, password) <br />
 *  registerUser(email, username, password) <br />
 *  getAllTreasures(token) <br />
 *  getNearTreasures(token, myLongitude, myLatitude, [optional radius?]) <br />
 *  getProfileData(token, userID)
 *  eventTreasureOpened(token, treasureID, userID) [a treasure is opened when the quiz is solved]
 *  eventTreasureWrongAnswer(token, treasureID, userID) [serverside handling of wrong answer given, maybe reset treasure or whatever]
 *  getHighscoreListAll(token)
 *  getHighscoreListAroundMe(token, userID, [range])
 *
 */
public interface RequestResolver {

    JSONRPC2Response handleRequest(JSONRPC2Request request);

    Integer checkLogIn(String username, String pwHash);
    Boolean registerUser(String email, String username, String pwHash);
    List<Treasure> getAllTreasures();
    List<Treasure> getNearTreasures(Integer token, Double longitude, Double latitude);
    List<Treasure> getNearTreasures(Integer token, Double longitude, Double latitude, Double radius);
    // getProfileData(int token, int userID);
    Boolean eventTreasureOpened(Integer token, Integer treasureID, Integer userID); //[a treasure is opened when the quiz is solved]
    void eventTreasureWrongAnswer(Integer token, Integer treasureID, Integer userID); //[serverside handling of wrong answer given, maybe reset treasure or whatever]
    // HighscoreList getHighscoreListAll(int token);
    HighscoreList getHighscoreList(Integer token, Integer low, Integer high);
    // HighscoreList getHighscoreListAroundMe(int token, int userID);, [range])
    // HighscoreList getHighscoreListAroundMe(int token, int userID, int range);

}

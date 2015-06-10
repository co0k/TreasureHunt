package frontend.Requests;

import com.thetransactioncompany.jsonrpc2.JSONRPC2Request;
import com.thetransactioncompany.jsonrpc2.JSONRPC2Response;
import data_structures.treasure.Treasure;
import data_structures.user.HighscoreList;
import data_structures.user.User;

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

    Integer checkLogIn(String username, String pwHash);                     // tested
    Boolean registerUser(String email, String username, String pwHash);     // tested
    User getProfileData();                                                  // tested
    Boolean editUser(String email, String username, String pwHash);         // tested
    Boolean deleteUser();                                                   // tested

    List<Treasure> getAllTreasures();                                       // tested
    List<Treasure> getNearTreasures( Double longitude, Double latitude);    // tested
    List<Treasure> getNearTreasures( Double longitude, Double latitude, Double radius);   // test
    Boolean eventTreasureOpened( Integer treasureID ); //[a treasure is opened when the quiz is solved] // tested
    void eventTreasureWrongAnswer( Integer treasureID ); //[serverside handling of wrong answer given, maybe reset treasure or whatever]
    // HighscoreList getHighscoreListAll(int token);
    HighscoreList getHighscoreList( Integer low, Integer high);             // tested
    // HighscoreList getHighscoreListAroundMe(int token, int userID);, [range])
    // HighscoreList getHighscoreListAroundMe(int token, int userID, int range);

}

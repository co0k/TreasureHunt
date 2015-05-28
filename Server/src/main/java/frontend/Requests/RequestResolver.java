package frontend.Requests;

import data_structures.treasure.Treasure;
import data_structures.user.HighscoreList;

import java.util.List;

/**
 * Created by nebios on 28.05.15.
 *
 *  checkLogIn(username, password)
 *  registerUser(email, username, password)
 *  getAllTreasures(token)
 *  getNearTreasures(token, myLongitude, myLatitude, [optional radius?])
 *  getProfileData(token, userID)
 *  eventTreasureOpened(token, treasureID, userID) [a treasure is opened when the quiz is solved]
 *  eventTreasureWrongAnswer(token, treasureID, userID) [serverside handling of wrong answer given, maybe reset treasure or whatever]
 *  getHighscoreListAll(token)
 *  getHighscoreListAroundMe(token, userID, [range])
 *
 */
public interface RequestResolver {

    Integer checkLogIn(String username, String pwHash);
    boolean registerUser(String email, String username, String pwHash);
    List<Treasure> getAllTreasures(Integer token);
    List<Treasure> getNearTreasures(Integer token, double longitude, double latitude);
    List<Treasure> getNearTreasures(Integer token, double longitude, double latitude, double radius);
    // getProfileData(int token, int userID);
    boolean eventTreasureOpened(Integer token, int treasureID, int userID); //[a treasure is opened when the quiz is solved]
    void eventTreasureWrongAnswer(Integer token, int treasureID, int userID); //[serverside handling of wrong answer given, maybe reset treasure or whatever]
    // HighscoreList getHighscoreListAll(int token);
    HighscoreList getHighscoreList(Integer token, int high, int low);
    // HighscoreList getHighscoreListAroundMe(int token, int userID);, [range])
    // HighscoreList getHighscoreListAroundMe(int token, int userID, int range);

}

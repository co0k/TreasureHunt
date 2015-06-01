package db;

import java.util.List;
import java.util.Map;

import data_structures.treasure.*;
import data_structures.user.*;

public interface DatabaseControllerDAO {
	/*********************** Treasure ***********************/
	
	/**
	 * return either all or only the active treasures
	 * @param onlyActive
	 * @return
	 */
	List<Treasure> getAllTreasures(boolean onlyActive);
	
	/**
	 * returns a specific treasure, from a given id
	 * @param treasureId
	 * @return the treasure or null if not found
	 */
	Treasure getTreasure(int treasureId);

	/**
	 * returns a list of all treasures within the given radius.
	 * 
	 * @param location
	 *            the center of the circle that holds the treasures
	 * @param radius in meters
	 * @return the list of treasures
	 */
	List<Treasure> getTreasures(GeoLocation location, double radius);

	/**
	 * returns id of saved treasure
	 * 
	 * @param treasure
	 *            the treasure to be saved, should hold all items, either per id
	 *            or per object
	 * @return the id of the saved treasure
	 */
	 int saveTreasure(Treasure treasure);

	/**
	 * deletes the treasure by the reference of the id
	 * 
	 * @param id
	 * @return if everything succeeded, return true otherwise false
	 */
	boolean deleteTreasure(int id);

	/**
	 * obviously deletes all treasures in the database, should only be used for
	 * test cases
	 * 
	 * @return if everything succeeded, return true otherwise false
	 */
	boolean deleteAll();
	
	/**
	 * checks if a treasure is active
	 * @param treasureId
	 * @return
	 */
	boolean isTreasureActive(int treasureId);
	
	/**
	 * checks if user is allowed to open a treasure
	 * @param userId
	 * @param treasureId
	 * @return 
	 */
	boolean allowedToOpenTreasure(int userId, int treasureId);
	
	/**
	 * checks if a user is allowed to open a treasure and tries to open it.
	 * It will add a history entry. It does NOT update the score of the user
	 * @param userId
	 * @param treasureId
	 * @return if everything succeeded, return true otherwise false
	 */
	boolean openTreasure(int userId, int treasureId);
	
	/**
	 * activates a treasure, given the id
	 * @param treasureId id of the treasure
	 * @return 
	 */
	boolean activateTreasure (int treasureId);
	
	/**
	 * deactivates a treasure ,given the id
	 * @param treasureId id of the treasure
	 * @return
	 */
	boolean deactivateTreasure (int treasureId);
	
	/**
	 * returns the Id of all or only the inactive treasures
	 * @param onlyInactive
	 * @return
	 */
	List<Integer> getallTreasureID (boolean onlyInactive);

	/**
	 * inserts the given content
	 *
	 * @param content the content to save
	 * @return the id or -1 if something went wrong
	 */
	int addContent(Treasure.Content content);
	
	/**
	 *  returns the Content of a given id
	 * @param id
	 * @return the content or null if not found
	 */
	Treasure.Content getContent(int id);


	/*********************** User ***********************/
	/**
	 * adds a user to the database 
	 * 
	 * @param user
	 * @return returns id of created User, -1 otherwise
	 */
	int addUser(User user);

	/**
	 * returns the userId on success, -1 otherwise (in 'user' only passwordHash and
	 * name is available) maybe not needed since this could be done with
	 * 'getUserProfile(...)' in the Core-model
	 * 
	 * @param user
	 * @return
	 */
	int checkUserLogin(User user);

	/**
	 * deletes the user by reference of the id
	 * 
	 * @param id
	 * @return
	 */
	boolean deleteUser(int id);

	/**
	 * returns user and all user connected data
	 * 
	 * @param id
	 * @return the user or null if not found
	 */
	User getUser(int id);

	/**
	 * returns user and all user connected data
	 * 
	 * @param name
	 * @return the user or null if not found
	 */
	User getUser(String name);

	/**
	 * returns only the basic information(name, email, rank, email)
	 * 
	 * @param id
	 * @return the user or null if not found
	 */
	User getUserProfile(int id);

	/**
	 * returns only the basic information(id, email, rank, email)
	 * 
	 * @param name
	 * @return the user or null if not found
	 */
	User getUserProfile(String name);

	/**
	 * returns the complete user inventory by reference of the user id
	 * 
	 * @param uId
	 *            the user Id
	 * @return the user inventory or null if not found
	 */
	Inventory getUserInventory(int uId);

	/**
	 * inserts a list of contents into the inventory of the user
	 * @param uId the user Id
	 * @param contentEntries the entry consisting of count of the content and the content it links to
	 * @return
	 */
	boolean insertInInventory(int uId, List<Inventory.Entry> contentEntries);

	/**
	 * inserts an Inventory.Entry into the inventory of the user.
	 * @param uId the user Id
	 * @param contentEntry the entry consisting of count of the content and the content it links to
	 * @return
	 */
	boolean insertInInventory(int uId, Inventory.Entry contentEntry);

	/**
	 * returns a list of treasures, and the time when it was opened in milliseconds since
	 * January 1, 1970, 00:00:00 GMT.
	 * 
	 * @param uId
	 * @return a list of treasures, and the time or null if either the user is not found 
	 * or he has no entry in his inventory
	 */
	Map<Treasure, Long> getTreasureHistory(int uId);

	/**
	 * returns a highscore list within the given range.
	 * 
	 * @param fromRank the starting rank. If lower then 1, 1 will be used
	 * @param numberOfEntries desired number of entries in the highscore. If lower then 1, 1 will be used.
	 * @return the list of highscores
	 */
	HighscoreList getHighscoreList(int fromRank, int numberOfEntries);
	
	/**
	 * checks if a user is allowed to change his Password and if so it will be updated.
	 * @param user
	 * @param newPwdHash
	 * @return
	 * @throws IllegalArgumentException if the newPwdHash is not a valid string
	 */
	boolean changePassword (User user, String newPwdHash) throws IllegalArgumentException;	
	
	/**
	 * gives back the rank of a given user.
	 * @param uId
	 * @return the rank or -1 if the user is not found
	 */
	int getRank (int uId);
	
	/**
	 * updates the score of a given user, from his id.
	 * the resulting score is the score the user had before, plus the score given by this method
	 * @param userId
	 * @param score points to add
	 * @return
	 */
	boolean updateScore (int userId, int score);
	
}

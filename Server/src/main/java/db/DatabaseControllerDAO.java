package db;

import java.util.List;
import data_structures.treasure.*;
import data_structures.user.*;

public interface DatabaseControllerDAO {
	/*********************** Treasure ***********************/
	List<Treasure> getAllTreasures(boolean onlyActive);

	Treasure getTreasure(int treasureId);

	/**
	 * returns a list of all treasures within the given radius
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
	 * checks if a user is allowed to open a treasure and tries to open it
	 * it does NOT update the score of the user
	 * @param userId
	 * @param treasureId
	 * @return
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


	/*********************** User ***********************/
	/**
	 * returns id of created User, -1 otherwise
	 * 
	 * @param user
	 * @return
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
	 * @return
	 */
	User getUser(int id);

	/**
	 * returns user and all user connected data
	 * 
	 * @param name
	 * @return
	 */
	User getUser(String name);

	/**
	 * returns only the basic information(name, email, rank, email)
	 * 
	 * @param id
	 * @return
	 */
	User getUserProfile(int id);

	/**
	 * returns only the basic information(id, email, rank, email)
	 * 
	 * @param name
	 * @return
	 */
	User getUserProfile(String name);

	/**
	 * returns the complete user inventory by reference of the user id
	 * 
	 * @param uId
	 *            the user Id
	 * @return the user inventory
	 */
	Inventory getUserInventory(int uId);

	/**
	 * returns a list of treasures, the treasures only need to be filled partly
	 * like size, id, type experience...
	 * 
	 * @param uId
	 * @return
	 */
	List<Treasure> getTreasureHistory(int uId);

	/**
	 * returns a highscore list within the given range. E.g. from the third to
	 * the 20th rank
	 * 
	 * @param minRange
	 *            the upper bound(e.g. the first place) if it is lower than 1
	 *            the first place will be used
	 * @param maxRange
	 *            the lower bound(e.g. the last place) if it is higher than the
	 *            last place the last place will be used
	 * @return the list of highscores
	 */
	HighscoreList getHighscoreList(int minRange, int maxRange);
	
	/**
	 * checks if a user is allowed to change his Password and if so it will be updated
	 * @param user
	 * @param newPwdHash
	 * @return
	 * @throws IllegalArgumentException
	 */
	boolean changePassword (User user, String newPwdHash) throws IllegalArgumentException;	
	
	/**
	 * gives back the rank of a given user
	 * @param user
	 * @return 
	 */
	int getRank (int uID);
	
	/**
	 * updates the score of a given user 
	 * @param userId
	 * @param score points to add
	 * @return
	 */
	boolean updateScore (int userId, int score);
	
}

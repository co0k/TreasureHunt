package main.java;

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
	 * @param radius
	 * @param onlyActive
	 *            decides if only active treasures should be returned, if false,
	 *            all treasures will be returned
	 * @return the list of treasures
	 */
	List<Treasure> getTreasures(GeoLocation location, double radius, boolean onlyActive);

	/**
	 * returns id of saved treasure, -1 otherwise
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
	 * @return
	 */
	boolean deleteAllTreasures();

	/**
	 * returns list of Treasures where only id and Location is used (rest is
	 * null)
	 * 
	 * @param location
	 * @param radius
	 * @param onlyActive
	 * @return
	 */
	List<Treasure> getLocationData(GeoLocation location, double radius, boolean onlyActive);

	/**
	 * returns list of Treasures where only id and Location is used (rest is
	 * null)
	 * 
	 * @param onlyActive
	 * @return
	 */
	List<Treasure> getAllLocationData(boolean onlyActive);

	/**
	 * returns a quiz object for the given Treasure id, -1 otherwise needed ?
	 * since its possible to get a whole Treasure with 'getTreasure(...)'
	 * 
	 * @param treasureId
	 * @return
	 */
	Quiz getQuiz(int treasureId);

	/*********************** User ***********************/
	/**
	 * returns id of created User, -1 otherwise
	 * 
	 * @param user
	 * @return
	 */
	int addUser(User user);

	/**
	 * returns true on success, false otherwise (in 'user' only passwordHash and
	 * name is available) maybe not needed since this could be done with
	 * 'getUserProfile(...)' in the Core-model
	 * 
	 * @param user
	 * @return
	 */
	boolean checkUserLogin(User user);

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
	 * @param id
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
}

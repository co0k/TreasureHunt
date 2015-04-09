package controllers;

import java.util.List;

import data_structures.treasure.*;
import data_structures.user.*;

public interface DatabaseController {
	/*********************** Treasure ***********************/
	List<Treasure> getAllTreasures(boolean onlyActive);

	Treasure getTreasure(int treasureId);

	List<Treasure> getTreasures(GeoLocation location, double radius, boolean onlyActive);

	/**
	 * returns id of saved treasure, -1 otherwise
	 * 
	 * @param treasure
	 * @return
	 */
	int saveTreasure(Treasure treasure);

	boolean deleteTreasure(int id);

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

	Inventory getUserInventory(int uId);

	/**
	 * returns a list of treasures, the treasures only need to be filled
	 * partly...
	 * 
	 * @param uId
	 * @return
	 */
	List<Treasure> getTreasureHistory(int uId);

	HighscoreList getHighscoreList(int minRange, int maxRange);
}

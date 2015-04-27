package main.java;

import java.util.List;
import main.java.data_structures.treasure.*;
import main.java.data_structures.user.*;


public class DatabaseController implements DatabaseControllerDAO {
    private DatabaseController() { }

    /**
     * Initializes singleton.
     *
     * DatabaseControllerHolder is loaded on the first execution of DatabaseController.getInstance()
     * or the first access to DatabaseController.INSTANCE, not before.
     */
    private static class DatabaseControllerHolder {
            private static final DatabaseController INSTANCE = new DatabaseController();
    }

    public static DatabaseController getInstance() {
            return DatabaseControllerHolder.INSTANCE;
    }

	@Override
	public List<Treasure> getAllTreasures(boolean onlyActive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Treasure getTreasure(int treasureId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Treasure> getTreasures(GeoLocation location, double radius, boolean onlyActive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int saveTreasure(Treasure treasure) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean deleteTreasure(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteAllTreasures() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<Treasure> getLocationData(GeoLocation location, double radius, boolean onlyActive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Treasure> getAllLocationData(boolean onlyActive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Quiz getQuiz(int treasureId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int addUser(User user) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean checkUserLogin(User user) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteUser(int id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public User getUser(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUser(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserProfile(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public User getUserProfile(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Inventory getUserInventory(int uId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Treasure> getTreasureHistory(int uId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public HighscoreList getHighscoreList(int minRange, int maxRange) {
		// TODO Auto-generated method stub
		return null;
	}
}

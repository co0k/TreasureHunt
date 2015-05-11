

import java.sql.SQLException;
import java.util.List;

import data_structures.treasure.*;
import data_structures.user.*;
import db.manager.DatabaseManager;
import db.manager.DatabaseSupervisor;


public class DatabaseController implements DatabaseControllerDAO {
	private DatabaseSupervisor dataBaseSupervisor;
    
	private DatabaseController() {
    	dataBaseSupervisor = new DatabaseSupervisor();
    }

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
		if (onlyActive) 
			return dataBaseSupervisor.getAllActiveTresures();
		else
			try {
				return DatabaseManager.getAllTreasure();
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	}

	@Override
	public Treasure getTreasure(int treasureId) {
		try {
			return DatabaseManager.getTreasureFromId(treasureId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}


	@Override
	public int saveTreasure(Treasure treasure) throws IllegalArgumentException{
		try {
			return DatabaseManager.saveTreasure(treasure);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public boolean deleteTreasure(int id) {
		try {
			return DatabaseManager.deleteTreasure(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteAllTreasures() {
		try {
			DatabaseManager.deleteAll();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
	}
	
	@Override
	public List<Treasure> getTreasures(GeoLocation location, double radius) {
		radius = radius / 6378137; // earth radius in meters
		return dataBaseSupervisor.getTreasuresNearLocation(location.getLon(), location.getLat(), radius);
	}
	
	@Override
	public boolean isTreasureActive(int treasureId) {
		return dataBaseSupervisor.isActive(treasureId);
	}
	
	@Override
	public boolean allowedToOpenTreasure(int userId, int treasureId) {
		try {
			if (dataBaseSupervisor.isActive(treasureId) && DatabaseManager.userAllowedToOpenTreasure(treasureId, userId))
				return true;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
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

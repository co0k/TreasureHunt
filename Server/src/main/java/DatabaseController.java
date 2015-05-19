

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
		radius = radius / 6371000; // earth radius in meters
		return dataBaseSupervisor.getTreasuresNearLocation(location.getLon(), location.getLat(), radius);
	}
	
	@Override
	public boolean activateTreasure(int treasureId) {
		return dataBaseSupervisor.addTresure(treasureId);
	}
	
	@Override
	public boolean deactivateTreasure(int treasureId) {
		return dataBaseSupervisor.removeTreasure(treasureId);
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
	public boolean openTreasure(int userId, int treasureId) {
		if (allowedToOpenTreasure(userId, treasureId))
			return deactivateTreasure(treasureId);
		else
			return false;
	}
	
	@Override
	public boolean updateScore (int userId, int score) {
		try {
			return DatabaseManager.updateScore(userId, score);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int addUser(User user) {
		try {
			return DatabaseManager.insertUser(user.getName(), user.getPasswordHash(), user.getEmail());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public boolean checkUserLogin(User user) {
		User toCompare = getUser(user.getName());
		return user.getPasswordHash().equals(toCompare.getPasswordHash());
		}

	@Override
	public boolean deleteUser(int id) {
		try {
			DatabaseManager.deleteInventory(id);
			DatabaseManager.deleteHistory(id);
			return DatabaseManager.deleteUser(id);

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public User getUser(int id) {
		try {
			return DatabaseManager.getUserFromId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User getUser(String name) {
		try {
			return DatabaseManager.getUserFromName(name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User getUserProfile(int id) {
		try {
			return DatabaseManager.getUserProfileFromId(id);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public User getUserProfile(String name) {
		try {
			return DatabaseManager.getUserProfileFromName(name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Inventory getUserInventory(int uId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Treasure> getTreasureHistory(int uId) {
		try {
			return DatabaseManager.getHistory(uId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public HighscoreList getHighscoreList(int minRange, int maxRange) {
		try {
			return DatabaseManager.getHighScoreFromTo(minRange, maxRange-minRange);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean changePassword (User user, String newPwdHash) throws IllegalArgumentException {
		if (checkUserLogin(user))
			try {
				return DatabaseManager.changePassword(user.getId(), newPwdHash);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return false;
	}
	
	@Override
	public int getRank (int uId) {
		try {
			return DatabaseManager.getRankFromId(uId);			
		} catch (IllegalArgumentException | SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}
	
	public List<Integer> getallTreasureID (boolean onlyInactive) {
			try {
				if (!onlyInactive) {
					return DatabaseManager.getAllBoxId();
				} else {
					List<Integer> out = DatabaseManager.getAllBoxId();
					out.removeAll(dataBaseSupervisor.getAllActiveTresuresId());
					return out;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	}


}

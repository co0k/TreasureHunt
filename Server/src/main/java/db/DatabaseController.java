package db;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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
     * DatabaseControllerHolder is loaded on the first execution of db.DatabaseController.getInstance()
     * or the first access to db.DatabaseController.INSTANCE, not before.
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
				return DatabaseManager.getAllTreasures();
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
	public boolean deleteAll() {
		try {
			DatabaseManager.deleteAll();
			dataBaseSupervisor.resetActive();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public List<Treasure> getTreasures(GeoLocation location, double radius) {
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
		if (allowedToOpenTreasure(userId, treasureId)){
			try {
				DatabaseManager.insertHistory(userId, treasureId);
				DatabaseManager.updateLastUser(userId, treasureId);
				return deactivateTreasure(treasureId);
			} catch (SQLException e) {
				e.printStackTrace();
				return false;
			}
		} else
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
			return DatabaseManager.insertUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@Override
	public int checkUserLogin(User user) {
		User toCompare = getUser(user.getName());
		if(user != null && user.getPasswordHash() != null &&
			toCompare != null && toCompare.getPasswordHash() != null) {
			if (user.getPasswordHash().equals(toCompare.getPasswordHash()))
				return toCompare.getId();
		}

		return -1;
	}

	@Override
	public boolean deleteUser(int id) {
		try {
			DatabaseManager.deleteAllBlockForUser(id);
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
	public boolean editUser(User user) {
		try {
			return DatabaseManager.editUser(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Inventory getUserInventory(int uId) {
		try {
			return DatabaseManager.getUserInventory(uId);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public boolean insertInInventory(int uId, List<Inventory.Entry> contentEntries) {
		try {
			for (Inventory.Entry cE : contentEntries) {
				for (int i = 0; i < cE.getCount(); i++) {
					if (cE.getContent().getId() != -1)
						DatabaseManager.insertInInventory(uId, cE.getContent().getId());
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean insertInInventory(int uId, Inventory.Entry contentEntry) {
		try {
			for (int i = 0; i < contentEntry.getCount(); i++) {
				if (contentEntry.getContent().getId() != -1)
					DatabaseManager.insertInInventory(uId, contentEntry.getContent().getId());
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public Map<Treasure, Long> getTreasureHistory(int uId) {
		try {
			return DatabaseManager.getHistory(uId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public HighscoreList getHighscoreList(int fromRank, int numberOfEntries) {
		try {
			if (fromRank < 1)
				fromRank = 1;
			if (numberOfEntries < 1)
				numberOfEntries = 1;
			return DatabaseManager.getHighScoreFromTo(fromRank, numberOfEntries);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean changePassword (User user, String newPwdHash) throws IllegalArgumentException {
		if (checkUserLogin(user) != -1)
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

	@Override
	public int addContent(Treasure.Content content) {
		Integer retVal;
		try {
			retVal = DatabaseManager.insertContent(content);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
		return retVal == null ? -1 : retVal;
	}

	@Override
	public Treasure.Content getContent(int id) {
		try {
			return DatabaseManager.getContentFromId(id);
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public boolean addBlock (int uID, int treasureId) {
		try {
			return DatabaseManager.insertBlock(uID, treasureId);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean isBlocked (int uID, int treasureId) {
		try {
			return DatabaseManager.isUserBlockedForTreasure(uID, treasureId);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeBlock (int uID, int treasureId) {
		try {
			return DatabaseManager.deleteBlock(uID, treasureId);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public long getLockTime (int uID, int treasureId) {
		try {
			return DatabaseManager.getLockTime(uID, treasureId);
		} catch (SQLException e) {
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int activateRandomTreasure (int n) {
		List<Integer> inactiveIDs = getallTreasureID(true);
		if (inactiveIDs.isEmpty())
			return 0;

		int countError = 0;
		if (inactiveIDs.size() < n)
			n = inactiveIDs.size();
		if (n == inactiveIDs.size()) {
			for (Integer i : inactiveIDs) {
				if (!activateTreasure(i))
					countError++;
			}
			return n - countError;
		}

		for (int i = 0; i < n; i++) {
			int rand = 0 + (int)(Math.random()*inactiveIDs.size());
			if (!activateTreasure(inactiveIDs.get(rand)))
				countError++;
			inactiveIDs.remove(rand);
		}
		return n - countError;
	}


}
package db.manager;

import java.sql.SQLException;
import java.util.AbstractCollection;
import java.util.ArrayList;

import data_structures.treasure.Treasure;
import data_structures.treasure.Treasure.*;
import db.quadtree.*;

public class DatabaseSupervisor {
	private QuadTree<Integer> activeTree = null;
	private static DatabaseSupervisor instance = null;

	private DatabaseSupervisor() {
		// Exists only to defeat instantiation.
	}

	public static DatabaseSupervisor getInstance() {
		if (instance == null) {
			instance = new DatabaseSupervisor();
		}
		return instance;
	}
	
	public void setupSupervisor() {
		if (activeTree != null) {
			activeTree = new QuadTree<Integer>(-90, -180, 90, 180);
		}
	}
	
	public boolean addTresure(int bid) {
		Location tmp;
		try {
			tmp = DatabaseManager.getLocationFromBid(bid);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;			
		}
		activeTree.put(tmp.getLon(), tmp.getLat(), bid);
		return true;
	}
	
	public ArrayList<Treasure> getTreasuresNearLocation (float lon, float lat, float radius) {
		ArrayList<Integer> tmp = activeTree.get(lon, lat, radius);
		ArrayList<Treasure> out = new ArrayList<Treasure>();
		for (Integer i : tmp ) {
			try {
				out.add(DatabaseManager.getTreasureFromId(i));
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return out;
	}
	
	public ArrayList<Treasure> getAllActiveTresures () {
		AbstractCollection<Integer> tmp = activeTree.values();
		ArrayList<Treasure> out = new ArrayList<Treasure>();
		for (Integer i : tmp ) {
			try {
				out.add(DatabaseManager.getTreasureFromId(i));
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
		}
		return out;
	}

}

package db.manager;

import java.sql.SQLException;
import java.util.AbstractCollection;
import java.util.ArrayList;

import data_structures.treasure.Treasure;
import data_structures.treasure.Treasure.*;
import db.quadtree.*;

public class DatabaseSupervisor {
	private QuadTree<Integer> activeTree = null;
	private ArrayList<Integer> activeId = null;

	public DatabaseSupervisor() {
		setupSupervisor();
	}
	
	private void setupSupervisor() {
		if (activeTree == null) {
			activeTree = new QuadTree<Integer>(-180, -90, 180, 90);
		}
		if (activeId == null) {
			activeId = new ArrayList<Integer>();
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
		activeId.add(bid);
		return true;
	}
	
	public boolean removeTreasure(int bid) {
		Location tmp;
		try {
			tmp = DatabaseManager.getLocationFromBid(bid);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;			
		}
		activeTree.remove(tmp.getLon(), tmp.getLat(), bid);
		activeId.remove(bid);
		return true;
	}
	
	public boolean isActive(Integer bid) {
		return activeId.contains(bid);
	}
	
	public ArrayList<Treasure> getTreasuresNearLocation (double lon, double lat, double radius) {
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

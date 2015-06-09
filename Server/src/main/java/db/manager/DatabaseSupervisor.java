package db.manager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import data_structures.treasure.GeoLocation;
import data_structures.treasure.Treasure;
import data_structures.treasure.Treasure.Location;


public class DatabaseSupervisor {
	private HashMap <Integer, Location> activeTreasures = null;

	public DatabaseSupervisor() {
		setupSupervisor();
	}
	
	private void setupSupervisor() {
		if (activeTreasures == null)
			activeTreasures = new HashMap <Integer, Location>();
	}
	
	public boolean addTresure(int bid) {
		Location tmp;
		try {
			tmp = DatabaseManager.getLocationFromBid(bid);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;			
		}
		activeTreasures.put(bid, tmp);
		return true;
	}
	
	public boolean removeTreasure(int bid) {
		if (activeTreasures.remove(bid) == null)
			return false;
		else
			return true;
	}
	
	public boolean isActive(Integer bid) {
		return activeTreasures.containsKey(bid);
	}
	
	public ArrayList<Treasure> getTreasuresNearLocation (double lon, double lat, double radius) {
		GeoLocation userLocation = new GeoLocation (lat, lon);
		ArrayList<Treasure> out = new ArrayList<Treasure>();
		for(Entry<Integer, Location> tmp : activeTreasures.entrySet()) {
	        Location value = tmp.getValue();
	        if ( value.getDistanceTo(userLocation) <= radius) {
	        	try {
					out.add(DatabaseManager.getTreasureFromId(tmp.getKey()));
				} catch (SQLException e) {
					e.printStackTrace();
					return null;
				}
	        	
	        }
	    }
		
		return (!out.isEmpty() ? out : null);
	}
	
	public ArrayList<Treasure> getAllActiveTresures () {
		Set<Integer> tmp = activeTreasures.keySet();
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
	
	public List<Integer> getAllActiveTresuresId () {
		List<Integer> out = new ArrayList<Integer>();
		out.addAll(activeTreasures.keySet());
		return out;
	}
	
	public void resetActive () {
		activeTreasures = new HashMap<Integer, Treasure.Location>();
	}

}

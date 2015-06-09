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
	private HashMap <Integer, Location> activeTreasuresID = null;
	private HashMap <Integer, Treasure> activeTreasures = null;

	public DatabaseSupervisor() {
		setupSupervisor();
	}
	
	private void setupSupervisor() {
		if (activeTreasuresID == null)
			activeTreasuresID = new HashMap <Integer, Location>();
		if (activeTreasures == null)
			activeTreasures = new HashMap <Integer, Treasure>();
	}
	
	public boolean addTresure(int bid) {
		Location tmpLocation;
		Treasure tmpTreasure;
		try {
			tmpLocation = DatabaseManager.getLocationFromBid(bid);
			tmpTreasure = DatabaseManager.getTreasureFromId(bid);
		} catch (SQLException e) {
			e.printStackTrace();
			return false;			
		}
		activeTreasuresID.put(bid, tmpLocation);
		activeTreasures.put(bid, tmpTreasure);
		return true;
	}
	
	public boolean removeTreasure(int bid) {
		if (activeTreasuresID.remove(bid) == null || activeTreasures.remove(bid) == null)
			return false;
		else
			return true;
	}
	
	public boolean isActive(Integer bid) {
		return activeTreasuresID.containsKey(bid);
	}
	
	public ArrayList<Treasure> getTreasuresNearLocation (double lon, double lat, double radius) {
		GeoLocation userLocation = new GeoLocation (lat, lon);
		ArrayList<Treasure> out = new ArrayList<Treasure>();
		for(Entry<Integer, Location> tmp : activeTreasuresID.entrySet()) {
	        Location value = tmp.getValue();
	        if ( value.getDistanceTo(userLocation) <= radius) {
					out.add(activeTreasures.get(tmp.getKey()));	        	
	        }
	    }
		
		return (!out.isEmpty() ? out : null);
	}
	
	public ArrayList<Treasure> getAllActiveTresures () {
		ArrayList<Treasure> out = new ArrayList<Treasure>();
		out.addAll(activeTreasures.values());
		if (out.isEmpty())
			return null;
		else
			return out;
	}
	
	public List<Integer> getAllActiveTresuresId () {
		List<Integer> out = new ArrayList<Integer>();
		out.addAll(activeTreasuresID.keySet());
		return out;
	}
	
	public void resetActive () {
		activeTreasuresID = new HashMap<Integer, Treasure.Location>();
	}

}

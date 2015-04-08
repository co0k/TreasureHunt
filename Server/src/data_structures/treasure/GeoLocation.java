package data_structures.treasure;

import data_structures.ExperiencePointHolder;

public class GeoLocation implements ExperiencePointHolder{
	private double lat;
	private double lon;
	private int experience;

	public GeoLocation(double lat, double lon, int experience) {
		this.lat = lat;
		this.lon = lon;
	}

	public double getLat() {
		return lat;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLon() {
		return lon;
	}

	public void setLon(double lon) {
		this.lon = lon;
	}

	@Override
	public int getXP() {
		return experience;
	}
}

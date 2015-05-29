package data_structures.treasure;

import java.io.Serializable;

public class GeoLocation implements Serializable {

	private static final long serialVersionUID = -107439324567278310L;
	private double lat;
	private double lon;

	public GeoLocation(double lat, double lon) {
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

	public double getDistanceTo(GeoLocation l) {
		// Haversine formula
		double latRad1 = Math.toRadians(lat);
		double latRad2 = Math.toRadians(l.lat);
		double deltaLatRad = Math.toRadians(lat - l.lat);
		double deltaLonRad = Math.toRadians(lon - l.lon);
		double a = Math.sin(deltaLatRad / 2) * Math.sin(deltaLatRad / 2) + Math.cos(latRad1) * Math.cos(latRad2) * Math.sin(deltaLonRad / 2) * Math.sin(deltaLonRad / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		return 6371000 * c;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		GeoLocation that = (GeoLocation) o;

		if (Double.compare(that.lat, lat) != 0) return false;
		return Double.compare(that.lon, lon) == 0;

	}


	@Override
	public int hashCode() {
		int result;
		long temp;
		temp = Double.doubleToLongBits(lat);
		result = (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(lon);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public String toString() {
		return "GeoLocation{" +
				"lat=" + lat +
				", lon=" + lon +
				'}';
	}
}

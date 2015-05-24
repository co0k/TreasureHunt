package data_structures.treasure;

import java.io.Serializable;

import data_structures.ExperiencePointHolder;
import data_structures.IdHolder;

public class Treasure implements ExperiencePointHolder, IdHolder, Comparable<Treasure>, Serializable {

	private static final long serialVersionUID = -4460184027651609755L;
	private int id;
	private Location location;
	private Type type;
	private Size size;
	private Content content;

	public Treasure(int id, Location location, Type type, Size size, Content content) {
		this.id = id;
		this.location = location;
		this.type = type;
		this.size = size;
		this.content = content;
	}

	public Treasure(Location location, Type type, Size size, Content content) {
		this(-1, location, type, size, content);
	}


	public Location getLocation() {
		return location;
	}

	public Type getType() {
		return type;
	}

	public Size getSize() {
		return size;
	}

	public Content getContent() {
		return content;
	}


	@Override
	public int getId() {
		return id;
	}

	@Override
	public int getXP() {
		return 0;
	}

	@Override
	public String toString() {
		String out = "bid: " + this.getId() + " lat: " + this.getLocation().getLat() + " lon: " + this.getLocation().getLon() +
				"\ntype: " + (type == null ? null : this.getType().getType()) +
				"\nsize: " + this.getSize().getSize() + " size exp: " + this.getSize().getXP();
		return out;
	}

	public boolean equalsWithoutId(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Treasure treasure = (Treasure) o;

		if (location != null ? !location.equalsWithoutId(treasure.location) : treasure.location != null) return false;
		if (type != null ? !type.equalsWithoutId(treasure.type) : treasure.type != null) return false;
		if (size != null ? !size.equalsWithoutId(treasure.size) : treasure.size != null) return false;
		return !(content != null ? !content.equals(treasure.content) : treasure.content != null);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Treasure treasure = (Treasure) o;

		if (id != treasure.id) return false;
		if (location != null ? !location.equals(treasure.location) : treasure.location != null) return false;
		if (type != null ? !type.equals(treasure.type) : treasure.type != null) return false;
		if (size != null ? !size.equals(treasure.size) : treasure.size != null) return false;
		return !(content != null ? !content.equals(treasure.content) : treasure.content != null);

	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + (location != null ? location.hashCode() : 0);
		result = 31 * result + (type != null ? type.hashCode() : 0);
		result = 31 * result + (size != null ? size.hashCode() : 0);
		result = 31 * result + (content != null ? content.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(Treasure treasure) {
		return Integer.compare(this.id, treasure.id);
	}

	/**
	 * *************** (sub-)interfaces and classes *****************
	 */

	public static abstract class Type implements IdHolder, ExperiencePointHolder, Serializable {

		private static final long serialVersionUID = -776106189348790607L;
		private int experience;
		private int id;

		abstract public String getType();

		public void setId(int id) {
			this.id = id;
		}

		@Override
		public int getId() {
			return id;
		}

		public void setXP(int xp) {
			this.experience = xp;
		}

		@Override
		public int getXP() {
			return experience;
		}

		public boolean equalsWithoutId(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Type type = (Type) o;

			if (this.experience != type.experience) return false;
			return !(getType() != null ? !getType().equals(type.getType()) : type.getType() != null);
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Type type = (Type) o;

			if (experience != type.experience) return false;
			if (id != type.id) return false;
			return !(getType() != null ? !getType().equals(type.getType()) : type.getType() != null);
		}

		@Override
		public int hashCode() {
			int result = experience;
			result = 31 * result + id;
			result = 31 * result + (getType() != null ? getType().hashCode() : 0);
			return result;
		}


	}

	public interface Content extends IdHolder, ExperiencePointHolder {
		String getType();
	}

	public static class Location extends GeoLocation implements IdHolder, ExperiencePointHolder, Serializable {

		private static final long serialVersionUID = 5989987395139608857L;
		int id;
		int experience;
		public Location(int id, int exp, double lat, double lon) {
			super(lat, lon);
			this.id = id;
			this.experience = exp;
		}

		public Location(int exp, double lat, double lon) {
			this(-1, exp, lat, lon);
		}

		@Override
		public int getXP() {
			return experience;
		}

		@Override
		public int getId() {
			return id;
		}

		public boolean equalsWithoutId(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			if (!super.equals(o)) return false;

			Location location = (Location) o;

			return experience == location.experience;

		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			if (!super.equals(o)) return false;

			Location location = (Location) o;

			if (id != location.id) return false;
			return experience == location.experience;

		}


		@Override
		public int hashCode() {
			int result = super.hashCode();
			result = 31 * result + id;
			result = 31 * result + experience;
			return result;
		}
	}

	public static class Size implements IdHolder, ExperiencePointHolder, Serializable {

		private static final long serialVersionUID = -2209741524996598990L;
		private int id;
		private int experience;
		private int size;

		public Size(int id, int exp, int size) {
			this.id = id;
			this.experience = exp;
			this.size = size;
		}

		Size(int exp, int size) {
			this(-1, exp, size);
		}

		public int getSize() {
			return size;
		}

		@Override
		public int getXP() {
			return experience;
		}

		@Override
		public int getId() {
			return id;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Size size1 = (Size) o;

			if (id != size1.id) return false;
			if (experience != size1.experience) return false;
			return size == size1.size;

		}

		@Override
		public int hashCode() {
			int result = id;
			result = 31 * result + experience;
			result = 31 * result + size;
			return result;
		}

		public boolean equalsWithoutId(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Size size1 = (Size) o;

			if (experience != size1.experience) return false;
			return size == size1.size;
		}
	}
}

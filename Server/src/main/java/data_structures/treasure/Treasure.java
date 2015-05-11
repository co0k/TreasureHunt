package data_structures.treasure;

import data_structures.ExperiencePointHolder;
import data_structures.IdHolder;

public class Treasure implements ExperiencePointHolder, IdHolder, Comparable<Treasure> {
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Treasure treasure = (Treasure) o;

		if (id != treasure.id) return false;
		if (!location.equals(treasure.location)) return false;
		if (!type.equals(treasure.type)) return false;
		if (!size.equals(treasure.size)) return false;
		if (content != null ? !content.equals(treasure.content) : treasure.content != null) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + location.hashCode();
		result = 31 * result + type.hashCode();
		result = 31 * result + size.hashCode();
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

	public static abstract class Type implements IdHolder, ExperiencePointHolder {
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

	public static class Location extends GeoLocation implements IdHolder, ExperiencePointHolder {
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

	public static class Size implements IdHolder, ExperiencePointHolder {
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
	}
}

package data_structures.treasure;

import data_structures.ExperiencePointHolder;
import data_structures.IdHolder;

public class Treasure implements ExperiencePointHolder, IdHolder {
	private int id;
	private int experience;
	private boolean isActive;
	private Location location;
	private Type type;
	private Size size;
	private Content content;

	public Treasure(int id, int exp, Location location, Type type, Size size, Content content, boolean isActive) {
		this.id = id;
		this.experience = exp;
		this.location = location;
		this.type = type;
		this.size = size;
		this.content = content;
		this.isActive = isActive;
	}

	public Treasure(int id, int exp, Location location, Type type, Size size, Content content) {
		this(id, exp, location, type, size, content, false);
	}

	public Treasure(int exp, Location location, Type type, Size size, Content content) {
		this(-1, exp, location, type, size, content, false);
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
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
		return experience;
	}

	/****************** (sub-)interfaces and classes ******************/

	public static abstract class  Type implements IdHolder, ExperiencePointHolder {
		private int experience;
		private int id;
		private String name;
		
		@Override
		public int getId() {
			return id;
		}
		@Override
		public int getXP() {
			return experience;
		}
		public String getType() {
			return name;
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

	}

	public static class Size implements IdHolder, ExperiencePointHolder {
		private int id;
		private int experience;
		private int size;

		Size(int id, int exp, int size) {
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
	}
}

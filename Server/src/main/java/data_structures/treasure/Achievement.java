package data_structures.treasure;

import java.io.Serializable;

public class Achievement implements Treasure.Content, Serializable {
	private static final long serialVersionUID = 9123987395139608857L;

	private int experience;
	private String name;
	private String description;
	private int id;

	public Achievement(int id, int experience, String achievementName, String description) {
		this.id = id;
		this.experience = experience;
		this.name = achievementName;
		this.description = description;
	}

	public Achievement(int experience, String achievementName, String description) {
		this(-1, experience, achievementName, description);
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public String getType() {
		return "ACHIEVEMENT";
	}

	@Override
	public void setId(int id) {
		this.id = id;
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
	public boolean equalsWithoudId(Treasure.Content content) {
		return false;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Achievement that = (Achievement) o;

		if (experience != that.experience) return false;
		if (id != that.id) return false;
		if (name != null ? !name.equals(that.name) : that.name != null) return false;
		return !(description != null ? !description.equals(that.description) : that.description != null);

	}

	@Override
	public int hashCode() {
		int result = experience;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (description != null ? description.hashCode() : 0);
		result = 31 * result + id;
		return result;
	}

	@Override
	public String toString() {
		return "Achievement{" +
				"experience=" + experience +
				", name='" + name + '\'' +
				", description='" + description + '\'' +
				", id=" + id +
				'}';
	}
}

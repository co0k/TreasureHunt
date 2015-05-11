package data_structures.user;

import data_structures.ExperiencePointHolder;
import data_structures.IdHolder;

import java.io.Serializable;

public class User implements IdHolder, ExperiencePointHolder, Serializable {
	private int id;
	private int rank;
	private int experience;
	private String name;
	private String passwordHash;
	private String email;
	private Inventory inventory;

	public User(int id, String name, String passwordHash, String email, int experience, int rank, Inventory inventory) {
		this.id = id;
		this.rank = rank;
		this.experience = experience;
		this.name = name;
		this.passwordHash = passwordHash;
		this.email = email;
		this.inventory = inventory;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public int getXP() {
		return experience;
	}

	public int getRank() {
		return rank;
	}

	public String getName() {
		return name;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public String getEmail() {
		return email;
	}

	public Inventory getInventory() {
		return inventory;
	}

	public boolean equalsWithoutId(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (rank != user.rank) return false;
		if (experience != user.experience) return false;
		if (name != null ? !name.equals(user.name) : user.name != null) return false;
		if (passwordHash != null ? !passwordHash.equals(user.passwordHash) : user.passwordHash != null) return false;
		if (email != null ? !email.equals(user.email) : user.email != null) return false;
		return !(inventory != null ? !inventory.equals(user.inventory) : user.inventory != null);
	}
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		User user = (User) o;

		if (id != user.id) return false;
		if (rank != user.rank) return false;
		if (experience != user.experience) return false;
		if (name != null ? !name.equals(user.name) : user.name != null) return false;
		if (passwordHash != null ? !passwordHash.equals(user.passwordHash) : user.passwordHash != null) return false;
		if (email != null ? !email.equals(user.email) : user.email != null) return false;
		return !(inventory != null ? !inventory.equals(user.inventory) : user.inventory != null);
	}

	@Override
	public int hashCode() {
		int result = id;
		result = 31 * result + rank;
		result = 31 * result + experience;
		result = 31 * result + (name != null ? name.hashCode() : 0);
		result = 31 * result + (passwordHash != null ? passwordHash.hashCode() : 0);
		result = 31 * result + (email != null ? email.hashCode() : 0);
		result = 31 * result + (inventory != null ? inventory.hashCode() : 0);
		return result;
	}
}

package data_structures.user;

import data_structures.ExperiencePointHolder;
import data_structures.IdHolder;

public class User implements IdHolder, ExperiencePointHolder {
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

}

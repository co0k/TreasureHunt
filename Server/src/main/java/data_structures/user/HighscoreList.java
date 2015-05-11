package data_structures.user;

import java.util.List;

import data_structures.ExperiencePointHolder;
import data_structures.IdHolder;

public class HighscoreList {
	private int minRange;
	private int maxRange;
	private List<Entry> list;

	public int getMinRange() {
		return minRange;
	}

	public int getMaxRange() {
		return maxRange;
	}

	public List<Entry> getList() {
		return list;
	}

	public void setOrder(Order order) {
		// TODO
	}

	public int getListCount() {
		return list.size();
	}

	public int getRank(int uId) {
		// TODO
		return -1;
	}

	public int getRank(String name) {
		// TODO
		return -1;
	}

	public int getUId(int rank) {
		// TODO
		return -1;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		HighscoreList that = (HighscoreList) o;

		if (minRange != that.minRange) return false;
		if (maxRange != that.maxRange) return false;
		return !(list != null ? !list.equals(that.list) : that.list != null);

	}

	@Override
	public int hashCode() {
		int result = minRange;
		result = 31 * result + maxRange;
		result = 31 * result + (list != null ? list.hashCode() : 0);
		return result;
	}

	/****************** (sub-)interfaces and classes ******************/

	public static enum Order {
		RANK, UID, NAME
	}

	public static class Entry implements IdHolder, ExperiencePointHolder {
		private int id;
		private String name;
		private int rank;
		private int experience;

		public Entry(int id, String name, int rank, int exp) {
			this.id = id;
			this.name = name;
			this.rank = rank;
			this.experience = exp;
		}


		public String getName() {
			return name;
		}

		public int getRank() {
			return rank;
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

			Entry entry = (Entry) o;

			if (id != entry.id) return false;
			if (rank != entry.rank) return false;
			if (experience != entry.experience) return false;
			return !(name != null ? !name.equals(entry.name) : entry.name != null);

		}

		@Override
		public int hashCode() {
			int result = id;
			result = 31 * result + (name != null ? name.hashCode() : 0);
			result = 31 * result + rank;
			result = 31 * result + experience;
			return result;
		}
	}
}

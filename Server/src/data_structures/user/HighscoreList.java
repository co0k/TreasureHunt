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

	/****************** (sub-)interfaces and classes ******************/

	public static enum Order {
		RANK, UID, NAME
	}

	public static class Entry implements IdHolder, ExperiencePointHolder {
		private int uId;
		private int id;
		private String name;
		private int rank;
		private int experience;

		public Entry(int id, int uId, String name, int rank, int exp) {
			this.id = id;
			this.uId = uId;
			this.name = name;
			this.rank = rank;
			this.experience = exp;
		}

		public int getUId() {
			return uId;
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
	}
}

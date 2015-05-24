package data_structures.user;

import java.io.Serializable;
import java.util.List;

import data_structures.ExperiencePointHolder;
import data_structures.IdHolder;

public class HighscoreList  implements Serializable, Comparable<HighscoreList> {

	private static final long serialVersionUID = 1855455260769203099L;
	private int minRange;
	private int maxRange;
	private List<Entry> list;
	
	
	public HighscoreList(int minRange, int maxRange, List<Entry> list) {
		super();
		this.minRange = minRange;
		this.maxRange = maxRange;
		this.list = list;
	}

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

	@Override
	public int compareTo(HighscoreList highscoreList) {
		if(minRange < highscoreList.minRange)
			return -1;
		else if(minRange > highscoreList.minRange)
			return 1;
		else  {
			if(maxRange < highscoreList.maxRange)
				return -1;
			else return 1;
		}
	}

	/****************** (sub-)interfaces and classes ******************/

	public static enum Order {
		RANK, UID, NAME
	}

	public static class Entry implements IdHolder, ExperiencePointHolder, Serializable {
		
		private static final long serialVersionUID = -5355700657619391702L;
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

		public Entry(String name, int rank, int exp) {
			this(-1, name,rank,exp);
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

		public boolean equalsWithoutId(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Entry entry = (Entry) o;

			if (rank != entry.rank) return false;
			if (experience != entry.experience) return false;
			return !(name != null ? !name.equals(entry.name) : entry.name != null);
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

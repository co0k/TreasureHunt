package data_structures.user;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import data_structures.treasure.Treasure;
import data_structures.treasure.Treasure.Content;


public class Inventory implements Serializable{
	
	private static final long serialVersionUID = 7945628184208861956L;
	private List<Entry> inventoryList;

	public Inventory() {
		inventoryList = new ArrayList<Inventory.Entry>();
	}

	public Inventory(List<Entry> inventoryList) {
		this.inventoryList = inventoryList;
	}

	public int getContentCount() {
		int retVal = 0;
		for (Entry e : inventoryList)
			retVal += e.getCount();
		return retVal;
	}

	public List<Entry> getInventoryList() {
		return inventoryList;
	}

	public void addEntry(Entry entry) {
		inventoryList.add(entry);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Inventory inventory = (Inventory) o;

		return !(inventoryList != null ? !inventoryList.equals(inventory.inventoryList) : inventory.inventoryList != null);

	}

	@Override
	public String toString() {
		return "Inventory{" +
				"inventoryList=" + inventoryList +
				'}';
	}

	@Override
	public int hashCode() {
		return inventoryList != null ? inventoryList.hashCode() : 0;
	}

	/**
	 * *************** (sub-)interfaces and classes *****************
	 */

	public static class Entry implements Serializable{
		
		private static final long serialVersionUID = 4495788775657108215L;
		private int count;
		private Treasure.Content content;

		public Entry(int count, Content content) {
			this.count = count;
			this.content = content;
		}

		public int getCount() {
			return count;
		}

		public Treasure.Content getContent() {
			return content;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;

			Entry entry = (Entry) o;

			if (count != entry.count) return false;
			return !(content != null ? !content.equals(entry.content) : entry.content != null);

		}

		@Override
		public int hashCode() {
			int result = 31 * count;
			result = 31 * result + (content != null ? content.hashCode() : 0);
			return result;
		}

		@Override
		public String toString() {
			return "Entry{" +
					"count=" + count +
					", content=" + content +
					'}';
		}
	}
}

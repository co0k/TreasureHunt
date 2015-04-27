package main.java.data_structures.user;

import java.util.ArrayList;
import java.util.List;

import main.java.data_structures.IdHolder;
import main.java.data_structures.treasure.Treasure;
import main.java.data_structures.treasure.Treasure.Content;


public class Inventory {
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
	public int hashCode() {
		return inventoryList != null ? inventoryList.hashCode() : 0;
	}

	/**
	 * *************** (sub-)interfaces and classes *****************
	 */

	public static class Entry implements IdHolder {
		private int id;
		private int count;
		private Treasure.Content content;

		public Entry(int id, int count, Content content) {
			this.id = id;
			this.count = count;
			this.content = content;
		}

		@Override
		public int getId() {
			return id;
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

			if (id != entry.id) return false;
			if (count != entry.count) return false;
			return !(content != null ? !content.equals(entry.content) : entry.content != null);

		}

		@Override
		public int hashCode() {
			int result = id;
			result = 31 * result + count;
			result = 31 * result + (content != null ? content.hashCode() : 0);
			return result;
		}
	}
}

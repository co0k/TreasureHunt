package data_structures.user;

import java.util.ArrayList;
import java.util.List;

import data_structures.IdHolder;
import data_structures.treasure.Treasure;
import data_structures.treasure.Treasure.Content;

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

	/****************** (sub-)interfaces and classes ******************/

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

	}
}

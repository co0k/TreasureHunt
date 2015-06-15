package core.commands;

import core.Achievements;
import core.Command;
import data_structures.treasure.Achievement;
import data_structures.treasure.Treasure;
import data_structures.user.Inventory;
import db.DatabaseController;

import java.util.List;
import java.util.Map;

/**
 * opens a treasure, returns true on success and false otherwise,
 * also updates the user score
 */
public class OpenTreasureCommand implements Command<Boolean> {
	private int tid;
	private int uid;

	public OpenTreasureCommand(int tid, int uid) {
		this.tid = tid;
		this.uid = uid;
	}

	@Override
	public int getPriority() {
		return 90;
	}

	@Override
	public Boolean execute() throws InterruptedException {
		DatabaseController db = DatabaseController.getInstance();
		if (db.openTreasure(uid, tid)) {
			Treasure t = db.getTreasure(tid);
			// add achievements like a "countless treasure opened achievment"
			List<Inventory.Entry> contentList = db.getUserInventory(uid).getInventoryList();
			Map<Treasure, Long> tHistory = db.getTreasureHistory(uid);
			if (tHistory.size() + 1 == 5) {
				Inventory.Entry t5 = new Inventory.Entry(1, db.getContent(Achievements.ACHIEVEMENT5_T));
				if (!contentList.contains(t5)) {
					db.insertInInventory(uid, t5);
					db.updateScore(uid, t.getXP() + t5.getContent().getXP());
				}
			} else if (tHistory.size() + 1 == 10) {
				Inventory.Entry t10 = new Inventory.Entry(1, db.getContent(Achievements.ACHIEVEMENT10_T));
				if (!contentList.contains(t10)) {
					db.insertInInventory(uid, t10);
					db.updateScore(uid, t.getXP() + t10.getContent().getXP());
				}
			} else if (tHistory.size() + 1 == 20) {
				Inventory.Entry t20 = new Inventory.Entry(1, db.getContent(Achievements.ACHIEVEMENT20_T));
				if (!contentList.contains(t20)) {
					db.insertInInventory(uid, t20);
					db.updateScore(uid, t.getXP() + t20.getContent().getXP());
				}
			} else if (tHistory.size() + 1 == 50) {
				Inventory.Entry t50 = new Inventory.Entry(1, db.getContent(Achievements.ACHIEVEMENT50_T));
				if (!contentList.contains(t50)) {
					db.insertInInventory(uid, t50);
					db.updateScore(uid, t.getXP() + t50.getContent().getXP());
				}
			} else if (tHistory.size() + 1 == 100) {
				Inventory.Entry t100 = new Inventory.Entry(1, db.getContent(Achievements.ACHIEVEMENT100_T));
				if (!contentList.contains(t100)) {
					db.insertInInventory(uid, t100);
					db.updateScore(uid, t.getXP() + t100.getContent().getXP());
				}
			} else if (tHistory.size() + 1 == 500) {
				Inventory.Entry t500 = new Inventory.Entry(1, db.getContent(Achievements.ACHIEVEMENT500_T));
				if (!contentList.contains(t500)) {
					db.insertInInventory(uid, t500);
					db.updateScore(uid, t.getXP() + t500.getContent().getXP());
				}
			} else if (tHistory.size() + 1 == 1000) {
				Inventory.Entry t1000 = new Inventory.Entry(1, db.getContent(Achievements.ACHIEVEMENT1000_T));
				if (!contentList.contains(t1000)) {
					db.insertInInventory(uid, t1000);
					db.updateScore(uid, t.getXP() + t1000.getContent().getXP());
				}
				if (t.getContent() != null)
					db.insertInInventory(uid, new Inventory.Entry(1, t.getContent()));
			}
			db.activateRandomTreasure(1);
			return true;
		}
		return false;
	}
}

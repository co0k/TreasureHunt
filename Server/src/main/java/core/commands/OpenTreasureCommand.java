package core.commands;

import core.Command;
import db.DatabaseController;

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

		if(DatabaseController.getInstance().openTreasure(uid, tid)) {
			DatabaseController.getInstance().updateScore(uid, DatabaseController.getInstance().getTreasure(tid).getXP());
			return true;
		}
		return false;
	}
}

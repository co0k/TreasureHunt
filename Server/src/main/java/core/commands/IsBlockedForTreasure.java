package core.commands;

import core.Command;
import db.DatabaseController;

public class IsBlockedForTreasure implements Command<Boolean> {
	private int tid;
	private int uid;

	public IsBlockedForTreasure(int tid, int uid) {
		this.tid = tid;
		this.uid = uid;
	}

	@Override
	public int getPriority() {
		return 60;
	}

	@Override
	public Boolean execute() throws InterruptedException {
		return DatabaseController.getInstance().isBlocked(uid, tid);
	}
}

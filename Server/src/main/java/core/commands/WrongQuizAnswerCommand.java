package core.commands;

import core.Command;
import db.DatabaseController;

public class WrongQuizAnswerCommand implements Command<Boolean> {
	private int tid;
	private int uid;

	public WrongQuizAnswerCommand(int tid, int uid) {
		this.tid = tid;
		this.uid = uid;
	}

	@Override
	public int getPriority() {
		return 50;
	}

	@Override
	public Boolean execute() throws InterruptedException {
		return DatabaseController.getInstance().addBlock(uid, tid);
	}
}

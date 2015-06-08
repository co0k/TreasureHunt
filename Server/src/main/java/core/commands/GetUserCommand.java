package core.commands;

import core.Command;
import data_structures.user.User;
import db.DatabaseController;

/**
 * returns the full user and its connected data
 */
public class GetUserCommand implements Command<User> {
	private int uid;

	public GetUserCommand(int uid) {
		this.uid = uid;
	}

	@Override
	public int getPriority() {
		return 70;
	}

	@Override
	public User execute() throws InterruptedException {
		return DatabaseController.getInstance().getUser(uid);
	}
}

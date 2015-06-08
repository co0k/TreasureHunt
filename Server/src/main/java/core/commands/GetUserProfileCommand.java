package core.commands;

import core.Command;
import data_structures.user.User;
import db.DatabaseController;

/**
 * returns only the basic information(name, email, rank, email)
 */
public class GetUserProfileCommand implements Command<User> {
	private int uid;

	public GetUserProfileCommand(int uid) {
		this.uid = uid;
	}

	@Override
	public int getPriority() {
		return 75;
	}

	@Override
	public User execute() throws InterruptedException {
		return DatabaseController.getInstance().getUserProfile(uid);
	}
}

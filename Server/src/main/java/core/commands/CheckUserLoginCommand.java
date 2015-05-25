package core.commands;

import core.Command;
import data_structures.user.User;
import db.DatabaseController;

/**
 * Checks the user login, where only the passwordHash and the user name is needed, packaged inside a
 * sparse(everything else is null) User object
 */
public class CheckUserLoginCommand implements Command<Boolean> {
	private User user;

	public CheckUserLoginCommand(User user) {
		this.user = user;
	}

	@Override
	public int getPriority() {
		return 80;
	}

	@Override
	public Boolean execute() throws InterruptedException {
		return DatabaseController.getInstance().checkUserLogin(user);
	}
}

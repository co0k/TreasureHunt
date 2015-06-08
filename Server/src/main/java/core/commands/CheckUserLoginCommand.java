package core.commands;

import core.Command;
import core.CoreModel;
import data_structures.user.User;
import db.DatabaseController;

/**
 * Checks the user login, where only the passwordHash and the user name is needed, packaged inside a
 * sparse(everything else is null) User object
 */
public class CheckUserLoginCommand implements Command<Integer> {
	private User user;

	public CheckUserLoginCommand(User user) {
		this.user = user;
	}

	@Override
	public int getPriority() {
		return 80;
	}

	@Override
	public Integer execute() throws InterruptedException {
		int token = DatabaseController.getInstance().checkUserLogin(user);
		if( token != -1) {
			CoreModel.getInstance().setActive(token);
			return token;
		}
		return null;
	}
}

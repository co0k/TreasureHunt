package core.commands;

import core.Command;
import data_structures.user.User;
import db.DatabaseController;

/**
 * Created by philm on 6/10/15.
 */
public class EditUserCommand implements Command<Boolean> {
	private User user;

	public EditUserCommand(User user) {
		this.user = user;
	}

	@Override
	public int getPriority() {
		return 10;
	}

	@Override
	public Boolean execute() throws InterruptedException {
		return DatabaseController.getInstance().editUser(user);
	}
}

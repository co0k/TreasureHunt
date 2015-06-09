package core.commands;

import core.Command;
import data_structures.user.User;
import db.DatabaseController;

/**
 * returns the full User, so every connected data like User information, profile, Inventory etc.
 */
public class GetUserByNameCommand implements Command<User> {
	private String name;

	public GetUserByNameCommand(String name) {
		this.name = name;
	}

	@Override
	public int getPriority() {
		return 10;
	}

	@Override
	public User execute() throws InterruptedException {
		return DatabaseController.getInstance().getUser(name);
	}
}

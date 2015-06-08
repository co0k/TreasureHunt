package core.commands;

import core.Command;
import core.CoreModel;

/**
 * returns if the user is active and logged in
 * it also refreshes the timeout
 */
public class IsActiveTokenCommand implements Command<Boolean> {
	private int token;

	public IsActiveTokenCommand(int token) {
		this.token = token;
	}
	@Override
	public int getPriority() {
		return 100;
	}

	@Override
	public Boolean execute() throws InterruptedException {
		return CoreModel.getInstance().isActive(token);
	}
}

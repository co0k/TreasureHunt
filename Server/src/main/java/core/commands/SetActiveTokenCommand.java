package core.commands;

import core.Command;
import core.CoreModel;

/**
 * set's the user active with the given token(which is the user id)
 */
public class SetActiveTokenCommand implements Command<Void> {
	private int token;

	public SetActiveTokenCommand(int token) {
		this.token = token;
	}
	@Override
	public int getPriority() {
		return 100;
	}

	@Override
	public Void execute() throws InterruptedException {
		CoreModel.getInstance().setActive(token);
		return null;
	}
}

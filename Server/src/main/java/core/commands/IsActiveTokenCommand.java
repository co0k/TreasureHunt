package core.commands;

import core.Command;
import core.CoreModel;

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

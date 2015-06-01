package core.commands;

import core.Command;
import data_structures.treasure.Treasure;
import db.DatabaseController;

import java.util.List;

public class GetAllTreasuresCommand implements Command<List<Treasure>> {
	private boolean onlyActive;

	public GetAllTreasuresCommand(boolean onlyActive) {
		this.onlyActive = onlyActive;
	}
	@Override
	public int getPriority() {
		return 5;
	}

	@Override
	public List<Treasure> execute() throws InterruptedException {
		return DatabaseController.getInstance().getAllTreasures(onlyActive);
	}
}

package core.commands;

import core.Command;
import data_structures.user.HighscoreList;
import db.DatabaseController;

/**
 * returns the whole highscore list
 */
public class GetHighscoresListCommand implements Command<HighscoreList> {
	@Override
	public int getPriority() {
		return 15;
	}

	@Override
	public HighscoreList execute() throws InterruptedException {
		return DatabaseController.getInstance().getHighscoreList(0, Integer.MAX_VALUE);
	}
}

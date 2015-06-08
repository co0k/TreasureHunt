package core.commands;

import core.Command;
import data_structures.user.HighscoreList;
import db.DatabaseController;

/**
 * returns the Highscores around a given userid, where values for upper and lower limit have to be given
 * for example the user requests the highscores with a lower limit of 2 and an upper limit of 3, the resulting list would be e.g.:
 * foreign user1 rank 3
 * foreign user2 rank 4
 * foreign user3 rank 5
 * user          rank 6
 * foreign user5 rank 7
 * foreign user6 rank 8
 *
 * the limits have to be positive otherwise null will be returned
 */
public class GetHighscoresAroundCommand implements Command<HighscoreList> {
	int uid;
	int lowerLimit;
	int upperLimit;

	public GetHighscoresAroundCommand(int uid, int lowerLimit, int upperLimit) {
		this.uid = uid;
		this.lowerLimit = lowerLimit;
		this.upperLimit = upperLimit;
	}

	@Override
	public int getPriority() {
		return 85;
	}

	@Override
	public HighscoreList execute() throws InterruptedException {
		int rank = DatabaseController.getInstance().getRank(uid) - 1;
		// user does not exist
		if(rank == -2)
			return null;
		// illegal
		if (upperLimit < 0 || lowerLimit < 0)
			return null;
		if (rank - upperLimit >= 0)
			return DatabaseController.getInstance().getHighscoreList(rank - upperLimit, upperLimit + lowerLimit + 1);
		else if (rank - upperLimit < 0)
			return DatabaseController.getInstance().getHighscoreList(0, lowerLimit + rank + 1);
		return null;
	}
}

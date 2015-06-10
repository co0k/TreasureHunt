package core.commands;

import core.Command;
import core.CoreModel;

/**
 * Created by nebios on 10.06.15.
 */
public class DeleteTreasureReservationCommand implements Command<Boolean> {
    private int token;
    private int treasureID;

    public DeleteTreasureReservationCommand(int token, int treasureID) {
        this.token = token;
        this.treasureID = treasureID;
    }

    @Override
    public int getPriority() {
        return 60;
    }

    @Override
    public Boolean execute() throws InterruptedException {
        CoreModel.getInstance().deleteTreasureReservation(token, treasureID);
        return !CoreModel.getInstance().isReserved(treasureID);
    }
}

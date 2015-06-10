package core.commands;

import core.Command;
import core.CoreModel;

/**
 * Created by nebios on 10.06.15.
 */
public class ReserveTreasureCommand implements Command<Boolean> {
    private int token;
    private int treasureID;

    public ReserveTreasureCommand(int treasureID, int token) {
        this.treasureID = treasureID;
        this.token = token;
    }

    // this Command is quiet expensive but also important
    @Override
    public int getPriority() {
        return 75;
    }

    @Override
    public Boolean execute() throws InterruptedException {
        CoreModel.getInstance().addTreasureReservation(treasureID, token);
        return CoreModel.getInstance().isReservedForUser(treasureID, token);
    }
}

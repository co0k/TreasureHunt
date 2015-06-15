package core.commands;

import core.Command;
import core.CoreModel;
import db.DatabaseController;
import db.manager.DatabaseManager;

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

    @Override
    public int getPriority() {
        return 75;
    }

    @Override
    public Boolean execute() throws InterruptedException {
        if ( DatabaseController.getInstance().allowedToOpenTreasure(token, treasureID) ) {
            CoreModel.getInstance().addTreasureReservation(treasureID, token);
            return CoreModel.getInstance().isReservedForUser(treasureID, token);
        }
        return false;
    }
}

package core.commands;

import core.Command;
import data_structures.treasure.GeoLocation;
import data_structures.treasure.Treasure;
import db.DatabaseController;

import java.util.List;

/**
 * Created by nebios on 10.06.15.
 */
public class GetFilteredTreasuresAroundCommand implements Command<List<Treasure>> {
    GeoLocation location;
    double radius;
    int userID;

    public GetFilteredTreasuresAroundCommand(int userID, GeoLocation location, double radius) {
        this.userID = userID;
        this.location = location;
        this.radius = radius;
    }

    @Override
    public int getPriority() {
        return 20;
    }

    @Override
    public List<Treasure> execute() throws InterruptedException {
        return DatabaseController.getInstance().getTreasures(userID, location, radius);
    }
}

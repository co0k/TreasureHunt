package core.commands;

import core.Command;
import core.CoreModel;
import data_structures.treasure.GeoLocation;
import data_structures.treasure.Treasure;
import db.DatabaseController;

import java.util.ArrayList;
import java.util.List;

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
        List<Treasure> treasures = DatabaseController.getInstance().getTreasures(userID, location, radius);
        List<Treasure> retVal = new ArrayList<>();
        for(Treasure t : treasures) {
            CoreModel cm = CoreModel.getInstance();
            if(!cm.isReserved(t.getId()) || cm.isReservedForUser(t.getId(),userID))
                retVal.add(t);
        }
        return retVal;
    }
}

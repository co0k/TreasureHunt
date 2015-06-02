package core.commands;

import core.Command;
import data_structures.treasure.GeoLocation;
import data_structures.treasure.Treasure;
import db.DatabaseController;

import java.util.List;

public class GetTreasuresAroundCommand implements Command<List<Treasure>> {
	GeoLocation location;
	double radius;

	public GetTreasuresAroundCommand(GeoLocation location, double radius) {
		this.location = location;
		this.radius = radius;
	}

	@Override
	public int getPriority() {
		return 20;
	}

	@Override
	public List<Treasure> execute() throws InterruptedException {
		return DatabaseController.getInstance().getTreasures(location,radius);
	}
}

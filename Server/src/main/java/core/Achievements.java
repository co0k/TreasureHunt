package core;

import data_structures.treasure.Achievement;
import db.DatabaseController;

public class Achievements {

	public static final int ACHIEVEMENT5_T;
	public static final int ACHIEVEMENT10_T;
	public static final int ACHIEVEMENT20_T;
	public static final int ACHIEVEMENT50_T;
	public static final int ACHIEVEMENT100_T;
	public static final int ACHIEVEMENT500_T;
	public static final int ACHIEVEMENT1000_T;
	static {
		// add Achievements, would be better to check if there are already existing Achievements to avoid duplicates and the dirty static variables
		ACHIEVEMENT5_T = DatabaseController.getInstance().addContent(new Achievement(100, "opened 5 treasure chests", "Congratulations you opened 5 treasure chests! you earned 100 exp"));
		ACHIEVEMENT10_T = DatabaseController.getInstance().addContent(new Achievement(200, "opened 10 treasure chests", "Congratulations you opened 5 treasure chests! you earned 200 exp"));
		ACHIEVEMENT20_T = DatabaseController.getInstance().addContent(new Achievement(500, "opened 20 treasure chests", "Congratulations you opened 5 treasure chests! you earned 500 exp"));
		ACHIEVEMENT50_T = DatabaseController.getInstance().addContent(new Achievement(1000, "opened 50 treasure chests", "Congratulations you opened 5 treasure chests! you earned 1000 exp"));
		ACHIEVEMENT100_T = DatabaseController.getInstance().addContent(new Achievement(5000, "opened 100 treasure chests", "Congratulations you opened 5 treasure chests! you earned 5000 exp"));
		ACHIEVEMENT500_T = DatabaseController.getInstance().addContent(new Achievement(30000, "opened 500 treasure chests", "Congratulations you opened 5 treasure chests! you earned 30000 exp"));
		ACHIEVEMENT1000_T = DatabaseController.getInstance().addContent(new Achievement(100000, "opened 1000 treasure chests", "Congratulations you opened 5 treasure chests! you earned 100000 exp"));
	}
}

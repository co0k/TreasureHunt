import static org.junit.Assert.*;

import data_structures.treasure.GeoLocation;
import data_structures.treasure.Quiz;
import data_structures.treasure.Treasure;
import org.junit.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseControllerTreasuresTest {
	List<Treasure> exampleTreasures;
	List<Integer> exampleTreasuresID;

	@Before
	public void initialize() {
		// TODO add more test data
		Quiz quiz1 = new Quiz("Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", "Konzerthaus", null, null);
		exampleTreasuresID = new ArrayList<Integer>();
		exampleTreasures = new ArrayList<Treasure>();
		exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.26952, 11.39570), quiz1, new Treasure.Size(-1, 20, 1), null));
		Collections.sort(exampleTreasures);
		// add all the treasures
		for (Treasure t : exampleTreasures)
			exampleTreasuresID.add(DatabaseController.getInstance().saveTreasure(t));

	}

	@After
	public void finish() {
		// clean the database
		DatabaseController.getInstance().deleteAllTreasures();
	}

	@Test
	public void saveTreasureTest() {
		Quiz quiz1 = new Quiz("Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", null, "Konzerthaus", null);
		Quiz quiz2 = new Quiz("Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", null, "Bank", null, "Konzerthaus", null);
		Quiz quiz3 = new Quiz("Aus was für einem Gebäude entstand das Landestheater?", null, "Ballspielhaus", "Bank", null, "Konzerthaus", null);
		Quiz quiz4 = new Quiz(null, "Bank", "Ballspielhaus", "Bank", null, "Konzerthaus", null);
		try {
			DatabaseController.getInstance().saveTreasure(new Treasure(new Treasure.Location(10, 47.26952, 11.39570), quiz1, new Treasure.Size(-1, 20, 1), null));
			fail("an exception should be thrown");
		} catch (IllegalArgumentException e) {
			assertEquals("exception strings aren't equal!", "an answer was given although the previous answer is null", e.getMessage());
		}
		try {
			DatabaseController.getInstance().saveTreasure(new Treasure(new Treasure.Location(10, 47.26952, 11.39570), quiz2, new Treasure.Size(-1, 20, 1), null));
			fail("an exception should be thrown");
		} catch (IllegalArgumentException e) {
			assertEquals("exception strings aren't equal!", "an answer was given although the previous answer is null", e.getMessage());
		}
		try {
			DatabaseController.getInstance().saveTreasure(new Treasure(new Treasure.Location(10, 47.26952, 11.39570), quiz3, new Treasure.Size(-1, 20, 1), null));
			fail("an exception should be thrown");
		} catch (IllegalArgumentException e) {
			assertEquals("exception strings aren't equal!", "no correct answer or question given!", e.getMessage());
		}
		try {
			DatabaseController.getInstance().saveTreasure(new Treasure(new Treasure.Location(10, 47.26952, 11.39570), quiz4, new Treasure.Size(-1, 20, 1), null));
			fail("an exception should be thrown");
		} catch (IllegalArgumentException e) {
			assertEquals("exception strings aren't equal!", "no correct answer or question given!", e.getMessage());
		}
		List<Treasure> result = DatabaseController.getInstance().getAllTreasures(false);
		Collections.sort(result);
		assertEquals("there was saved a treasure although it shouldn't!", exampleTreasures, result);
	}

	@Test
	public void deleteTreasureTest() {
		Quiz quiz1 = new Quiz("Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", "Konzerthaus", null, null);
		int id = DatabaseController.getInstance().saveTreasure(new Treasure(new Treasure.Location(10, 47.26952, 11.39570), quiz1, new Treasure.Size(-1, 20, 1), null));
		DatabaseController.getInstance().deleteTreasure(id);
		List<Treasure> result = DatabaseController.getInstance().getAllTreasures(false);
		Collections.sort(result);
		assertEquals("the saved treasure should have been deleted!", exampleTreasures, result);
	}

	@Test
	public void getTreasuresTest() {
		GeoLocation loc = new GeoLocation(47.26952, 11.39570);
		List<Treasure> exampleTreasuresLoc = new ArrayList<Treasure>();
		List<Treasure> result = DatabaseController.getInstance().getTreasures(loc, 2000);
		for (Treasure t : exampleTreasures)
			if (t.getLocation().getDistanceTo(loc) <= 2000) exampleTreasuresLoc.add(t);
		Collections.sort(exampleTreasuresLoc);
		assertEquals("the treasures within the radius(2000m) are not equal to the treasures the database gave", exampleTreasuresLoc, result);
		exampleTreasuresLoc = new ArrayList<Treasure>();
		result = DatabaseController.getInstance().getTreasures(loc, 300);
		for (Treasure t : exampleTreasures)
			if (t.getLocation().getDistanceTo(loc) <= 300) exampleTreasuresLoc.add(t);
		Collections.sort(exampleTreasuresLoc);
		assertEquals("the treasures within the radius(300m) are not equal to the treasures the database gave", exampleTreasuresLoc, result);
		exampleTreasuresLoc = new ArrayList<Treasure>();
		result = DatabaseController.getInstance().getTreasures(loc, 50);
		for (Treasure t : exampleTreasures)
			if (t.getLocation().getDistanceTo(loc) <= 50) exampleTreasuresLoc.add(t);
		Collections.sort(exampleTreasuresLoc);
		assertEquals("the treasures within the radius(50m) are not equal to the treasures the database gave", exampleTreasuresLoc, result);
	}

	@Test
	public void getTreasureTest() {
		List<Treasure> result = new ArrayList<Treasure>();
		for (Integer i : exampleTreasuresID) {
			Treasure t = DatabaseController.getInstance().getTreasure(i);
			result.add(t);
			assertEquals("the treasure with id: " + i + " is not equal to the id: " + t.getId() + "!", t, i);
		}
		Collections.sort(result);
		assertEquals("the treasures aren't equal", exampleTreasures, result);
	}

	@Test
	public void getAllTreasuresTest() {
		// test all treasures including inactive treasures
		List<Treasure> result = DatabaseController.getInstance().getAllTreasures(false);

		assertNotNull("null returned instead of all treasures", result);

		assertEquals("count of treasures has to be equal", result.size(), exampleTreasures.size());
		Collections.sort(result);
		assertEquals("the treasures aren't equal", exampleTreasures, result);
		// test only treasures that are active
		result = DatabaseController.getInstance().getAllTreasures(true);
		List<Treasure> exampleTreasuresActive = new ArrayList<Treasure>();
		for (Treasure t : result)
			assertTrue("treasure is not active, although it should be", DatabaseController.getInstance().isTreasureActive(t.getId()));
	}
}

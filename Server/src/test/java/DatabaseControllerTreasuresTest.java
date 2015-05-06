import static org.junit.Assert.*;

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
		Quiz quiz1 = new Quiz("Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", "Konzerthaus", null, null);
		exampleTreasuresID = new ArrayList<Integer>();
		exampleTreasures = new ArrayList<Treasure>();
		exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.26952, 11.39570), quiz1, new Treasure.Size(-1, 20, 1), null));
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
		assertEquals("there was saved a treasure although it shouldn't!", exampleTreasures.size(), DatabaseController.getInstance().getAllTreasures(false).size());
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
		Collections.sort(exampleTreasures);
		assertEquals("the treasures aren't equal", exampleTreasures, result);
	}

	@Test
	public void getAllTreasuresTest() {
		// test all treasures including inactive treasures
		List<Treasure> result = DatabaseController.getInstance().getAllTreasures(false);

		assertNotNull("null returned instead of all treasures", result);

		assertEquals("count of treasures has to be equal", result.size(), exampleTreasures.size());
		Collections.sort(result);
		Collections.sort(exampleTreasures);
		assertEquals("the treasures aren't equal", exampleTreasures, result);
		// test only treasures that are active
		result = DatabaseController.getInstance().getAllTreasures(true);
		List<Treasure> exampleTreasuresActive = new ArrayList<Treasure>();
		for (Treasure t : result)
			assertTrue("treasure is not active, although it should be", DatabaseController.getInstance().isTreasureActive(t.getId()));
	}
}

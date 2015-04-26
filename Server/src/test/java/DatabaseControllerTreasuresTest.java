import static org.junit.Assert.*;

import data_structures.treasure.Quiz;
import data_structures.treasure.Treasure;
import org.junit.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DatabaseControllerTreasuresTest {
	List<Treasure> exampleTreasures;

	@Before
	public void initialize() {
		Quiz quiz1 = new Quiz("Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", "Konzerthaus", null, null, 0);
		exampleTreasures = new ArrayList<Treasure>();
		exampleTreasures.add(new Treasure(23, new Treasure.Location(10, 47.26952, 11.39570), quiz1, new Treasure.Size(-1, 20, 1), null, -1));
		// clean the database
		DatabaseController.getInstance().deleteAllTreasures();
		// add all the treasures
		for (Treasure t : exampleTreasures)
			DatabaseController.getInstance().saveTreasure(t);

	}

	@Test
	public void getAllTreasuresTest() {
		// test all treasures including inactive treasures
		List<Treasure> result = DatabaseController.getInstance().getAllTreasures(false);

		assertNotNull("null returned instead of all treasures", result);

		assertEquals("count of treasures has to be equal", result.size(), exampleTreasures.size());
		Collections.sort(result);
		Collections.sort(exampleTreasures);
		assertTrue("the treasures aren't equal", exampleTreasures.equals(result));
		// test only treasures that are active
		result = DatabaseController.getInstance().getAllTreasures(true);
		List<Treasure> exampleTreasuresActive = new ArrayList<Treasure>();
		for(Treasure t : exampleTreasures) {
			if(t.isActive())
				exampleTreasuresActive.add(t);
		}
		assertNotNull("null returned instead of all treasures", result);
		assertEquals("count of treasures has to be equal", result.size(), exampleTreasuresActive.size());
		Collections.sort(result);
		Collections.sort(exampleTreasuresActive);
		assertTrue("the treasures aren't equal", exampleTreasures.equals(result));
	}
}

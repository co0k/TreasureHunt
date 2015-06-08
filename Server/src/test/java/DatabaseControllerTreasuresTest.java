import static org.junit.Assert.*;

import data_structures.treasure.Coupon;
import data_structures.treasure.GeoLocation;
import data_structures.treasure.Quiz;
import data_structures.treasure.Treasure;
import db.DatabaseController;
import org.junit.*;
import java.util.logging.LogManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class DatabaseControllerTreasuresTest {
	List<Treasure> exampleTreasures;
	List<Integer> exampleTreasuresID;

	@Before
	public void initialize() {
		LogManager.getLogManager().reset();
		Logger globalLogger = Logger.getLogger(java.util.logging.Logger.GLOBAL_LOGGER_NAME);
		globalLogger.setLevel(java.util.logging.Level.OFF);



		// TODO add more test data
		Quiz quiz1 = new Quiz(10,"Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", "Konzerthaus", null, null);
		Quiz quiz2 = new Quiz(10,"Wo ist der Rechnerraum 15?", "Uni Innsbruck", "dein zuhause", "Bank", "Konzerthaus", null, null);
		Quiz quiz3 = new Quiz(10,"In welchen Gebäude befindet sich Frau Webber?", "ICT Gebäude", "Rathaus", "Bauingenieurgebäude", "Mensa", "Bei dir zuhause", null);
		Quiz quiz4 = new Quiz(10,"Wo bekommt man den besten Kaffee am Campus?", "Jollys", "ICT Gebäude", "Bauingenieurgebäude", "Mensa", null, null);
		Quiz quiz5 = new Quiz(25,"Wofür ist der Alpenzoo bekannt?", "höchstgelegener Zoo Europas", "artenreichster Zoo Europas", "sauberster Zoo Europas", "was ist ein Zoo?", null, null);
		Quiz quiz6 = new Quiz(20,"Welches bekannte Snowboardevent findet alljährlich in Innsbruck statt?", "Air+Style", "PipetoPipe", "AlpinFreeze", "Rail Jam", null, null);
		Quiz quiz7 = new Quiz(15,"Wie viele vergoldete Kupferschindeln wurden beim goldenen Dachl verlegt?", "2.657", "1.529", "403", "86", null, null);
		Quiz quiz8 = new Quiz(30,"Wie viele Bäcker Ruetz gibt es in Innsbruck?", "16", "7", "pro Einwohner einen", "ist schon schlimmer als McDonalds", null, null);


		exampleTreasuresID = new ArrayList<Integer>();
		exampleTreasures = new ArrayList<Treasure>();

		exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.26952, 11.39570), quiz1, new Treasure.Size(-1, 20, 1), new Coupon(10, "SuperDuperMarket", 10.50)));
		exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.263372, 11.345269), quiz2, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.263567, 11.345916), quiz3, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.264659,11.3445717), quiz4, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(25, 47.263567, 11.345916), quiz5, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(20, 47.249058,11.399484), quiz6, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2686516, 11.393286), quiz7, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2675584, 11.3923194), quiz7, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.267785, 11.390727), quiz7, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(30, 47.2635802, 11.3945087), quiz8, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(30, 47.2654258,11.3936075), quiz8, new Treasure.Size(-1, 20, 1), null));


		Collections.sort(exampleTreasures);
		// add all the treasures
		for (Treasure t : exampleTreasures)
			exampleTreasuresID.add(DatabaseController.getInstance().saveTreasure(t));
	}

	@After
	public void finish() {
		// clean the database
		for (Integer tmp : exampleTreasuresID) {
			DatabaseController.getInstance().deactivateTreasure(tmp);
		}
		DatabaseController.getInstance().deleteAll();
	}

	@Test
	public void saveTreasureTest() {
		Quiz quiz1 = new Quiz(10,"Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", null, "Konzerthaus", null);
		Quiz quiz2 = new Quiz(10,"Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", null, "Bank", null, "Konzerthaus", null);
		Quiz quiz3 = new Quiz(10,"Aus was für einem Gebäude entstand das Landestheater?", null, "Ballspielhaus", "Bank", null, "Konzerthaus", null);
		Quiz quiz4 = new Quiz(10, null, "Bank", "Ballspielhaus", "Bank", null, "Konzerthaus", null);
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
		assertEquals("the size of the saved treasures is different to the size that is hold locally! local: " + exampleTreasures.size() + " DB: " + result.size(), exampleTreasures.size(), result.size());
		for(int i = 0; i < exampleTreasures.size(); i++)
			assertTrue("there was saved a treasure although it shouldn't!", exampleTreasures.get(i).equalsWithoutId(result.get(i)));
	}


	@Test
	public void deleteTreasureTest() {
		Quiz quiz1 = new Quiz(10, "Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", "Konzerthaus", null, null);
		int id = DatabaseController.getInstance().saveTreasure(new Treasure(new Treasure.Location(10, 47.26952, 11.39570), quiz1, new Treasure.Size(-1, 20, 1), null));
		DatabaseController.getInstance().deleteTreasure(id);
		List<Treasure> result = DatabaseController.getInstance().getAllTreasures(false);
		Collections.sort(result);
		assertEquals("the size of the saved treasures is different to the size that is hold locally! local: " + exampleTreasures.size() + " DB: " + result.size(), exampleTreasures.size(), result.size());
		for(int i = 0; i < exampleTreasures.size(); i++)
			assertTrue("the saved treasure should have been deleted!", exampleTreasures.get(i).equalsWithoutId(result.get(i)));
	}

	@Test
	public void getTreasuresTest() {
		GeoLocation loc = new GeoLocation(47.26952, 11.39570);
		for (Integer tmp : exampleTreasuresID) {
			DatabaseController.getInstance().activateTreasure(tmp);
		}
		List<Treasure> exampleTreasuresLoc = new ArrayList<Treasure>();
		List<Treasure> result = DatabaseController.getInstance().getTreasures(loc, 2000);
		for (Treasure t : exampleTreasures)
			if (t.getLocation().getDistanceTo(loc) <= 2000) exampleTreasuresLoc.add(t);
		Collections.sort(exampleTreasuresLoc);
		Collections.sort(result);
		assertEquals("the size of the saved treasures is different to the size that is hold locally! local: " + exampleTreasuresLoc.size() + " DB: " + result.size(), exampleTreasuresLoc.size(), result.size());
		for(int i = 0; i < exampleTreasuresLoc.size(); i++)
			assertTrue("the treasures within the radius(2000m) are not equal to the treasures the database gave", exampleTreasuresLoc.get(i).equalsWithoutId(result.get(i)));
		exampleTreasuresLoc = new ArrayList<Treasure>();
		result = DatabaseController.getInstance().getTreasures(loc, 300);
		for (Treasure t : exampleTreasures)
			if (t.getLocation().getDistanceTo(loc) <= 300) exampleTreasuresLoc.add(t);
		Collections.sort(exampleTreasuresLoc);
		Collections.sort(result);
		assertEquals("the size of the saved treasures is different to the size that is hold locally! local: " + exampleTreasuresLoc.size() + " DB: " + result.size(), exampleTreasuresLoc.size(), result.size());
		for(int i = 0; i < exampleTreasuresLoc.size(); i++)
			assertTrue("the treasures within the radius(300m) are not equal to the treasures the database gave", exampleTreasuresLoc.get(i).equalsWithoutId(result.get(i)));
		exampleTreasuresLoc = new ArrayList<Treasure>();
		result = DatabaseController.getInstance().getTreasures(loc, 50);
		for (Treasure t : exampleTreasures)
			if (t.getLocation().getDistanceTo(loc) <= 50) exampleTreasuresLoc.add(t);
		Collections.sort(exampleTreasuresLoc);
		Collections.sort(result);
		
		for(int i = 0; i < exampleTreasuresLoc.size(); i++)
			assertTrue("the treasures within the radius(50m) are not equal to the treasures the database gave", exampleTreasuresLoc.get(i).equalsWithoutId(result.get(i)));
	}

	@Test
	public void getTreasureTest() {
		List<Treasure> result = new ArrayList<Treasure>();
		for (Integer i : exampleTreasuresID) {
			Treasure t = DatabaseController.getInstance().getTreasure(i);
			result.add(t);
			assertEquals("the treasure with id: " + i + " is not equal to the id: " + t.getId() + "!", (Integer)t.getId(), i);
		}
		Collections.sort(result);
		assertEquals("the size of the saved treasures is different to the size that is hold locally! local: " + exampleTreasures.size() + " DB: " + result.size(), exampleTreasures.size(), result.size());
		for(int i = 0; i < exampleTreasures.size(); i++) {
			assertTrue("the treasures aren't equal: \n" + result.get(i) + "\n" + exampleTreasures.get(i), exampleTreasures.get(i).equalsWithoutId(result.get(i)));
		}
	}

	@Test
	public void getAllTreasuresTest() {
		// test all treasures including inactive treasures
		List<Treasure> result = DatabaseController.getInstance().getAllTreasures(false);

		assertNotNull("null returned instead of all treasures", result);

		assertEquals("count of treasures has to be equal", exampleTreasures.size(), result.size() );
		Collections.sort(result);
		for(int i = 0; i < exampleTreasures.size(); i++)
			assertTrue("the treasures aren't equal", exampleTreasures.get(i).equalsWithoutId(result.get(i)));
		// test only treasures that are active
		result = DatabaseController.getInstance().getAllTreasures(true);
		List<Treasure> exampleTreasuresActive = new ArrayList<Treasure>();
		for (Treasure t : result)
			assertTrue("treasure is not active, although it should be", DatabaseController.getInstance().isTreasureActive(t.getId()));
	}
}

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
		Quiz quiz8 = new Quiz(20,"Wie viele Bäcker Ruetz gibt es in Innsbruck?", "16", "7", "pro Einwohner einen", "8", null, null);
		Quiz quiz9 = new Quiz(10,"Wann fanden die ersten Youth Olympic Games in Innsbruck statt?", "2012", "2011", "2008", "2001", null, null);
		Quiz quiz10 = new Quiz(20,"In welchem Jahr wurde Innsbruck Landeshauptstadt von Tirol?", "1849", "1912", "1731", "1818", null, null);
		Quiz quiz11 = new Quiz(30,"Wie viele unterschiedliche Fakultäten besitzt die Universität Innsbruck?", "16", "8", "10", "78", null, null);
		Quiz quiz12 = new Quiz(40,"Was ist das älteste bekannte Gemüse?", "Erbsen", "Karotten", "Birnen", "Äpfel", null, null);
		Quiz quiz13 = new Quiz(20,"Welche Gebirgskette war Schauplatz weltbekannter Filme, wie 'Geiger Wally' oder 'Sissi die Kaiserin'?", "Nordkette", "Silvretta", "Verwall", "Kaisergebirge", null, null);
		Quiz quiz14 = new Quiz(30,"Wieviele verschiedene Tierarten leben im Alpenzoo Innsbruck?", "150", "80", "15", "34", null, null);
		Quiz quiz15 = new Quiz(10,"Was ist auf den Wappen von Innsbruck zu sehen?", "Brücke", "Adler", "Krone", "Sichel", null, null);
		Quiz quiz16 = new Quiz(15,"Wie hoch liegt die Seegrube?", "1905m", "2003m", "1530m", "960m", null, null);
		Quiz quiz17 = new Quiz(35,"Wieviele Passagiere werden durchschnittlich pro Jahr am Innsbrucker Flughafen transportiert?","991.356", "421.758", "125.091", "12.000", null, null);
		Quiz quiz18 = new Quiz(35,"Wieviele Gemeinden grenzen an Innsbruck?", "16", "5", "10", "1000", null, null);
		Quiz quiz19 = new Quiz(25,"Auf welcher bekannten Münze wurde Schloss Ambras verewigt?", "silberne Euro-Gedenkmünze", "goldene Euro-Gedenkmünze", "Wiener Philharmoniker", "American Eagle", null, null);
		Quiz quiz20 = new Quiz(45,"Aus welchen Anlass wurde die Triumphpforte erbaut?", "Hochzeit von Erzherzog Leopold", "Hochzeit von Kaiserin Maria Theresia", "weil es gut aussieht", "Befehl von  Kaiser Franz Joseph", null, null);

		Quiz quiz21 = new Quiz(30,"Wieviele Brunnen stehen in der Innsbrucker Altstadt?", "12", "10", "6", "2", null, null);
		Quiz quiz22 = new Quiz(5,"Wie wurde der Inn früher genannt?", "Innanna", "Innanam", "Rudolf", "Innum", null, null);
		Quiz quiz23 = new Quiz(10,"Die wievielten Olympischen Winterspiele fanden im Jahre 1964 in Innsbruck statt?", "9", "7", "15", "4", null, null);
		Quiz quiz24 = new Quiz(15,"Wieviele Heilige schmücken den Podest der Annasäule?", "4", "5", "2", "3", null, null);
		Quiz quiz25 = new Quiz(25,"Wie wird das Tiroler Landesmuseum sonst noch genannt?", "Ferdinandeum", "Humboldtianum", "Mumok", "Albertina", null, null);
		Quiz quiz26 = new Quiz(20,"Wieviele Stufen musst du gehen um die oberste Plattform des Stadtturms zu erreichen", "148", "2503", "257", "1102", null, null);
		Quiz quiz27 = new Quiz(30,"Was versteht man O-Dorf?", "Olypisches Dorf", "Ossiacher Dorf", "Ober-Dorf", "Ochsen-Dorf", null, null);
		Quiz quiz28 = new Quiz(35,"Wieviele Bezirke besitzt Tirol?", "8", "7", "3","9", null, null);
		Quiz quiz29 = new Quiz(40,"Wann wurde Stift Wilten gegründet?", "880", "1012", "1203", "1621", null, null);
		Quiz quiz30 = new Quiz(45,"Wann wurde die Leopold-Franzens-Universität gegründet?", "1669", "1578", "1861", "1747", null, null);
		Quiz quiz31 = new Quiz(20,"Mit wievielen Fakultäten wurde die Leopold-Franzens-Universität gegründet?", "4", "10", "7", "17", null, null);
		Quiz quiz32 = new Quiz(20,"Wieviele Fakultäten befinden sich auf den Technik Campus?", "4", "5", "7", "2", null, null);
		Quiz quiz33 = new Quiz(20,"Wieviele Fakultäten befinden sich auf den Innrain Campus?", "8", "10", "7", "4", null, null);
		Quiz quiz34 = new Quiz(20,"Mit wievielen Fakultäten wurde die Leopold-Franzens-Universität gegründet?", "4", "10", "7", "17", null, null);
		Quiz quiz35 = new Quiz(20,"Mit wievielen Fakultäten wurde die Leopold-Franzens-Universität gegründet?", "4", "10", "7", "17", null, null);
		Quiz quiz36 = new Quiz(20,"Mit wievielen Fakultäten wurde die Leopold-Franzens-Universität gegründet?", "4", "10", "7", "17", null, null);
		Quiz quiz37 = new Quiz(20,"Mit wievielen Fakultäten wurde die Leopold-Franzens-Universität gegründet?", "4", "10", "7", "17", null, null);
		Quiz quiz38 = new Quiz(20,"Mit wievielen Fakultäten wurde die Leopold-Franzens-Universität gegründet?", "4", "10", "7", "17", null, null);




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
		exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.2634968,11.3996253), quiz9, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2617463,11.4057584), quiz10, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(30, 47.2639078,11.3858713), quiz11, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(20, 47.2675885,11.3916753), quiz12, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(25, 47.2687439,11.39429), quiz13, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(15, 47.2743348,11.4126041), quiz14, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(5, 47.2719325,11.405051), quiz15, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(5, 47.2678976,11.3937696), quiz15, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2644267,11.3896575), quiz15, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(20, 47.3046995,11.3795509), quiz16, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2580885,11.3885631), quiz17, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2578809,11.3538982), quiz17, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2578809,11.3538982), quiz18, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(30, 47.2635993,11.3457473), quiz18, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.256583,11.433619), quiz19, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2657582,11.3991417), quiz20, new Treasure.Size(-1, 20, 1), null));

		exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2657582,11.3991417), quiz21, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2657582,11.3991417), quiz22, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2657582,11.3991417), quiz23, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2657582,11.3991417), quiz24, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2657582,11.3991417), quiz25, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2657582,11.3991417), quiz26, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2657582,11.3991417), quiz27, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2657582,11.3991417), quiz28, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2657582,11.3991417), quiz29, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2657582,11.3991417), quiz30, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(35, 47.2657582,11.3991417), quiz31, new Treasure.Size(-1, 20, 1), null));





		Collections.sort(exampleTreasures);
		// add all the treasures
		for (Treasure t : exampleTreasures) {
			int tmp = DatabaseController.getInstance().saveTreasure(t);
			exampleTreasuresID.add(tmp);
			DatabaseController.getInstance().activateTreasure(tmp);
		}
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
		for (Treasure t : result)
			assertTrue("treasure is not active, although it should be", DatabaseController.getInstance().isTreasureActive(t.getId()));
	}
	
	@Test
	public void activeDeactivateIsActiveTreasureTest() {
		for (int i : exampleTreasuresID) {
			DatabaseController.getInstance().deactivateTreasure(i);
		}
		assertNull("There are active treasure, but there shouldn't be a single one",DatabaseController.getInstance().getAllTreasures(true));
		for (int i = 0; i < exampleTreasuresID.size(); i++) {
			DatabaseController.getInstance().activateTreasure(exampleTreasuresID.get(i));
			if (i == 2) {
				List<Treasure> tmp = DatabaseController.getInstance().getAllTreasures(true);
				assertTrue("There should be 3 active treasure at this point",tmp.size() == 3);
				Collections.sort(tmp);
				for (int j = 0; j < tmp.size(); j++) {
					assertTrue("the active treasures are not the same", tmp.get(j).equalsWithoutId(exampleTreasures.get(j)));
					assertTrue("the treasure should be active",DatabaseController.getInstance().isTreasureActive(tmp.get(j).getId()));
				}
			}
		}
	}
	
	@Test
	public void getallTreasureIdTest() {
		List<Integer> allTreasureID = DatabaseController.getInstance().getallTreasureID(true);
		assertTrue("Ther should be no inactive treasure in the db",allTreasureID.isEmpty());
		
		allTreasureID = DatabaseController.getInstance().getallTreasureID(false);
		Collections.sort(allTreasureID);
		assertTrue("the ids must be the same", allTreasureID.equals(exampleTreasuresID));
		
		DatabaseController.getInstance().deactivateTreasure(exampleTreasuresID.get(0));
		DatabaseController.getInstance().deactivateTreasure(exampleTreasuresID.get(1));
		
		allTreasureID = DatabaseController.getInstance().getallTreasureID(true);
		Collections.sort(allTreasureID);
		assertFalse("the ids must not be the same", allTreasureID.equals(exampleTreasuresID));
		assertTrue("the ids must be the same", allTreasureID.equals(exampleTreasuresID.subList(0, 2)));
		
		DatabaseController.getInstance().activateTreasure(exampleTreasuresID.get(0));
		DatabaseController.getInstance().activateTreasure(exampleTreasuresID.get(1));
	}
}

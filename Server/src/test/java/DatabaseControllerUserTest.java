import communication_controller.json.JsonConstructor;
import data_structures.treasure.Coupon;
import data_structures.treasure.Quiz;
import data_structures.treasure.Treasure;
import data_structures.user.HighscoreList;
import data_structures.user.HighscoreList.Entry;
import data_structures.user.Inventory;
import data_structures.user.User;
import db.DatabaseController;
import static org.junit.Assert.*;

import org.junit.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class DatabaseControllerUserTest {
	List<User> exampleUsers;
	List<Integer> exampleUsersId;
	ArrayList<Integer> exampleTreasuresID;
	ArrayList<Treasure> exampleTreasures;
	
	DatabaseController dC;

	@Before
	public void initialize() {
		dC = DatabaseController.getInstance();
		exampleUsers = new ArrayList<>();
		exampleUsersId = new ArrayList<>();
		//List<Inventory.Entry> inventoryEntries = new ArrayList<>();

		//Inventory inventory = new Inventory();
		exampleUsers.add(new User("Jaqueline", "tqewrtsndgfbre", "Jaqueline@Chantal.at", 2234, 1, new Inventory()));
		exampleUsers.add(new User("Hans", "aasasdadsljaheoh", "hans@franz.at", 1234, 2, new Inventory()));		
		exampleUsers.add(new User("Chantal", "abcdefghijklmnop", "Chantal@Jaqueline.at", 234, 3, new Inventory()));
		exampleUsers.add(new User("Natalie", "abcdefghijklmnop", "natalie@Jaqueline.at", 234, 3, new Inventory()));
		exampleUsers.add(new User("Max", "abcdefghijklmnop", "max@Jaqueline.at", 10, 5, new Inventory()));
		for (User u : exampleUsers) {
			exampleUsersId.add(dC.addUser(u));
		}
		
		Quiz quiz1 = new Quiz(10,"Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", "Konzerthaus", null, null);
		Quiz quiz2 = new Quiz(10,"Wo ist der Rechnerraum 15?", "Uni Innsbruck", "dein zuhause", "Bank", "Konzerthaus", null, null);
		Quiz quiz3 = new Quiz(10,"In welchen Gebäude befindet sich Frau Webber?", "ICT Gebäude", "Rathaus", "Bauingenieurgebäude", "Mensa", "Bei dir zuhause", null);
		Quiz quiz4 = new Quiz(10,"Wo bekommt man den besten Kaffee am Campus?", "Jollys", "ICT Gebäude", "Bauingenieurgebäude", "Mensa", null, null);
		Quiz quiz5 = new Quiz(25,"Wofür ist der Alpenzoo bekannt?", "höchstgelegener Zoo Europas", "artenreichster Zoo Europas", "sauberster Zoo Europas", "was ist ein Zoo?", null, null);
		
		exampleTreasuresID = new ArrayList<Integer>();
		exampleTreasures = new ArrayList<Treasure>();

		exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.26952, 11.39570), quiz1, new Treasure.Size(-1, 20, 1), new Coupon(10, "SuperDuperMarket", 10.50)));
		exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.263372, 11.345269), quiz2, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.263567, 11.345916), quiz3, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.264659,11.3445717), quiz4, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(25, 47.263567, 11.345916), quiz5, new Treasure.Size(-1, 20, 1), null));
		
		Collections.sort(exampleTreasures);
		// add all the treasures
		for (Treasure t : exampleTreasures) {
			int tmp = dC.saveTreasure(t);
			exampleTreasuresID.add(tmp);
			dC.activateTreasure(tmp);
		}
		
	}

	@After
	public void deleteAllTraces() {
		dC.deleteAll();
	}

	@Test
	public void checkUserLoginTest() {
		User tU1 = new User("Hans", "wrongHash", "hans@franz.at", 1234, 2, new Inventory());
		User tU2 = new User("wrongUserName", "tqewrtsndgfbre", "Jaqueline@Chantal.at", 2234, 1, new Inventory());
		assertEquals("user could login although it shouldn't", -1, dC.checkUserLogin(tU1));
		assertEquals("user could login although it shouldn't", -1, dC.checkUserLogin(tU2));
		int i = 0;
		for (User u : exampleUsers) {
			int uid = exampleUsersId.get(i++);
			assertEquals("user couldn't login or the returned user id is wrong", uid, dC.checkUserLogin(u));
		}
	}

	@Test
	public void getUserTestAndRankTest() {
		// some sample data
		int i = 0;
		for( User u : exampleUsers) {
			User rU1 = dC.getUser(exampleUsersId.get(i++));
			User rU2 = dC.getUser(u.getName());
			assertTrue("the user returned was not equal to the given user!", u.equalsWithoutId(rU1));
			assertTrue("the user returned was not equal to the given user!", u.equalsWithoutId(rU2));
		}
	}
	
	@Test
	public void HighscoreListTest() { 
		List<HighscoreList.Entry> hList = dC.getHighscoreList(1, 8).getList();
		
		for (int i = 0; i < hList.size(); i++) {
			Entry entry = hList.get(i);
			int id = entry.getId();
			assertTrue("the highscorelist is not equal to the expected values",dC.getRank(entry.getId()) == entry.getRank());
			assertTrue("the highscorelist is not equal to the expected values",dC.getUser(entry.getId()).getName().equals(entry.getName()));
			assertTrue("the highscorelist is not equal to the expected values",dC.getUser(entry.getId()).getXP() == (entry.getXP()));
		}

	}

	@Test
	public void inventoryTest() {
		Treasure.Content c1 = new Coupon(10, "SuperDuperMarket", 10);
		Treasure.Content c2 = new Coupon(50, "SuperDuperMarket", 50);
		Treasure.Content c3 = new Coupon(100, "ElektroMarkt", 50);
		int c1Id = dC.addContent(c1);
		int c2Id = dC.addContent(c2);
		int c3Id = dC.addContent(c3);
		Treasure.Content c1R = dC.getContent(c1Id);
		Treasure.Content c2R = dC.getContent(c2Id);
		Treasure.Content c3R = dC.getContent(c3Id);
		assertTrue("the content is not equal to the content returned by the DB", c1.equalsWithoudId(c1R));
		assertTrue("the content is not equal to the content returned by the DB", c2.equalsWithoudId(c2R));
		assertTrue("the content is not equal to the content returned by the DB", c3.equalsWithoudId(c3R));

		// easy way to get the id of a user
		int uid1 = dC.checkUserLogin(exampleUsers.get(0));
		int uid2 = dC.checkUserLogin(exampleUsers.get(1));

		List<Inventory.Entry> inventoryEntries = new ArrayList<>();
		inventoryEntries.add(new Inventory.Entry(2, c1R));
		inventoryEntries.add(new Inventory.Entry(1, c2R));
		inventoryEntries.add(new Inventory.Entry(5, c3R));
		dC.insertInInventory(uid1, inventoryEntries);
		dC.insertInInventory(uid2, inventoryEntries.get(2));
		Inventory i1 = new Inventory(inventoryEntries);
		Inventory i1R = dC.getUser(uid1).getInventory();
		Inventory i2 = new Inventory(inventoryEntries);
		Inventory i2R = dC.getUser(uid1).getInventory();

		assertEquals("the length of the inventories is not equal!", i1.getInventoryList().size(), i1R.getInventoryList().size());
		assertEquals("the content count of the inventories is not equal!", i1.getContentCount(), i1R.getContentCount());
		assertEquals("the length of the inventories is not equal!", i2.getInventoryList().size(), i2R.getInventoryList().size());
		assertEquals("the content count of the inventories is not equal!", i2.getContentCount(), i2R.getContentCount());
		// further checks are too elaborate

	}
	
	@Test
	public void openTreasureAndHistoryTest() {
		assertNull("this must be null", dC.getTreasureHistory(exampleUsersId.get(0)));
		
		for (Integer i : exampleTreasuresID) {
			assertTrue("this user needs to be able to open this treasure", dC.allowedToOpenTreasure(exampleUsersId.get(0), i));
			assertTrue("he must be able to open them", dC.openTreasure(exampleUsersId.get(0), i));
		}
		assertNull("There should be no treasure be left", dC.getAllTreasures(true));
		
		for (Integer i : exampleTreasuresID) {
			assertFalse("this user needs to be able to open this treasure, because it is inactive", dC.allowedToOpenTreasure(exampleUsersId.get(0), i));
			dC.activateTreasure(i);
			assertFalse("this user needs not to be able to open this treasure, because he was the last user", dC.allowedToOpenTreasure(exampleUsersId.get(0), i));
			assertTrue("this user needs to be able to open this treasure", dC.allowedToOpenTreasure(exampleUsersId.get(1), i));
		}
		
		Map<Treasure, Long> history = dC.getTreasureHistory(exampleUsersId.get(0));
		assertTrue("the histroy has the wrong size",history.size() == exampleTreasuresID.size());
		
		List<Treasure> historyTreasures = new ArrayList<Treasure>();
		historyTreasures.addAll(history.keySet());
		Collections.sort(historyTreasures);
		for (int j = 0; j < historyTreasures.size(); j++) {
			assertTrue("the active treasures are not the same", historyTreasures.get(j).equalsWithoutId(exampleTreasures.get(j)));
			assertTrue("the treasure should be active",dC.isTreasureActive(historyTreasures.get(j).getId()));
		}
			
	}
	
	
}

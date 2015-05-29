import communication_controller.json.JsonConstructor;
import data_structures.treasure.Coupon;
import data_structures.treasure.Treasure;
import data_structures.user.Inventory;
import data_structures.user.User;
import db.DatabaseController;

import static org.junit.Assert.*;

import org.junit.*;

import java.util.ArrayList;
import java.util.List;

public class DatabaseControllerUserTest {
	List<User> exampleUsers;
	List<Integer> exampleUsersId;
	DatabaseController dC;

	@Before
	public void initialize() {
		dC = DatabaseController.getInstance();
		exampleUsers = new ArrayList<>();
		exampleUsersId = new ArrayList<>();
		List<Inventory.Entry> inventoryEntries = new ArrayList<>();

		Inventory inventory = new Inventory();
		exampleUsers.add(new User("Hans", "aasasdadsljaheoh", "hans@franz.at", 1234, 2, new Inventory()));
		exampleUsers.add(new User("Jaqueline", "tqewrtsndgfbre", "Jaqueline@Chantal.at", 2234, 1, new Inventory()));
		exampleUsers.add(new User("Chantal", "abcdefghijklmnop", "Chantal@Jaqueline.at", 234, 3, new Inventory()));
		for (User u : exampleUsers) {
			exampleUsersId.add(dC.addUser(u));
		}
	}

	@After
	public void deleteAllTraces() {
		DatabaseController.getInstance().deleteAll();
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
	public void getUserTest() {
		// some sample data
		int i = 0;
		for( User u : exampleUsers) {
			User rU1 = DatabaseController.getInstance().getUser(exampleUsersId.get(i++));
			User rU2 = DatabaseController.getInstance().getUser(u.getName());
			assertTrue("the user returned was not equal to the given user!", u.equalsWithoutId(rU1));
			assertTrue("the user returned was not equal to the given user!", u.equalsWithoutId(rU2));
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
}

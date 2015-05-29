import static org.junit.Assert.*;

import communication_controller.json.JsonConstructor;
import data_structures.treasure.Quiz;
import data_structures.treasure.Treasure;
import data_structures.user.HighscoreList;
import data_structures.user.Inventory;
import data_structures.user.User;
import org.junit.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonConstructorTest {
	List<Treasure> exampleTreasures;
	List<User> exampleUsers;
	List<HighscoreList> exampleHighscoreLists;
	JsonConstructor jsonC;

	@Before
	public void initialize() {
		// TODO add more test data
		jsonC = new JsonConstructor();
		Quiz quiz1 = new Quiz(10, "Aus was für einem Gebäude entstand das Landestheater?", "Ballspielhaus", "Rathaus", "Bank", "Konzerthaus", null, null);
		exampleTreasures = new ArrayList<Treasure>();
		exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.26952, 11.39570), quiz1, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(null, quiz1, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.26952, 11.39570), null, new Treasure.Size(-1, 20, 1), null));
		exampleTreasures.add(new Treasure(new Treasure.Location(10, 47.26952, 11.39570), quiz1, null, null));
		exampleUsers = new ArrayList<>();
		List<Inventory.Entry> inventoryEntries = new ArrayList<>();
		inventoryEntries.add(new Inventory.Entry(1, null));
		inventoryEntries.add(new Inventory.Entry(2, null));
		Inventory i = new Inventory(inventoryEntries);
		exampleUsers.add(new User("Hans", "aasasdadsljaheoh", "hans@franz.at", 1234, 1, i));
		exampleUsers.add(new User("Hans", "aasasdadsljaheoh", "hans@franz.at", 1234, 1, null));
		exampleUsers.add(new User("Hans", null, "hans@franz.at", 1234, 1, i));
		exampleHighscoreLists = new ArrayList<>();

		List<HighscoreList.Entry> highscoreListEntries = new ArrayList<>();
		highscoreListEntries.add(new HighscoreList.Entry("Hans", 1, 1234));
		highscoreListEntries.add(new HighscoreList.Entry("Max", 2, 1134));
		highscoreListEntries.add(new HighscoreList.Entry("Vanessa", 3, 666));
		highscoreListEntries.add(new HighscoreList.Entry("Rupert", 4, 192));
		highscoreListEntries.add(new HighscoreList.Entry("Mogli", 5, 128));
		exampleHighscoreLists.add(new HighscoreList(1, new ArrayList<HighscoreList.Entry>(highscoreListEntries)));
		highscoreListEntries.add(new HighscoreList.Entry("Jaqueline", 6, 111));
		exampleHighscoreLists.add(new HighscoreList(1, new ArrayList<HighscoreList.Entry>(highscoreListEntries)));
		highscoreListEntries.add(new HighscoreList.Entry("Chantal", 7, 99));
		exampleHighscoreLists.add(new HighscoreList(1, new ArrayList<HighscoreList.Entry>(highscoreListEntries)));
		Collections.sort(exampleTreasures);
		Collections.sort(exampleUsers);
		Collections.sort(exampleHighscoreLists);
	}

	@Test
	public void treasureTest() {
		List<String> jsonExampleTreasures = new ArrayList<>();
		for (Treasure t : exampleTreasures) {
			jsonExampleTreasures.add(jsonC.toJson(t));
		}
		List<Treasure> result = new ArrayList<>();
		for (String s : jsonExampleTreasures) {
			result.add(jsonC.fromJson(s, Treasure.class));
		}
		Collections.sort(result);
		assertEquals("the treasures aren't equal", exampleTreasures, result);
	}

	@Test
	public void userTest() {
		List<String> jsonExampleUsers = new ArrayList<>();
		for (User u : exampleUsers) {
			jsonExampleUsers.add(jsonC.toJson(u));
		}
		List<User> result = new ArrayList<>();
		for (String s : jsonExampleUsers) {
			result.add(jsonC.fromJson(s, User.class));
		}
		Collections.sort(result);
		assertEquals("the users aren't equal", exampleUsers, result);
	}

	@Test
	public void highscoreListTest() {
		List<String> jsonExampleHighscoreLists = new ArrayList<>();
		for (HighscoreList h : exampleHighscoreLists) {
			jsonExampleHighscoreLists.add(jsonC.toJson(h));
		}
		List<HighscoreList> result = new ArrayList<>();
		for (String s : jsonExampleHighscoreLists) {
			result.add(jsonC.fromJson(s, HighscoreList.class));
		}
		Collections.sort(result);
		assertEquals("the HighscoreLists aren't equal", exampleHighscoreLists, result);
	}

}

import static org.junit.Assert.*;

import communication_protocol.json.JsonConstructor;
import data_structures.treasure.Quiz;
import data_structures.treasure.Treasure;
import data_structures.user.User;
import org.junit.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JsonConstructorTest {
	List<Treasure> exampleTreasures;
	List<User> exampleUsers;
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
		exampleUsers.add(new User("Hans","aasasdadsljaheoh", "hans@franz.at", 1234, 1, null));
		Collections.sort(exampleTreasures);
	}

	@Test
	public void treasureTest() {
		List<String> jsonExampleTreasures = new ArrayList<>();
		for(Treasure t : exampleTreasures) {
			jsonExampleTreasures.add(jsonC.toJson(t));
		}
		List<Treasure> result = new ArrayList<>();
		for(String s : jsonExampleTreasures) {
			result.add(jsonC.fromJson(s,Treasure.class));
		}
		Collections.sort(result);
		assertEquals("the treasures aren't equal", exampleTreasures, result);
	}
	@Test
	public void userTest() {
		List<String> jsonExampleUsers = new ArrayList<>();
		for(User u : exampleUsers) {
			jsonExampleUsers.add(jsonC.toJson(u));
		}
		List<User> result = new ArrayList<>();
		for(String s : jsonExampleUsers) {
			result.add(jsonC.fromJson(s,User.class));
		}
		Collections.sort(result);
		assertEquals("the treasures aren't equal", exampleUsers, result);

	}

}

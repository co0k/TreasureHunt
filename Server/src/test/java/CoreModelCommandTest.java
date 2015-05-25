import core.CoreModel;

import static org.junit.Assert.*;

import core.commands.*;
import data_structures.user.Inventory;
import data_structures.user.User;
import db.DatabaseController;
import org.junit.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class CoreModelCommandTest {
	@Test
	public void activeTokenTest() throws ExecutionException, InterruptedException {
		// it could be easier to get or set a token, but to keep it clean, every command to the core should go
		// through the Command Queue bus
		Future<Boolean> v = CoreModel.getInstance().addCommand(new IsActiveTokenCommand(10));
		assertFalse("the Token has to be unset!", v.get());

		CoreModel.getInstance().addCommand(new SetActiveTokenCommand(10)).get();

		v = CoreModel.getInstance().addCommand(new IsActiveTokenCommand(10));
		assertTrue("the Token has to be set!", v.get());
	}

	@Test
	public void checkUserLoginTest() throws ExecutionException, InterruptedException {
		// some sample data
		List<User> exampleUsers = new ArrayList<>();
		exampleUsers.add(new User("Hans", "aasasdadsljaheoh", "hans@franz.at", 1234, 1, null));
		exampleUsers.add(new User("Jaqueline", "tqewrtsndgfbre", "Jaqueline@Chantal.at", 2234, 1, null));
		exampleUsers.add(new User("Chantal", "abcdefghijklmnop", "Chantal@Jaqueline.at", 234, 1, null));

		for(User u : exampleUsers)
			assertFalse("the user does not exist, so it can't login!", CoreModel.getInstance().addCommand(new CheckUserLoginCommand(u)).get());

		for(User u : exampleUsers)
			DatabaseController.getInstance().addUser(u);

		for(User u : exampleUsers)
			assertTrue("the user does exist, so it can login!", CoreModel.getInstance().addCommand(new CheckUserLoginCommand(u)).get());
		User wrongPasswdUser = new User("Hans", "wrongPasswordHash", "hans@franz.at", 1234, 1, null);
		assertFalse("the user does not exist, so it can't login!", CoreModel.getInstance().addCommand(new CheckUserLoginCommand(wrongPasswdUser)).get());

	}

/*  will fail since there can't be added a inventory yet
	@Test
	public void getUserByNameTest() {
		// some sample data
		List<User> exampleUsers = new ArrayList<>();
		List<Inventory.Entry> inventoryEntries = new ArrayList<>();
		inventoryEntries.add(new Inventory.Entry(1, null));
		inventoryEntries.add(new Inventory.Entry(2, null));
		Inventory i = new Inventory(inventoryEntries);
		exampleUsers.add(new User("Hans", "aasasdadsljaheoh", "hans@franz.at", 1234, 1, i));
		exampleUsers.add(new User("Jaqueline", "tqewrtsndgfbre", "Jaqueline@Chantal.at", 2234, 1, null));
		exampleUsers.add(new User("Chantal", "abcdefghijklmnop", "Chantal@Jaqueline.at", 234, 1, i));
	}*/
}

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
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class CoreModelCommandTest {
	@After
	public void deleteAllTraces() {
		DatabaseController.getInstance().deleteAll();
	}
	@Test
	public void activeTokenTest() throws ExecutionException, InterruptedException, TimeoutException {
		// it could be easier to get or set a token, but to keep it clean, every command to the core should go
		// through the Command Queue bus
		Future<Boolean> v = CoreModel.getInstance().addCommand(new IsActiveTokenCommand(10));
		assertFalse("the Token has to be unset!", v.get(5, TimeUnit.SECONDS));

		CoreModel.getInstance().addCommand(new SetActiveTokenCommand(10)).get(5, TimeUnit.SECONDS);

		v = CoreModel.getInstance().addCommand(new IsActiveTokenCommand(10));
		assertTrue("the Token has to be set!", v.get(5, TimeUnit.SECONDS));
	}

	@Test
	public void checkUserLoginTest() throws ExecutionException, InterruptedException, TimeoutException {
		// some sample data
		List<User> exampleUsers = new ArrayList<>();
		exampleUsers.add(new User("Hans", "aasasdadsljaheoh", "hans@franz.at", 1234, 1, null));
		exampleUsers.add(new User("Jaqueline", "tqewrtsndgfbre", "Jaqueline@Chantal.at", 2234, 1, null));
		exampleUsers.add(new User("Chantal", "abcdefghijklmnop", "Chantal@Jaqueline.at", 234, 1, null));

		for(User u : exampleUsers)
			assertTrue("the user does not exist, so it can't login!", null == CoreModel.getInstance().addCommand(new CheckUserLoginCommand(u)).get(5, TimeUnit.SECONDS));

		for(User u : exampleUsers)
			DatabaseController.getInstance().addUser(u);

		for(User u : exampleUsers)
			assertTrue("the user does exist, so it can login!", null != CoreModel.getInstance().addCommand(new CheckUserLoginCommand(u)).get(5, TimeUnit.SECONDS));
		User wrongPasswdUser = new User("Hans", "wrongPasswordHash", "hans@franz.at", 1234, 1, null);
		assertFalse("the user does not exist, so it can't login!", null != CoreModel.getInstance().addCommand(new CheckUserLoginCommand(wrongPasswdUser)).get(5, TimeUnit.SECONDS));

	}
}

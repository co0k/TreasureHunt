import core.CoreModel;

import static org.junit.Assert.*;

import core.commands.*;
import org.junit.*;

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
}

import core.CoreModel;

import static org.junit.Assert.*;

import org.junit.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Future;

public class CoreModelCommandQueueTest {

	@Test
	public void priorityOrderTest() throws InterruptedException {
		List<Integer> resultList = new ArrayList<>();
		// create some test commands
		// dummy commands are necessary, since it *will* be executed first
		// multiple commands are used, since then it is almost sure that there is no race condition afterwards
		TestCommand dummy = new TestCommand(0, 0);
		// let the core model be busy for at least one second
		for(int i = 0; i < 1000; i += 20)
			CoreModel.getInstance().addCommand(dummy);

		// 20 commands
		Random rand = new Random();
		List<Integer> expectedList = new ArrayList<>();
		List<Future<Integer>> fCList = new ArrayList<>();
		List<Thread> tList = new ArrayList<>();
		for (int i = 0; i < 50; i++) {
			int value = rand.nextInt(100);
			expectedList.add(value);
			Future<Integer> fC = CoreModel.getInstance().addCommand(new TestCommand(value, value));
			fCList.add(fC);
			// create for every Future a thread, since it is otherwise 'impossible' to get the correct execution order
			Thread t = new Thread(() -> {
				Integer v = null;
				try {
					v = fC.get();
				} catch (Exception e) {
					fail("an exception was thrown!" + e.getStackTrace());
				}
				synchronized (resultList) {
					resultList.add(v);
				}
			});
			tList.add(t);
			t.start();
		}
		Collections.sort(expectedList, (v1, v2) -> v2 - v1);
		for (Thread t : tList)
			t.join();
		assertEquals("the resulting list has not the same size as expected", expectedList.size(), resultList.size());
		assertEquals("the resulting list has not the same order as expected", expectedList, resultList);
	}

	/****************
	 * Test Command *
	 ****************/

	private static class TestCommand implements core.Command<Integer> {

		private int priority;
		private Integer value;

		public TestCommand(int priority, Integer value) {
			this.priority = priority;
			this.value = value;
		}

		@Override
		public int getPriority() {
			return priority;
		}

		@Override
		public Integer execute() throws InterruptedException {
			// wait a little bit, so that race conditions are mostly unlikely
			Thread.sleep(20);
			return value;
		}
	}
}

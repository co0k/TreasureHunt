import core.CoreModel;

import static org.junit.Assert.*;

import org.junit.*;

import java.util.*;
import java.util.concurrent.Future;

public class CoreModelCommandQueueTest {

	@After
	public void deleteAllTraces() {
		CoreModel.getInstance().reset();
	}
	@Test
	public void priorityOrderTest() throws InterruptedException {
		final List<Integer> resultList = new ArrayList<>();
		// create some test commands
		// dummy commands are necessary, since it *will* be executed first
		// multiple commands are used, since then it is almost sure that there is no race condition afterwards
		TestCommand dummy = new TestCommand(0, 0, 2000);
		TestCommand dummy2 = new TestCommand(0, 0, 500);
		// let the core model be busy for at least one second
		CoreModel.getInstance().addCommand(dummy);
		CoreModel.getInstance().addCommand(dummy2);

		// 20 commands
		Random rand = new Random();
		List<Integer> expectedList = new ArrayList<>();
		List<Future<Integer>> fCList = new ArrayList<>();
		List<Thread> tList = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			int value = rand.nextInt(100);
			expectedList.add(value);
			final Future<Integer> fC = CoreModel.getInstance().addCommand(new TestCommand(value, value, 80));
			fCList.add(fC);
			// create for every Future a thread, since it is otherwise 'impossible' to get the correct execution order
			Thread t = new Thread(new Runnable() {
				@Override
				public void run() {
					Integer v = null;
					try {
						v = fC.get();
					} catch (Exception e) {
						fail("an exception was thrown!" + e.getStackTrace());
					}
					synchronized (resultList) {
						resultList.add(v);
					}
				}
			});
			tList.add(t);
			t.start();
		}
		Collections.sort(expectedList, new Comparator<Integer>() {
			@Override
			public int compare(Integer v1, Integer v2) {
				return v2 - v1;
			}
		});
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
		private int timeToWait;

		public TestCommand(int priority, Integer value, int timeToWait) {
			this.priority = priority;
			this.value = value;
			this.timeToWait = timeToWait;
		}

		@Override
		public int getPriority() {
			return priority;
		}

		@Override
		public Integer execute() throws InterruptedException {
			// wait a little bit, so that race conditions are mostly unlikely
			Thread.sleep(timeToWait);
			return value;
		}
	}
}

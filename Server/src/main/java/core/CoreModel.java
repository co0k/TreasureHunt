package core;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Future;

public class CoreModel implements CommunicationControllerDAO {

	private Queue<FutureCommand> commandQueue;
	private Thread cQEThread;


	private CoreModel() {
		this.commandQueue = new PriorityQueue<>(10, (fC1, fC2) -> fC2.getPriority() - fC1.getPriority());
		this.cQEThread = new Thread(new CommandQueueExecutioner());
		this.cQEThread.start();
	}

	// Singleton
	private static class CoreModelHolder {
		private static final CoreModel INSTANCE = new CoreModel();
	}

	public static CoreModel getInstance() {
		return CoreModelHolder.INSTANCE;
	}

	@Override
	public <V> Future<V> addCommand(Command<V> command) {
		if (command != null) {
			FutureCommand<V> retVal = new FutureCommand<>(command);
			synchronized (commandQueue) {
				commandQueue.add(retVal);
				commandQueue.notifyAll();
			}
			return retVal;
		}
		return null;
	}

	/**
	 * The main loop for the command queue
	 */
	private class CommandQueueExecutioner implements Runnable {

		@Override
		public void run() {
			while (!Thread.interrupted()) {
				FutureCommand f = null;

				synchronized (commandQueue) {
					if (!commandQueue.isEmpty())
						f = commandQueue.poll();
				}

				// execute the current command at the head of the queue;
				if (f != null) {
					try {
						f.executeCommand();
					} catch (InterruptedException e) {
						return;
					}
				}

				synchronized (commandQueue) {
					if (commandQueue.isEmpty()) {
						try {
							commandQueue.wait();
						} catch (InterruptedException e) {
							return;
						}
					}
				}
			}
		}
	}
}

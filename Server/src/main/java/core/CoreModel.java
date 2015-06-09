package core;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.Future;

public class CoreModel implements CommunicationControllerDAO {

	private Queue<FutureCommand> commandQueue;
	private Thread cQEThread;
	private ActiveTokens activeTokens;


	private CoreModel() {
		this.commandQueue = new PriorityQueue<>(10, new Comparator<FutureCommand>() {
			@Override
			public int compare(FutureCommand fC1, FutureCommand fC2) {
				return fC2.getPriority() - fC1.getPriority();
			}
		});
		this.cQEThread = new Thread(new CommandQueueExecutioner());
		this.cQEThread.start();
		// token timeout set to 60 seconds
		this.activeTokens = new ActiveTokens(60);

	}

	// Singleton
	private static class CoreModelHolder {
		private static final CoreModel INSTANCE = new CoreModel();
	}

	public static CoreModel getInstance() {
		return CoreModelHolder.INSTANCE;
	}

	public boolean isActive(int token) {
		return activeTokens.isActive(token);
	}

	public void setActive(int token) {
		activeTokens.setActive(token);
	}

	public void reset() {
		this.cQEThread.interrupt();
		synchronized (commandQueue) {
			this.commandQueue = new PriorityQueue<>(10, new Comparator<FutureCommand>() {
				@Override
				public int compare(FutureCommand fC1, FutureCommand fC2) {
					return fC2.getPriority() - fC1.getPriority();
				}
			});
		}
		this.cQEThread = new Thread(new CommandQueueExecutioner());
		this.cQEThread.start();
		// token timeout set to 60 seconds
		this.activeTokens = new ActiveTokens(60);
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
					if (!f.isCancelled()) {
						try {
							f.executeCommand();
						} catch (InterruptedException e) {
							return;
						}
					}
				}

				synchronized (commandQueue) {
					if (commandQueue.isEmpty()) {
						try {
							commandQueue.wait(500);
						} catch (InterruptedException e) {
							return;
						}
					}
				}
			}
		}
	}
}

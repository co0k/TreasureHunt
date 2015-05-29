package core;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class FutureCommand<V> implements Future<V> {
	private V value;
	private Command<V> command;
	private boolean isDone;
	private boolean isCancelled;

	public FutureCommand(Command<V> command) {
		this.command = command;
		this.isDone = false;
		this.isCancelled = false;
		this.value = null;
	}

	public synchronized void executeCommand() throws InterruptedException {
		this.value = command.execute();
		this.isDone = true;
		notifyAll();
	}

	public int getPriority() {
		return command.getPriority();
	}

	@Override
	public synchronized boolean cancel(boolean b) {
		this.isCancelled = b;
		return true;
	}

	@Override
	public synchronized boolean isCancelled() {
		return isCancelled;
	}

	@Override
	public boolean isDone() {
		return isDone;
	}

	@Override
	public synchronized V get() throws InterruptedException, ExecutionException {
		while (!this.isDone)
			wait();
		return value;
	}

	@Override
	public synchronized V get(long timeout, TimeUnit timeUnit) throws InterruptedException, ExecutionException, TimeoutException {
		while (!this.isDone) {
			long tBefore = System.currentTimeMillis();
			// change the time prefix to milliseconds
			switch (timeUnit) {
				case NANOSECONDS:
					timeout /= 1000 * 1000;
					break;
				case MICROSECONDS:
					timeout /= 1000;
					break;
				case MILLISECONDS:
					break;
				case SECONDS:
					timeout *= 1000;
					break;
				case MINUTES:
					timeout *= 60 * 1000;
					break;
				case HOURS:
					timeout *= 60 * 60 * 1000;
					break;
				case DAYS:
					timeout *= 24 * 60 * 60 * 1000;
					break;
			}
			// wait till the commandQueue executed this command
			wait(timeout);
			if ((System.currentTimeMillis() - tBefore) >= timeout)
				throw new TimeoutException();
		}
		return value;
	}
}


package core;

public interface Command<V> {
	/**
	 * @return the priority, where a high value is a higher priority than a lower value
	 */
	int getPriority();

	/**
	 * @return the value, that will be created during execution
	 * @throws InterruptedException if the execution got interrupted
	 */
	V execute() throws InterruptedException;
}

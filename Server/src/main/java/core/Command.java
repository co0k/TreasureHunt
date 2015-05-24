package core;

public interface Command<V> {
	int getPriority();
	V execute() throws InterruptedException;
}

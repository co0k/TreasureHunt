package core;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ActiveTokens {
	/**
	 * in seconds
	 */
	private long timeout;
	private Map<Integer, Long> tokenTimeoutMap;
	private final ScheduledExecutorService scheduler;

	public ActiveTokens(int timeout) {
		this.timeout = timeout;
		tokenTimeoutMap = new HashMap<>();
		scheduler = Executors.newScheduledThreadPool(1);
		scheduler.scheduleAtFixedRate(new TimeoutTimerTask(), 0, 1, TimeUnit.SECONDS);
	}


	public boolean isActive(int token) {
		synchronized (tokenTimeoutMap) {
			if (!tokenTimeoutMap.containsKey(token))
				return false;
			else
				tokenTimeoutMap.put(token, timeout);
			return true;
		}
	}

	public void setActive(int token) {
		synchronized (tokenTimeoutMap) {
			tokenTimeoutMap.put(token, timeout);
		}
	}

	/**
	 * decrements the timeout every second
	 */
	private class TimeoutTimerTask implements Runnable {

		@Override
		public void run() {
			synchronized (tokenTimeoutMap) {
				for (Map.Entry<Integer, Long> entry : tokenTimeoutMap.entrySet()) {
					if (entry.getValue() <= 0)
						tokenTimeoutMap.remove(entry.getKey());
					else
						tokenTimeoutMap.put(entry.getKey(), entry.getValue() - 1);
				}
			}
		}
	}
}

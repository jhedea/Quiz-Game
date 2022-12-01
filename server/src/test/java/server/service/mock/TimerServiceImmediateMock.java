package server.service.mock;

import server.service.TimerService;

/**
 * A mock TimerService class that enables testing without timer delay for methods that use the timer.
 */
public class TimerServiceImmediateMock implements TimerService {
	@Override
	public long getTime() {
		return System.currentTimeMillis();
	}

	@Override
	public void scheduleTimer(int timerId, long delay, Runnable runnable) {
		runnable.run();
	}

	@Override
	public void rescheduleTimer(int timerId, long delay) {
		throw new RuntimeException(
				"Operation not supported by this mock. For richer functionality use TimeServiceControllableMock.java"
		);
	}

	@Override
	public long getRemainingTime(int timerId) {
		throw new RuntimeException(
				"Operation not supported by this mock. For richer functionality use TimeServiceControllableMock.java"
		);
	}
}

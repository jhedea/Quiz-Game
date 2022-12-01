package server.service.mock;

import server.service.TimerService;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * A mock TimerService class that enables simulation of passing time without the need to actually wait
 */
public class TimerServiceControllableMock implements TimerService {

	private static class Task {
		public final Runnable runnable;

		public long endTime;

		Task(Runnable runnable) {
			this.runnable = runnable;
		}
	}

	private final Map<Integer, Task> taskMap = new HashMap<>();

	private long currentTime;

	@Override
	public long getTime() {
		return currentTime;
	}

	@Override
	public void scheduleTimer(int timerId, long delay, Runnable runnable) {
		var task = new Task(runnable);
		taskMap.put(timerId, task);

		task.endTime = currentTime + delay;
	}

	@Override
	public void rescheduleTimer(int timerId, long delay) {
		var task = taskMap.get(timerId);
		if (task == null) throw new IllegalStateException("Timer with given id is not scheduled.");

		task.endTime = currentTime + delay;
	}

	@Override
	public long getRemainingTime(int timerId) {
		var task = taskMap.get(timerId);
		if (task == null) throw new IllegalStateException("Timer with given id is not scheduled.");

		return task.endTime - getTime();
	}

	/**
	 * Moves the time forward by [timeIncrement].
	 * If there are any timers that need to be executed in the time frame before and after the increment,
	 * they are executed in order.
	 *
	 * @param timeIncrement time increment
	 */
	public void advanceBy(long timeIncrement) {
		currentTime += timeIncrement;

		taskMap.entrySet().stream()
				.filter(entry -> entry.getValue().endTime <= currentTime)
				.sorted(Comparator.comparingLong(entry -> entry.getValue().endTime))
				.forEachOrdered(entry -> {
					entry.getValue().runnable.run();
					taskMap.remove(entry.getKey());
				});
	}
}

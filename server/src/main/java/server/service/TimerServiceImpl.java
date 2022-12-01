package server.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

@Service
public class TimerServiceImpl implements TimerService {

	private static class Task {
		public final Runnable runnable;

		public long endTime;
		public TimerTask timerTask;

		Task(Runnable runnable) {
			this.runnable = runnable;
		}
	}

	private final Map<Integer, Task> taskMap = new HashMap<>();
	private final Timer timer = new Timer();

	@Override
	public long getTime() {
		return System.currentTimeMillis();
	}

	@Override
	public void scheduleTimer(int timerId, long delay, Runnable runnable) {
		var oldTask = taskMap.get(timerId);
		if (oldTask != null) oldTask.timerTask.cancel();

		var task = new Task(runnable);
		taskMap.put(timerId, task);

		updateTask(task, delay);
	}

	@Override
	public void rescheduleTimer(int timerId, long delay) {
		var task = taskMap.get(timerId);
		if (task == null) throw new IllegalStateException("Timer with given id is not scheduled.");

		updateTask(task, delay);
	}

	@Override
	public long getRemainingTime(int timerId) {
		var task = taskMap.get(timerId);
		if (task == null) throw new IllegalStateException("Timer with given id is not scheduled.");

		return task.endTime - getTime();
	}

	private void updateTask(Task task, long delay) {
		if (task.timerTask != null) task.timerTask.cancel();

		task.endTime = getTime() + delay;
		task.timerTask = new TimerTask() {
			@Override
			public void run() {
				task.runnable.run();
			}
		};

		timer.schedule(task.timerTask, delay);
	}
}

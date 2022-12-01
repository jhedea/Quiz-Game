package server.service;

/**
 * Service for the timer
 */
public interface TimerService {
	/**
	 * Provides the time at the current moment.
	 *
	 * @return returns the current time in milliseconds
	 */
	long getTime();

	/**
	 * Schedules a timer for a runnable task.
	 * If a timer with given id is already scheduled, it is cancelled and a new timer is set.
	 *
	 * @param timerId the id of the timer
	 * @param delay the desired delay before running the runnable task, should be bigger than 0
	 * @param runnable the task to run after the delay
	 */
	void scheduleTimer(int timerId, long delay, Runnable runnable);

	/**
	 * Reschedules a previously set timer without changing the runnable.
	 *
	 * @param timerId the id of the timer
	 * @param delay the updated amount of delay
	 * @throws IllegalStateException if timer with given id is not scheduled
	 */
	void rescheduleTimer(int timerId, long delay);

	/**
	 * Returns the remaining delay time for a specific timer.
	 *
	 * @param timerId the id of the timer
	 * @return returns the remaining time
	 */
	long getRemainingTime(int timerId);
}

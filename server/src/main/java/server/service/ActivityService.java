package server.service;

import commons.model.Activity;

import java.util.List;

/**
 * Activity management service
 */
public interface ActivityService {
	/**
	 * Adds specified activities.
	 *
	 * @param activities activities to add
	 * @return created activities
	 */
	List<Activity> addActivities(List<Activity> activities);

	/**
	 * Provides a list with all activities currently in the repository.
	 *
	 * @return returns a list with all activities
	 */
	List<Activity> provideActivities();

	/**
	 * Removes all activities.
	 */
	void removeAllActivities();

	/**
	 * Removes one activity.
	 *
	 * @param id the id of the activity to remove
	 */
	void removeActivity(long id);

	/**
	 * Updates an activity in the repository.
	 * If an activity with the given id doesn't exist, adds the provided activity to the repository instead of updating.
	 *
	 * @param activityId the id of the activity to be updated
	 */
	void updateActivity(long activityId, Activity activity);
}

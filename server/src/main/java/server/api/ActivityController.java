package server.api;

import commons.model.Activity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import server.exception.IdNotFoundException;
import server.service.ActivityService;
import server.service.FileStorageService;

import javax.persistence.EntityNotFoundException;
import java.util.List;

/**
 * Controller for admin panel interactions
 */
@RestController
@RequestMapping("api/activities")
public class ActivityController {
	private final ActivityService activityService;
	private final FileStorageService storageService;

	public ActivityController(ActivityService activityService, FileStorageService storageService) {
		this.activityService = activityService;
		this.storageService = storageService;
	}

	/**
	 * Provides a list of all activities on the server.
	 *
	 * @return returns a list of activities
	 */
	@GetMapping
	public List<Activity> getActivities() {
		return activityService.provideActivities();
	}

	/**
	 * Adds new activities.
	 *
	 * @param activities list of activities to add
	 */
	@PostMapping
	public List<Activity> addActivities(@RequestBody List<Activity> activities) {
		return activityService.addActivities(activities);
	}

	/**
	 * Removes all activities.
	 */
	@DeleteMapping
	public void removeAllActivities() {
		activityService.removeAllActivities();
		storageService.deleteAll();
	}

	/**
	 * Removes one activity from the DB.
	 *
	 * @param id the id of the activity to remove
	 */
	@DeleteMapping("/{id}")
	public void removeOneActivity(@PathVariable long id) {
		try {
			activityService.removeActivity(id);
		} catch (EntityNotFoundException e) {
			throw new IdNotFoundException();
		}
	}

	/**
	 * Updates the contents of a single activity.
	 * Throws an IdNotFoundException if there isn't an activity with the id specified by the user.
	 *
	 * @param id the id of the activity to update
	 * @param activity the activity to replace the old activity with
	 */
	@PutMapping("/{id}")
	public void updateActivity(@PathVariable long id, @RequestBody Activity activity) {
		try {
			activityService.updateActivity(id, activity);
		} catch (EntityNotFoundException e) {
			throw new IdNotFoundException();
		}
	}

	/**
	 * Uploads a file (such as activity's image) and saves it on the server
	 *
	 * @param file uploaded multipart file
	 */
	@PostMapping("/imageUpload")
	public void uploadFile(@RequestParam("file") MultipartFile file, @RequestParam String imagePath) {
		storageService.save(file, imagePath);
	}
}

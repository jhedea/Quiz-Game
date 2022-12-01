package importer.api;

import commons.model.Activity;

import java.io.File;
import java.util.List;

/**
 * Server API for managing activities
 */
public interface ActivityApi {
	/**
	 * Adds activities on the server.
	 *
	 * @param serverUrl server url
	 * @param activities activities to add
	 */
	void addActivities(String serverUrl, List<Activity> activities);

	/**
	 * Uploads an image to the server.
	 *
	 * @param serverUrl server url
	 * @param imagePath path the image will be available on the server, relative to /images/, e.g.:
	 *  image uploaded with path 51/fridge.jpeg will be available at localhost:8080/images/51/fridge.jpeg
	 * @param image image file to upload
	 */
	void uploadImage(String serverUrl, String imagePath, File image);

	/**
	 * Deletes all activities on the server.
	 *
	 * @param serverUrl server url
	 */
	void deleteAllActivities(String serverUrl);
}

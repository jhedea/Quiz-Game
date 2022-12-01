package importer.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import commons.model.Activity;

/**
 * Model representing an activity from the ActivityBank
 */
public record ImportedActivity(
		String id,
		@JsonProperty("image_path") String imagePath,
		String title,
		@JsonProperty("consumption_in_wh") long energyInWh,
		String source
) {
	public Activity toModel(String imageUrlPrefix) {
		return new Activity(-1, title, imageUrlPrefix + imagePath, energyInWh);
	}
}

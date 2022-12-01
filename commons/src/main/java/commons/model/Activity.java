package commons.model;

/**
 * Model of an activity
 */
public record Activity(long id, String name, String imageUrl, float energyInWh) {
	public Activity copyWithImageUrl(String imageUrl) {
		return new Activity(id, name, imageUrl, energyInWh);
	}
}

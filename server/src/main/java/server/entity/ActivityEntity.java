package server.entity;

import commons.model.Activity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * DB entity for an activity
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ActivityEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NonNull
	private String name;

	@Nullable
	private String imageUrl;

	private float energyInWh;

	public static ActivityEntity fromModel(Activity activity) {
		return new ActivityEntity(activity.id(), activity.name(), activity.imageUrl(), activity.energyInWh());
	}

	public Activity toModel() {
		return new Activity(id, name, imageUrl, energyInWh);
	}

	public void setName(@NonNull String name) {
		this.name = name;
	}

	public void setImageUrl(@Nullable String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public void setEnergyInWh(float energyInWh) {
		this.energyInWh = energyInWh;
	}
}

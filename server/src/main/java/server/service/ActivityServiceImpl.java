package server.service;

import commons.model.Activity;
import org.springframework.stereotype.Service;
import server.entity.ActivityEntity;
import server.repository.ActivityRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActivityServiceImpl implements ActivityService {
	private final ActivityRepository activityRepository;


	public ActivityServiceImpl(ActivityRepository activityRepository) {
		this.activityRepository = activityRepository;
	}

	@Override
	public List<Activity> addActivities(List<Activity> activities) {
		List<ActivityEntity> entities = activities.stream().map(ActivityEntity::fromModel).toList();
		List<ActivityEntity> savedEntities = activityRepository.saveAll(entities);
		return savedEntities.stream().map(ActivityEntity::toModel).toList();
	}

	@Override
	public List<Activity> provideActivities() {
		List<ActivityEntity> entities = activityRepository.findAll();
		List<Activity> activities = new ArrayList<>();

		for (ActivityEntity entity : entities) {
			activities.add(entity.toModel());
		}
		return activities;
	}

	@Override
	public void removeAllActivities() {
		activityRepository.deleteAll();
	}

	@Override
	public void removeActivity(long id) throws EntityNotFoundException {
		ActivityEntity toDelete = activityRepository.getById(id);
		activityRepository.delete(toDelete);
	}

	@Override
	public void updateActivity(long activityId, Activity activity) throws EntityNotFoundException {
		ActivityEntity entity = activityRepository.getById(activityId);
		entity.setName(activity.name());
		entity.setImageUrl(activity.imageUrl());
		entity.setEnergyInWh(activity.energyInWh());
		activityRepository.save(entity);
	}
}

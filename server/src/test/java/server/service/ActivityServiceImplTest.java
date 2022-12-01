package server.service;

import commons.model.Activity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import server.entity.ActivityEntity;
import server.repository.ActivityRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ActivityServiceImplTest {
	private static final List<Activity> FAKE_ACTIVITY_LIST = List.of(
			new Activity(0, "A1", null, 1f),
			new Activity(1, "A2", null, 2f),
			new Activity(2, "A3", null, 3f)
	);
	private static final List<ActivityEntity> FAKE_ACTIVITY_ENTITY_LIST = List.of(
			new ActivityEntity(0, "A1", null, 1f),
			new ActivityEntity(1, "A1", null, 1f),
			new ActivityEntity(2, "A1", null, 1f)
	);
	private static final Activity FAKE_UPDATED_ACTIVITY = new Activity(0, "A4", null, 4f);

	@Mock
	private ActivityRepository activityRepository;

	@Captor
	private ArgumentCaptor<List<ActivityEntity>> activityEntityListCaptor;

	private ActivityServiceImpl createService() {
		return new ActivityServiceImpl(activityRepository);
	}


	@Test
	public void addActivities_should_save_activities_to_repo() {
		var service = createService();
		service.addActivities(FAKE_ACTIVITY_LIST);

		verify(activityRepository).saveAll(activityEntityListCaptor.capture());

		var activityEntities = activityEntityListCaptor.getValue();
		var activityModels = activityEntities.stream().map(ActivityEntity::toModel).toList();

		assertEquals(FAKE_ACTIVITY_LIST, activityModels);
	}

	@Test
	public void provideActivities_should_return_a_list_containing_all_activities_in_repo() {
		var service = createService();

		when(activityRepository.findAll()).thenReturn(FAKE_ACTIVITY_ENTITY_LIST);

		assertEquals(service.provideActivities().size(), FAKE_ACTIVITY_LIST.size());
	}

	@Test
	public void provideActivities_should_return_Activities_from_repo() {
		var service = createService();

		when(activityRepository.findAll()).thenReturn(FAKE_ACTIVITY_ENTITY_LIST);

		assertEquals(service.provideActivities().get(0), FAKE_ACTIVITY_LIST.get(0));
	}

	@Test
	public void removeAllActivities_should_delete_activities_from_repo() {
		var service = createService();
		service.removeAllActivities();

		verify(activityRepository).deleteAll();
	}

	@Test
	public void removeActivity_should_delete_one_activity_from_repo() {
		var service = createService();

		when(activityRepository.getById(0L)).thenReturn(FAKE_ACTIVITY_ENTITY_LIST.get(0));
		service.removeActivity(0L);

		verify(activityRepository).delete(FAKE_ACTIVITY_ENTITY_LIST.get(0));
	}

	@Test
	public void updateActivity_should_update_the_contents_of_an_activity() {
		var service = createService();

		when(activityRepository.findAll()).thenReturn(FAKE_ACTIVITY_ENTITY_LIST);
		when(activityRepository.getById(0L)).thenReturn(FAKE_ACTIVITY_ENTITY_LIST.get(0));

		service.updateActivity(0, FAKE_UPDATED_ACTIVITY);

		assertEquals(service.provideActivities().get(0), FAKE_UPDATED_ACTIVITY);
	}
}

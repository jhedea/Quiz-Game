package server.service;


import commons.model.LeaderboardEntry;
import server.entity.LeaderboardEntity;
import server.repository.LeaderboardRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import java.util.List;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class LeaderboardServiceImplTest {

	private static final LeaderboardEntry FAKE_ENTRY = new LeaderboardEntry("Name", 100);

	private static final List<LeaderboardEntity> FAKE_LIST_ENTRIES = List.of(
		new LeaderboardEntity((long) -1, "name1", 10),
		new LeaderboardEntity((long) -2, "name3", 30),
		new LeaderboardEntity((long) 1, "name2", 20)
	);

	private static final List<LeaderboardEntry> FAKE_RESULT_LIST = List.of(
		new LeaderboardEntry("name3", 30),
		new LeaderboardEntry("name2", 20),
		new LeaderboardEntry("name1", 10)
	);

	@Mock
	private LeaderboardRepository leaderboardRepository;

	@Mock
	private ActivityService activityService;

	@Captor
	private ArgumentCaptor<LeaderboardEntity> leaderboardEntityCaptor;

	@Captor
	private ArgumentCaptor<List<LeaderboardEntry>> entriesCaptor;

	private LeaderboardServiceImpl createService() {
		return new LeaderboardServiceImpl(leaderboardRepository, activityService);
	}

	@Test
	public void addtoLeaderboard_should_save_entry() {
		var service = createService();
		service.addToLeaderboard(FAKE_ENTRY);

		verify(leaderboardRepository).save(leaderboardEntityCaptor.capture());
	}

	@Test
	public void clearLeaderboard_should_remove_all_entries() {
		var service = createService();
		service.clearLeaderboard();

		verify(leaderboardRepository).deleteAll();
	}

	@Test
	public void getTopLeaderboardEntries_should_get_proper_result() {
		var service = createService();
		when(leaderboardRepository.findAll()).thenReturn(FAKE_LIST_ENTRIES);
		List<LeaderboardEntry> result = service.getTopLeaderboardEntries();
		assertEquals(FAKE_RESULT_LIST, result);
	}

}

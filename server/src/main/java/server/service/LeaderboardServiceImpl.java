package server.service;

import java.util.Comparator;
import java.util.List;

import commons.model.LeaderboardEntry;
import org.springframework.stereotype.Service;
import server.entity.LeaderboardEntity;
import server.repository.LeaderboardRepository;

@Service
public class LeaderboardServiceImpl implements LeaderboardService {

	private final LeaderboardRepository repository;

	private final ActivityService activityService;

	private static final int NUMBER_OF_TOP_ENTRIES_TO_RETURN = 10;

	public LeaderboardServiceImpl(LeaderboardRepository repository,
		ActivityService activityService) {

		this.repository = repository;
		this.activityService = activityService;

	}

	@Override
	public void addToLeaderboard(LeaderboardEntry entry) {
		repository.save(LeaderboardEntity.fromModel(entry));
	}

	@Override
	public void clearLeaderboard() {
		repository.deleteAll();
	}

	public List<LeaderboardEntry> getTopLeaderboardEntries() {
		LeaderboardRepository leaderboardRepository = repository;

		List<LeaderboardEntry> entryList = leaderboardRepository.findAll()
				.stream()
				.sorted(Comparator.<LeaderboardEntity>comparingInt(entry -> entry.getScore()).reversed())
				.limit(NUMBER_OF_TOP_ENTRIES_TO_RETURN)
				.map(LeaderboardEntity::toModel)
				.toList();


		return entryList;
	}

}

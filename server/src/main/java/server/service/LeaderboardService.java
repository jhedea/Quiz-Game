package server.service;

import java.util.List;
import commons.model.LeaderboardEntry;

public interface LeaderboardService {

	/**
	 * Add a new leaderboard entry to the repository.
	 *
	 * @param entry the LeaderboardEntry to add
	 */
	void addToLeaderboard(LeaderboardEntry entry);

	/**
	 * Removes all existing leaderboard entry records from the repository.
	 */
	void clearLeaderboard();

	/**
	 * Returns the ten highest leaderboard entries from the repository.
	 *
	 * @return list of leaderboard entries
	 */
	List<LeaderboardEntry> getTopLeaderboardEntries();
}

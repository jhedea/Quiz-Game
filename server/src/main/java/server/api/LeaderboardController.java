
package server.api;

import java.util.List;
import commons.model.LeaderboardEntry;
import server.service.LeaderboardService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping
public class LeaderboardController {

	private final LeaderboardService leaderboardService;

	public LeaderboardController(LeaderboardService leaderboardService) {
		this.leaderboardService = leaderboardService;
	}

	@GetMapping("/api/leaderboard")
	public List<LeaderboardEntry> getLeaderboard() {
		return leaderboardService.getTopLeaderboardEntries();
	}
}
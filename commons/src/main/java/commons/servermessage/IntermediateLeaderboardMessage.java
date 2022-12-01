package commons.servermessage;

import commons.model.LeaderboardEntry;

import java.util.List;

public record IntermediateLeaderboardMessage(List<LeaderboardEntry> leaderboard) {

}

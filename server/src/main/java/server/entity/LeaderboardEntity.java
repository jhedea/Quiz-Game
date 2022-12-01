package server.entity;

import org.springframework.lang.NonNull;

import commons.model.LeaderboardEntry;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * DB entity for single-player leaderboard entries
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LeaderboardEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NonNull
	private String name;

	@NonNull
	private int score;

	public static LeaderboardEntity fromModel(LeaderboardEntry entry) {
		return new LeaderboardEntity(-1, entry.name(), entry.score());
	}

	public LeaderboardEntry toModel() {
		return new LeaderboardEntry(name, score);
	}

}

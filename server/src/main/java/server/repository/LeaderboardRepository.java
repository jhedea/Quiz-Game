package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.LeaderboardEntity;

/**
 * DB repository for single-player leaderboard entries
 */
@Repository
public interface LeaderboardRepository extends JpaRepository<LeaderboardEntity, Long> {
}
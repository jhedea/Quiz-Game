package server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import server.entity.ActivityEntity;

/**
 * DB repository for Activity
 */
@Repository
public interface ActivityRepository extends JpaRepository<ActivityEntity, Long> {
}
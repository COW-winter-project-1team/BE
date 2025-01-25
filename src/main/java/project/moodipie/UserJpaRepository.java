package project.moodipie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.moodipie.entity.User;

@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
}

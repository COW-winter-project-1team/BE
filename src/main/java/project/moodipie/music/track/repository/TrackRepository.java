package project.moodipie.music.track.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.moodipie.music.track.entity.Track;

@Repository
public interface TrackRepository extends JpaRepository<Track, String> {
}

package project.moodipie.music.playlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.moodipie.music.playlist.entity.PlaylistTrack;

@Repository
public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, Long> {
}

package project.moodipie.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.moodipie.music.playlist.entity.Playlist;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> { }

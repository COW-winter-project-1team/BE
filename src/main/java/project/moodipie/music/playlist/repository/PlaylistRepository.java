package project.moodipie.music.playlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.moodipie.music.playlist.entity.Playlist;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findByUserId(UUID userId);

    Long countByUserId(UUID userId);

    Playlist getReferenceByUserIdAndPlaylistNumber(UUID userId, Long playlistNumber);

    void deleteByUserIdAndPlaylistNumber(UUID userId, Long playlistNumber);
}

package project.moodipie.music.playlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.moodipie.music.playlist.entity.Playlist;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    List<Playlist> findByUserId(Long userId);

    Long countByUserId(Long userId);

    Playlist getReferenceByUserIdAndPlaylistNumber(Long userId, Long playlistNumber);

    void deleteByUserIdAndPlaylistNumber(Long userId, Long playlistNumber);
}

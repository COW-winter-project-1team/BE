package project.moodipie.music.playlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.moodipie.music.playlist.entity.PlaylistTrack;

import java.util.List;

@Repository
public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, Long> {
    void deleteByPlaylistTrackIdAndPlaylistId(Long playlistTrackId, Long playlist_id);

    List<PlaylistTrack> getReferenceByPlaylistId(Long playlistId);


}

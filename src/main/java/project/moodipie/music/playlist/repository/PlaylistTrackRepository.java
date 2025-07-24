package project.moodipie.music.playlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.moodipie.music.playlist.entity.PlaylistTrack;

import java.util.List;
import java.util.UUID;

@Repository
public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, Long> {

    List<PlaylistTrack> findByPlaylistId(Long playlistId);


    List<PlaylistTrack> findByPlaylistUserIdAndPlaylistPlaylistNumber(UUID userId, Long playlistNumber);

    void deleteByPlaylistTrackNumberAndPlaylist_PlaylistNumberAndUser_Id(Long playlistTrackNumber, Long playlistNumber, UUID userId);
    PlaylistTrack findByPlaylistTrackNumberAndPlaylist_PlaylistNumberAndUser_Id(Long playlistTrackNumber, Long playlistNumber, UUID userId);

}

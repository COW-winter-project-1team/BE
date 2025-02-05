package project.moodipie.music.playlist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.moodipie.music.playlist.entity.PlaylistTrack;

import java.util.List;

@Repository
public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, Long> {

    List<PlaylistTrack> findByPlaylistId(Long playlistId);


    List<PlaylistTrack> findByPlaylistUserIdAndPlaylistPlaylistNumber(Long userId, Long playlistNumber);

    void deleteByPlaylistTrackIdAndPlaylist_PlaylistNumberAndUser_Id(Long playlistTrackId, Long playlistNumber, Long userId);

}

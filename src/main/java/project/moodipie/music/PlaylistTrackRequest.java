package project.moodipie.music;

import lombok.Getter;
import project.moodipie.music.playlist.entity.Playlist;
import project.moodipie.music.track.entity.Track;

@Getter
public class PlaylistTrackRequest {
    private Playlist playlist;
    private Track track;

    public PlaylistTrack toEntity() {
        return PlaylistTrack.builder()
                .playlist(playlist)
                .track(track)
                .build();
    }
}

package project.moodipie.music.playlist.controller.dto.response;

import lombok.Getter;
import project.moodipie.music.playlist.entity.PlaylistTrack;

@Getter
public class PlaylistTrackEachResponse {

    private PlaylistTrack playlistTrack;

    public PlaylistTrackEachResponse(PlaylistTrack playlistTrack) {
        this.playlistTrack = playlistTrack;
    }
}

package project.moodipie.music.playlist.controller.dto.response;

import lombok.Builder;
import lombok.Getter;
import project.moodipie.music.playlist.entity.PlaylistTrack;

@Getter
public class PlaylistTrackEachResponse {

    private PlaylistTrack playlistTrack;

    @Builder
    public PlaylistTrackEachResponse(PlaylistTrack playlistTrack) {
        this.playlistTrack = playlistTrack;
    }

    public static PlaylistTrackEachResponse from(PlaylistTrack playlistTrack) {
        return PlaylistTrackEachResponse.builder()
                .playlistTrack(playlistTrack)
                .build();
    }
}

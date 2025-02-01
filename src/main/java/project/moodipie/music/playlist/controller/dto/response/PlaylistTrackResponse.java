package project.moodipie.music.playlist.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import project.moodipie.entity.Emotion;
import project.moodipie.music.playlist.entity.Playlist;
import project.moodipie.music.track.controller.dto.response.TrackResponse;
import project.moodipie.music.track.entity.Track;

import java.util.List;

@Getter
public class PlaylistTrackResponse {
    private PlaylistResponse playlist;

    private List<TrackResponse> track;

    @Builder
    public PlaylistTrackResponse(PlaylistResponse playlist, List<TrackResponse> track) {
        this.playlist = playlist;
        this.track = track;
    }

    public static PlaylistTrackResponse from(PlaylistResponse playlist, List<TrackResponse> track) {
        return PlaylistTrackResponse.builder()
                .playlist(playlist)
                .track(track)
                .build();
    }
}

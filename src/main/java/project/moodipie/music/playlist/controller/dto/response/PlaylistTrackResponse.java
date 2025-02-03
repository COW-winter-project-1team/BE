package project.moodipie.music.playlist.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import project.moodipie.music.track.controller.dto.response.TrackResponse;

import java.util.List;

@Getter
public class PlaylistTrackResponse {
    @Schema(description = "플레이 리스트 정보",
            example = "{ \"id\": 1, \"title\": \"My Playlist\",\"playlistImage\": \"http://dfdfjiweoif.com\",\"timestamp\": \"2024-12-12T12:12:12\" , \"emotion\": \"HAPPY\" }")
    private PlaylistResponse playlist;

    @Schema(description = "플레이리스트 트랙 정보", example = "[{ \"trackId\": sdakfjiosdwn1dnfsd, \"trackName\": \"바다의 왕자\", \"artistName\": \"박명수\",\"imageUrl\":\"http://dfdfjiweoif.com\" },{ \"trackId\": 다른 아이디, \"trackName\": \"다른노래\", \"artistName\": \"다른 가수\",\"imageUrl\":\"다른 링크\" }]")
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

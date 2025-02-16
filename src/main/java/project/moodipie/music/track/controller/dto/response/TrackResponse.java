package project.moodipie.music.track.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import project.moodipie.music.playlist.entity.PlaylistTrack;
import project.moodipie.music.track.entity.Track;

@Getter
public class TrackResponse {

    private Long playlistTrackNumber;
    @Schema(description = "트랙 ID", example = "sdakfjiosdwn1dnfsd")
    private String trackId;
    @Schema(description = "트랙 제목", example = "바다의 왕자")
    private String trackName;
    @Schema(description = "가수이름", example = "박명수")
    private String artistName;
    @Schema(description = "이미지 URL", example = "http://dfdfjiweoif.com")
    private String imageUrl;

    @Builder
    public TrackResponse(Long playlistTrackNumber, String trackId, String trackName, String artistName, String imageUrl) {
        this.playlistTrackNumber = playlistTrackNumber;
        this.trackId = trackId;
        this.trackName = trackName;
        this.artistName = artistName;
        this.imageUrl = imageUrl;
    }

    public static TrackResponse from(Track track) {
        return TrackResponse.builder()
                .trackId(track.getId())
                .trackName(track.getTrackName())
                .artistName(track.getArtistName())
                .imageUrl(track.getImageUrl())
                .build();
    }
    public static TrackResponse fromEachPlaylist(Track track, PlaylistTrack playlistTrack) {
        return TrackResponse.builder()
                .playlistTrackNumber(playlistTrack.getPlaylistTrackNumber())
                .trackId(track.getId())
                .trackName(track.getTrackName())
                .artistName(track.getArtistName())
                .imageUrl(track.getImageUrl())
                .build();
    }
}

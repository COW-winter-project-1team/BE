package project.moodipie.music.playlist.controller.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import project.moodipie.music.playlist.entity.PlaylistTrack;

@Getter
public class PlaylistTrackEachResponse {

    @Schema(description = "플리 속 트랙 번호", example = "1")
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
    public PlaylistTrackEachResponse(Long playlistTrackNumber, String trackId, String trackName, String artistName, String imageUrl) {
        this.playlistTrackNumber = playlistTrackNumber;
        this.trackId = trackId;
        this.trackName = trackName;
        this.artistName = artistName;
        this.imageUrl = imageUrl;
    }



    public static PlaylistTrackEachResponse from(PlaylistTrack playlistTrack) {
        return PlaylistTrackEachResponse.builder()
                .playlistTrackNumber(playlistTrack.getPlaylistTrackNumber())
                .trackId(playlistTrack.getTrack().getId())
                .trackName(playlistTrack.getTrack().getTrackName())
                .artistName(playlistTrack.getTrack().getArtistName())
                .imageUrl(playlistTrack.getPlaylist().getPlaylistImage())
                .build();

    }
}
